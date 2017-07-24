package fr.lule.acad.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.event.JournalEntryEdited;

public class JournalProjection {

	private List<JournalEntry> list = new ArrayList<JournalEntry>();

	public JournalProjection(List<IJournalEvent> history) {
		EventBus tempBus = new EventBus();
		tempBus.register(this);
		for (IJournalEvent event : history) {
			tempBus.post(event);
		}
	}

	public List<JournalEntry> getJournal(String month) {
		return list.stream().filter(entry -> entry.getDay().startsWith(month)).collect(Collectors.toList());
	}

	@Subscribe
	public void handleJournalEntryEdited(JournalEntryEdited event) {
		Optional<JournalEntry> journalEntry = list.stream().filter(entry -> entry.getDay().equals(event.day))
				.findFirst();
		if (journalEntry.isPresent()) {
			journalEntry.get().setText(event.getText());
		} else {
			list.add(new JournalEntry(event.day, event.text));
		}
	}

}
