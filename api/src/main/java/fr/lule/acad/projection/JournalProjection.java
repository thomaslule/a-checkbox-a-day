package fr.lule.acad.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.event.JournalEntryEdited;
import fr.lule.acad.stream.HistoryPublisher;
import fr.lule.acad.stream.IEventPublisher;

public class JournalProjection {

	private List<JournalEntry> list = new ArrayList<JournalEntry>();

	public JournalProjection(List<IJournalEvent> history) {
		HistoryPublisher hp = new HistoryPublisher();
		subscribeTo(hp);
		hp.publishAll(history);
	}

	public List<JournalEntry> getJournal(String month) {
		return list.stream().filter(entry -> entry.getDay().startsWith(month)).collect(Collectors.toList());
	}

	public void subscribeTo(IEventPublisher bus) {
		bus.on(JournalEntryEdited.class, event -> {
			Optional<JournalEntry> journalEntry = list.stream().filter(entry -> entry.getDay().equals(event.day))
					.findFirst();
			if (journalEntry.isPresent()) {
				journalEntry.get().setText(event.getText());
			} else {
				list.add(new JournalEntry(event.day, event.text));
			}
		});
	}

}
