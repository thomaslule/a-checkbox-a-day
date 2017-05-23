package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;

public class TaskShould {
	
	@Test
	public void raiseTaskAddedWhenAddTask() {
		List<IEvent> history = new ArrayList<IEvent>();
		
		UUID id = Task.add(history, "buy baguette");
		
		assertThat(history).contains(new TaskAdded(id, "buy baguette"));
	}
	
	@Test
	public void raiseTaskCompletedWhenCompleteTask() {
		UUID id = UUID.randomUUID();
		List<IEvent> history = new ArrayList<IEvent>();
		history.add(new TaskAdded(id, "buy baguette"));
		Task task = new Task(history);
		
		task.complete(history);
		
		assertThat(history).contains(new TaskCompleted(id));
	}
	
	@Test
	public void dontRaiseTaskCompletedWhenCompleteTaskAlreadyCompleted() {
		UUID id = UUID.randomUUID();
		List<IEvent> history = new ArrayList<IEvent>();
		history.add(new TaskAdded(id, "buy baguette"));
		history.add(new TaskCompleted(id));
		Task task = new Task(history);
		
		task.complete(history);
		
		assertThat(history).containsOnlyOnce(new TaskCompleted(id));
	}
	
	@Test
	public void raiseTaskCompletedOnlyOnceWhenCompleteTaskTwice() {
		UUID id = UUID.randomUUID();
		List<IEvent> history = new ArrayList<IEvent>();
		history.add(new TaskAdded(id, "buy baguette"));
		Task task = new Task(history);
		
		task.complete(history);
		task.complete(history);

		assertThat(history).containsOnlyOnce(new TaskCompleted(id));
	}
	
}
