package fr.lule.acad.store;

import com.google.common.eventbus.Subscribe;

import fr.lule.acad.event.IJournalEvent;

public class JournalEventSaver {

	private final IEventStore<IJournalEvent, String> store;

	public JournalEventSaver(IEventStore<IJournalEvent, String> store) {
		this.store = store;
	}

	@Subscribe
	public void handle(IJournalEvent event) {
		store.add(event);
	}

}
