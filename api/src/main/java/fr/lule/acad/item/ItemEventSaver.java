package fr.lule.acad.item;

import com.google.common.eventbus.Subscribe;

import fr.lule.acad.event.IEventStore;
import fr.lule.acad.item.event.ItemEvent;

public class ItemEventSaver {

	private final IEventStore<ItemEvent, ItemId> store;

	public ItemEventSaver(IEventStore<ItemEvent, ItemId> store) {
		this.store = store;
	}

	@Subscribe
	public void handle(ItemEvent event) {
		store.add(event);
	}

}
