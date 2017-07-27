package fr.lule.acad.store;

import java.util.UUID;

import com.google.common.eventbus.Subscribe;

import fr.lule.acad.event.IItemEvent;

public class ItemEventSaver {

	private final IEventStore<IItemEvent, UUID> store;

	public ItemEventSaver(IEventStore<IItemEvent, UUID> store) {
		this.store = store;
	}

	@Subscribe
	public void handle(IItemEvent event) {
		store.add(event);
	}

}
