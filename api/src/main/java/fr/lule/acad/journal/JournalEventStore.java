package fr.lule.acad.journal;

import java.util.Arrays;

import fr.lule.acad.event.FileEventStore;
import fr.lule.acad.journal.event.JournalEntryEdited;
import fr.lule.acad.journal.event.JournalEvent;

public class JournalEventStore extends FileEventStore<JournalEvent, JournalDay> {

	public JournalEventStore(String filePath) {
		super(filePath, Arrays.asList(JournalEntryEdited.class));
	}

}
