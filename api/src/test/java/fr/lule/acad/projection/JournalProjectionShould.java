package fr.lule.acad.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.event.JournalEntryEdited;

public class JournalProjectionShould {

	private List<IJournalEvent> history;
	private JournalProjection projection;

	@Before
	public void before() {
		history = new ArrayList<IJournalEvent>();
	}

	@Test
	public void addEntryInListWhenEntryEdited() {
		projection = new JournalProjection(history);

		projection.handleJournalEntryEdited(new JournalEntryEdited("2017-07-19", "test"));

		assertThat(projection.getJournal("2017-07")).contains(new JournalEntry("2017-07-19", "test"));
		assertThat(projection.getJournal("2017-06")).isEmpty();
	}

	@Test
	public void haveEntryInListWhenHistoryContainsEntryEdited() {
		history.add(new JournalEntryEdited("2017-07-19", "test"));
		projection = new JournalProjection(history);

		assertThat(projection.getJournal("2017-07")).contains(new JournalEntry("2017-07-19", "test"));
		assertThat(projection.getJournal("2017-06")).isEmpty();
	}

}
