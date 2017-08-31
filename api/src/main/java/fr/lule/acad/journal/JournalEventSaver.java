package fr.lule.acad.journal;

import com.google.common.eventbus.Subscribe;

import fr.lule.acad.event.IEventStore;
import fr.lule.acad.journal.event.JournalEvent;

public class JournalEventSaver {

	private final IEventStore<JournalEvent, JournalDay> store;

	public JournalEventSaver(IEventStore<JournalEvent, JournalDay> store) {
		this.store = store;
	}

	@Subscribe
	public void handle(JournalEvent event) {
		store.add(event);
	}

}
