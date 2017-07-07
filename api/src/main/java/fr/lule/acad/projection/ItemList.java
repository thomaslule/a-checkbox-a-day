package fr.lule.acad.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.stream.IEventSubscriber;

public class ItemList implements IEventSubscriber {
	
	private List<ItemDisplayed> list = new ArrayList<ItemDisplayed>();

	public ItemList(List<IItemEvent> history) {
		history.forEach(this::handle);
	}

	public List<ItemDisplayed> getList(String month) {
		return list.stream().filter(t -> t.getMonth().equals(month)).collect(Collectors.toList());
	}
	
	public ItemDisplayed getItem(UUID id) {
		return list.stream().filter(t -> t.getId().equals(id)).findFirst().get();
	}

	@Override
	public void handle(IEvent event) {
		if (event instanceof ItemAdded) {
			ItemAdded itemAdded = (ItemAdded) event;
			list.add(new ItemDisplayed(itemAdded.getAggregateId(), itemAdded.getType(), itemAdded.getText(), itemAdded.getMonth(), false));
		}
		if (event instanceof TaskCompleted) {
			UUID id = ((TaskCompleted) event).getAggregateId();
			list.stream().filter(t -> t.getId().equals(id)).findFirst().ifPresent(t -> t.setCompleted(true));
		}
	}

}
