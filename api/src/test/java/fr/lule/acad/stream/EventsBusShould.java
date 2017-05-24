package fr.lule.acad.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.store.MemoryEventStore;

public class EventsBusShould {

	MemoryEventStore store;
	EventsBus bus;
	
	@Before
	public void before() {
		store = new MemoryEventStore();
		bus = new EventsBus(store);
	}
	
	@Test
	public void storeEventWhenPublishEvent() {
		UUID id = UUID.randomUUID();
		
		bus.publish(new TaskAdded(id, "buy bread"));
		
		assertThat(store.getAllEvents()).contains(new TaskAdded(id, "buy bread"));
	}
	
	@Test
	public void callSubscribersWhenPublishEvent() {
		SpySubscriber spy = new SpySubscriber();
		bus.subscribe(spy);
		
		bus.publish(new TaskAdded(UUID.randomUUID(), "buy bread"));

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
