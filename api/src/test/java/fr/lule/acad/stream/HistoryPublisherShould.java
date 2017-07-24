package fr.lule.acad.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.ItemAdded;

public class HistoryPublisherShould {

	@Test
	public void publishAllEvents() {
		HistoryPublisher hp = new HistoryPublisher();
		Spy spy = new Spy();
		hp.on(ItemAdded.class, spy);
		List<IEvent<?>> history = new ArrayList<IEvent<?>>();
		history.add(new ItemAdded(null, null, null, null));
		hp.publishAll(history);

		assertThat(spy.called).isTrue();
	}

	private static class Spy implements Consumer<ItemAdded> {

		boolean called = false;

		@Override
		public void accept(ItemAdded t) {
			called = true;
		}

	}

}
