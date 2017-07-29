package fr.lule.acad.store;

import com.google.common.eventbus.Subscribe;

import fr.lule.acad.event.ItemEvent;
import fr.lule.acad.event.ItemId;

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
