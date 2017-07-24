package fr.lule.acad.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemCancelled;
import fr.lule.acad.event.ItemDeleted;
import fr.lule.acad.event.ItemRestored;
import fr.lule.acad.event.ItemTextChanged;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;

public class ItemList {

	private List<ItemDisplayed> list = new ArrayList<ItemDisplayed>();

	public ItemList(List<? extends IItemEvent> history) {
		EventBus tempBus = new EventBus();
		tempBus.register(this);
		for (IItemEvent event : history) {
			tempBus.post(event);
		}
	}

	@Subscribe
	public void handleItemAdded(ItemAdded event) {
		list.add(new ItemDisplayed(event.getAggregateId(), event.getType(), event.getText(), event.getMonth(), false,
				false));
	}

	@Subscribe
	public void handleItemCancelled(ItemCancelled event) {
		findInList(event.getAggregateId()).ifPresent(item -> {
			item.setCancelled(true);
		});
	}

	@Subscribe
	public void handleItemRestored(ItemRestored event) {
		findInList(event.getAggregateId()).ifPresent(item -> {
			item.setCancelled(false);
		});
	}

	@Subscribe
	public void handleItemDeleted(ItemDeleted event) {
		list.removeIf(t -> t.getId().equals(event.getAggregateId()));
	}

	@Subscribe
	public void handleTaskCompleted(TaskCompleted event) {
		findInList(event.getAggregateId()).ifPresent(item -> {
			item.setCompleted(true);
		});
	}

	@Subscribe
	public void handleTaskUncompleted(TaskUncompleted event) {
		findInList(event.getAggregateId()).ifPresent(item -> {
			item.setCompleted(false);
		});
	}

	@Subscribe
	public void handleItemTextChanged(ItemTextChanged event) {
		findInList(event.getAggregateId()).ifPresent(item -> {
			item.setText(event.getNewText());
		});
	}

	public List<ItemDisplayed> getList(String month) {
		return list.stream().filter(t -> t.getMonth().equals(month)).collect(Collectors.toList());
	}

	public ItemDisplayed getItem(UUID id) {
		return findInList(id).get();
	}

	private Optional<ItemDisplayed> findInList(UUID id) {
		return list.stream().filter(item -> item.getId().equals(id)).findFirst();
	}

}
