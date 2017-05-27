package fr.lule.acad.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;

public class TaskListShould {
	
	@Test
	public void addTaskInListWhenTaskAdded() {
		TaskList list = new TaskList(new ArrayList<ITaskEvent>());
		UUID id = UUID.randomUUID();
		
		list.handle(new TaskAdded(id, "buy bread"));
		
		assertThat(list.getList()).contains(new TaskDisplayed(id, "buy bread", false));
	}
	
	@Test
	public void haveTaskInListWhenHistoryContainsTaskAdded() {
		List<ITaskEvent> history = new ArrayList<ITaskEvent>();
		UUID id = UUID.randomUUID();
		history.add(new TaskAdded(id, "buy bread"));
		
		TaskList list = new TaskList(history);

		assertThat(list.getList()).contains(new TaskDisplayed(id, "buy bread", false));
	}
	
	@Test
	public void setTaskCompletedWhenTaskCompleted() {
		List<ITaskEvent> history = new ArrayList<ITaskEvent>();
		UUID id1 = UUID.randomUUID();
		history.add(new TaskAdded(id1, "buy bread"));
		UUID id2 = UUID.randomUUID();
		history.add(new TaskAdded(id2, "pet cat"));
		TaskList list = new TaskList(history);
		
		list.handle(new TaskCompleted(id1));
		
		assertThat(list.getList().stream().filter(t -> t.getId().equals(id1)).findFirst().get().isCompleted()).isTrue();
	}

}
