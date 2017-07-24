package fr.lule.acad.aggregate;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.JournalEntryEdited;

public class Journal {

	public static boolean editJournalEntry(EventBus bus, String day, String text) {
		bus.post(new JournalEntryEdited(day, text));
		return true;
	}

}
