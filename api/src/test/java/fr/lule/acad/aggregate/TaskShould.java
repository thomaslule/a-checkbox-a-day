package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;

public class TaskShould {
	
	InMemoryEventStore<ITaskEvent> store;
	EventsBus bus;
	
	@Before
	public void before() {
		store = new InMemoryEventStore<ITaskEvent>();
		bus = new EventsBus(store);
	}
	
	@Test
	public void raiseTaskAddedWhenAddTask() {
		UUID id = Task.add(bus, "buy baguette", "2017-05");
		
		assertThat(store.getAllEvents()).contains(new TaskAdded(id, "buy baguette", "2017-05"));
	}
	
	@Test
	public void raiseTaskCompletedWhenCompleteTask() {
		UUID id = UUID.randomUUID();
		store.add(new TaskAdded(id, "buy baguette", "2017-05"));
		Task task = new Task(store.getEventsFor(id));
		
		task.complete(bus);
		
		assertThat(store.getEventsFor(id)).contains(new TaskCompleted(id));
	}

	@Test
	public void dontRaiseTaskCompletedWhenCompleteTaskAlreadyCompleted() {
		UUID id = UUID.randomUUID();
		store.add(new TaskAdded(id, "buy baguette", "2017-05"));
		store.add(new TaskCompleted(id));
		Task task = new Task(store.getEventsFor(id));
		
		task.complete(bus);
		
		assertThat(store.getAllEvents()).containsOnlyOnce(new TaskCompleted(id));
	}

	@Test
	public void dontRaiseTaskCompletedWhenTaskDoesntExist() {
		UUID id = UUID.randomUUID();
		Task task = new Task(store.getEventsFor(id));
		
		task.complete(bus);
		
		assertThat(store.getAllEvents()).isEmpty();
	}
	
	@Test
	public void raiseTaskCompletedOnlyOnceWhenCompleteTaskTwice() {
		UUID id = UUID.randomUUID();
		store.add(new TaskAdded(id, "buy baguette", "2017-05"));
		Task task = new Task(store.getEventsFor(id));
		
		task.complete(bus);
		task.complete(bus);

		assertThat(store.getEventsFor(id)).containsOnlyOnce(new TaskCompleted(id));
	}
	
}
