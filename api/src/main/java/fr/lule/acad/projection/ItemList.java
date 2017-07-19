package fr.lule.acad.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemCancelled;
import fr.lule.acad.event.ItemDeleted;
import fr.lule.acad.event.ItemRestored;
import fr.lule.acad.event.ItemTextChanged;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;
import fr.lule.acad.stream.HistoryPublisher;
import fr.lule.acad.stream.IEventPublisher;

public class ItemList {

	private List<ItemDisplayed> list = new ArrayList<ItemDisplayed>();

	public ItemList(List<? extends IItemEvent> history) {
		HistoryPublisher hp = new HistoryPublisher();
		subscribeTo(hp);
		hp.publishAll(history);

	}

	public void subscribeTo(IEventPublisher bus) {
		bus.on(ItemAdded.class, itemAdded -> {
			list.add(new ItemDisplayed(itemAdded.getAggregateId(), itemAdded.getType(), itemAdded.getText(),
					itemAdded.getMonth(), false, false));
		});
		bus.on(ItemCancelled.class, event -> {
			findInList(event.getAggregateId()).ifPresent(item -> {
				item.setCancelled(true);
			});
		});
		bus.on(ItemRestored.class, event -> {
			findInList(event.getAggregateId()).ifPresent(item -> {
				item.setCancelled(false);
			});
		});
		bus.on(ItemDeleted.class, event -> {
			list.removeIf(t -> t.getId().equals(event.getAggregateId()));
		});
		bus.on(TaskCompleted.class, event -> {
			findInList(event.getAggregateId()).ifPresent(item -> {
				item.setCompleted(true);
			});
		});
		bus.on(TaskUncompleted.class, event -> {
			findInList(event.getAggregateId()).ifPresent(item -> {
				item.setCompleted(false);
			});
		});
		bus.on(ItemTextChanged.class, event -> {
			findInList(event.getAggregateId()).ifPresent(item -> {
				item.setText(event.getNewText());
			});
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
