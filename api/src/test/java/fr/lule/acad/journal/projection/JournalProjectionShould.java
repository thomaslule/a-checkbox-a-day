package fr.lule.acad.journal.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.journal.JournalDay;
import fr.lule.acad.journal.event.JournalEntryEdited;
import fr.lule.acad.journal.event.JournalEvent;
import fr.lule.acad.journal.projection.JournalEntry;
import fr.lule.acad.journal.projection.JournalProjection;

public class JournalProjectionShould {

	private List<JournalEvent> history;
	private JournalProjection projection;

	@Before
	public void before() {
		history = new ArrayList<JournalEvent>();
	}

	@Test
	public void addEntryInListWhenEntryEdited() {
		projection = new JournalProjection(history);

		projection.handleJournalEntryEdited(new JournalEntryEdited("test", new JournalDay("2017-07-19"), new Date(0)));

		assertThat(projection.getJournal("2017-07")).contains(new JournalEntry("2017-07-19", "test"));
		assertThat(projection.getJournal("2017-06")).isEmpty();
	}

	@Test
	public void haveEntryInListWhenHistoryContainsEntryEdited() {
		history.add(new JournalEntryEdited("test", new JournalDay("2017-07-19"), new Date(0)));
		projection = new JournalProjection(history);

		assertThat(projection.getJournal("2017-07")).contains(new JournalEntry("2017-07-19", "test"));
		assertThat(projection.getJournal("2017-06")).isEmpty();
	}

}
