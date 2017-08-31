package fr.lule.acad.item.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fr.lule.acad.item.ItemId;
import fr.lule.acad.item.event.ItemAdded;
import fr.lule.acad.item.event.ItemCancelled;
import fr.lule.acad.item.event.ItemDeleted;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.event.ItemMoved;
import fr.lule.acad.item.event.ItemRestored;
import fr.lule.acad.item.event.ItemTextChanged;
import fr.lule.acad.item.event.TaskCompleted;
import fr.lule.acad.item.event.TaskUncompleted;

public class ItemListProjection {

	private List<ItemDisplayed> list = new ArrayList<ItemDisplayed>();

	public ItemListProjection(List<ItemEvent> history) {
		EventBus tempBus = new EventBus();
		tempBus.register(this);
		for (ItemEvent event : history) {
			tempBus.post(event);
		}
	}

	@Subscribe
	public void handleItemAdded(ItemAdded event) {
		list.add(new ItemDisplayed(event.getAggregateId().getId(), event.getType(), event.getText(), event.getMonth(),
				false, false, false));
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
		list.removeIf(t -> t.getId().equals(event.getAggregateId().getId()));
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

	@Subscribe
	public void handleItemMoved(ItemMoved event) {
		findInList(event.getAggregateId()).ifPresent(item -> {
			item.setMoved(true);
		});
	}

	public List<ItemDisplayed> getList(String month) {
		return list.stream().filter(t -> t.getMonth().equals(month)).collect(Collectors.toList());
	}

	public ItemDisplayed getItem(ItemId id) {
		return findInList(id).get();
	}

	private Optional<ItemDisplayed> findInList(ItemId id) {
		return list.stream().filter(item -> item.getId().equals(id.getId())).findFirst();
	}

}
