package fr.lule.acad.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.store.InMemoryEventStore;

public class EventsBusShould {

	private InMemoryEventStore<IItemEvent> store;
	private EventsBus bus;

	@Before
	public void before() {
		store = new InMemoryEventStore<IItemEvent>();
		bus = new EventsBus(store);
	}

	@Test
	public void storeEventWhenPublishEvent() {
		UUID id = UUID.randomUUID();

		bus.<ItemAdded>publish(new ItemAdded(id, "buy bread", "2017-05", ItemType.TASK));

		assertThat(store.getAllEvents()).contains(new ItemAdded(id, "buy bread", "2017-05", ItemType.TASK));
	}

	@Test
	public void callSubscribersWhenPublishEvent() {
		Spy spy = new Spy();
		bus.<ItemAdded>on(ItemAdded.class, spy);

		bus.<ItemAdded>publish(new ItemAdded(UUID.randomUUID(), "buy bread", "2017-05", ItemType.TASK));

		assertThat(spy.called).isTrue();
	}

	@Test
	public void dontCrashIfEventUnknown() {
		UnknownEvent e = new UnknownEvent();

		bus.<UnknownEvent>publish(e);

		assertThat(store.getAllEvents()).contains(e);
	}

	private static class Spy implements Consumer<ItemAdded> {

		public boolean called = false;

		@Override
		public void accept(ItemAdded t) {
			called = true;
		}

	}

	private static class UnknownEvent implements IItemEvent {

		@Override
		public UUID getAggregateId() {
			return null;
		}
	}

}
