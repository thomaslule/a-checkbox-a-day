package fr.lule.acad.store;

import com.google.common.eventbus.Subscribe;

import fr.lule.acad.event.JournalDay;
import fr.lule.acad.event.JournalEvent;

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
