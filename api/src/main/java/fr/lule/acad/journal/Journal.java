package fr.lule.acad.journal;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.journal.event.JournalEntryEdited;
import fr.lule.acad.util.IDateFactory;

public class Journal {

	public static boolean editJournalEntry(EventBus bus, String day, String text, IDateFactory dateFactory) {
		bus.post(new JournalEntryEdited(text, new JournalDay(day), dateFactory.now()));
		return true;
	}

}
