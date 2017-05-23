package fr.lule.acad.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;

public class EventsBusShould {
	
	@Test
	public void storeEventWhenPublishEvent() {
		MemoryEventStream stream = new MemoryEventStream();
		EventsBus bus = new EventsBus(stream);
		UUID id = UUID.randomUUID();
		
		bus.publish(new TaskAdded(id, "buy bread"));
		
		assertThat(stream.getHistory()).contains(new TaskAdded(id, "buy bread"));
	}
	/*
	@Test
	public void callSubscribersWhenPublishEvent() {
		EventsBus bus = new EventsBus(new MemoryEventStream());
		EventSubscriber<TaskAdded> sub1 = new EventSubscriber<TaskAdded>();
		bus.subscribe(sub1);
		EventSubscriber<TaskAdded> sub2 = new EventSubscriber<TaskAdded>();
		bus.subscribe(sub2);
		EventSubscriber<TaskCompleted> sub3 = new EventSubscriber<TaskCompleted>();
		bus.subscribe(sub3);
		
		bus.publish(new TaskAdded(UUID.randomUUID(), "buy bread"));

		assertThat(sub1.isCalled()).isTrue();
		assertThat(sub2.isCalled()).isTrue();
		assertThat(sub3.isCalled()).isFalse();
	}
*/
}
