package fr.lule.acad.journal.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fr.lule.acad.journal.event.JournalEntryEdited;
import fr.lule.acad.journal.event.JournalEvent;

public class JournalProjection {

	private List<JournalEntry> list = new ArrayList<JournalEntry>();

	public JournalProjection(List<JournalEvent> history) {
		EventBus tempBus = new EventBus();
		tempBus.register(this);
		for (JournalEvent event : history) {
			tempBus.post(event);
		}
	}

	public List<JournalEntry> getJournal(String month) {
		return list.stream().filter(entry -> entry.getDay().startsWith(month)).collect(Collectors.toList());
	}

	@Subscribe
	public void handleJournalEntryEdited(JournalEntryEdited event) {
		Optional<JournalEntry> journalEntry = list.stream().filter(entry -> entry.getDay().equals(event.getDay()))
				.findFirst();
		if (journalEntry.isPresent()) {
			journalEntry.get().setText(event.getText());
		} else {
			list.add(new JournalEntry(event.getDay(), event.getText()));
		}
	}

}
