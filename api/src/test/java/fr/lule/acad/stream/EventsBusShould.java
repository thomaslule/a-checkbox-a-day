package fr.lule.acad.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.store.InMemoryEventStore;

public class EventsBusShould {

	InMemoryEventStore<IItemEvent> store;
	EventsBus bus;
	
	@Before
	public void before() {
		store = new InMemoryEventStore<IItemEvent>();
		bus = new EventsBus(store);
	}
	
	@Test
	public void storeEventWhenPublishEvent() {
		UUID id = UUID.randomUUID();
		
		bus.publish(new ItemAdded(id, "buy bread", "2017-05"));
		
		assertThat(store.getAllEvents()).contains(new ItemAdded(id, "buy bread", "2017-05"));
	}
	
	@Test
	public void callSubscribersWhenPublishEvent() {
		SpySubscriber spy = new SpySubscriber();
		bus.subscribe(spy);
		
		bus.publish(new ItemAdded(UUID.randomUUID(), "buy bread", "2017-05"));

		assertThat(spy.called).isTrue();
	}
	
	private class SpySubscriber implements IEventSubscriber {
		
		private boolean called = false;

		@Override
		public void handle(IEvent event) {
			called = true;
		}
		
	}

}
