package fr.lule.acad.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.store.InMemoryEventStore;

public class EventsBusShould {

	InMemoryEventStore<ITaskEvent> store;
	EventsBus bus;
	
	@Before
	public void before() {
		store = new InMemoryEventStore<ITaskEvent>();
		bus = new EventsBus(store);
	}
	
	@Test
	public void storeEventWhenPublishEvent() {
		UUID id = UUID.randomUUID();
		
		bus.publish(new TaskAdded(id, "buy bread", "2017-05"));
		
		assertThat(store.getAllEvents()).contains(new TaskAdded(id, "buy bread", "2017-05"));
	}
	
	@Test
	public void callSubscribersWhenPublishEvent() {
		SpySubscriber spy = new SpySubscriber();
		bus.subscribe(spy);
		
		bus.publish(new TaskAdded(UUID.randomUUID(), "buy bread", "2017-05"));

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
