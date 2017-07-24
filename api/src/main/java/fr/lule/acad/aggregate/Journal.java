package fr.lule.acad.aggregate;

import fr.lule.acad.event.JournalEntryEdited;
import fr.lule.acad.stream.IEventPublisher;

public class Journal {

	public static boolean editJournalEntry(IEventPublisher publisher, String day, String text) {
		publisher.publish(new JournalEntryEdited(day, text));
		return true;
	}

}
