package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.stream.EventsBus;
import fr.lule.acad.stream.MemoryEventStream;

public class TaskShould {
	
	MemoryEventStream stream;
	EventsBus bus;
	
	@Before
	public void before() {
		stream = new MemoryEventStream();
		bus = new EventsBus(stream);
	}
	
	@Test
	public void raiseTaskAddedWhenAddTask() {
		UUID id = Task.add(bus, "buy baguette");
		
		assertThat(stream.getHistory()).contains(new TaskAdded(id, "buy baguette"));
	}
	
	@Test
	public void raiseTaskCompletedWhenCompleteTask() {
		UUID id = UUID.randomUUID();
		stream.add(new TaskAdded(id, "buy baguette"));
		Task task = new Task(stream.getHistory());
		
		task.complete(bus);
		
		assertThat(stream.getHistory()).contains(new TaskCompleted(id));
	}
	
	@Test
	public void dontRaiseTaskCompletedWhenCompleteTaskAlreadyCompleted() {
		UUID id = UUID.randomUUID();
		stream.add(new TaskAdded(id, "buy baguette"));
		stream.add(new TaskCompleted(id));
		Task task = new Task(stream.getHistory());
		
		task.complete(bus);
		
		assertThat(stream.getHistory()).containsOnlyOnce(new TaskCompleted(id));
	}
	
	@Test
	public void raiseTaskCompletedOnlyOnceWhenCompleteTaskTwice() {
		UUID id = UUID.randomUUID();
		stream.add(new TaskAdded(id, "buy baguette"));
		Task task = new Task(stream.getHistory());
		
		task.complete(bus);
		task.complete(bus);

		assertThat(stream.getHistory()).containsOnlyOnce(new TaskCompleted(id));
	}
	
}
