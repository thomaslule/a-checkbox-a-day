package fr.lule.acad.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.TaskAdded;

public class TaskListShould {
	
	@Test
	public void addTaskInListWhenTaskAdded() {
		TaskList list = new TaskList(new ArrayList<IEvent>());
		UUID id = UUID.randomUUID();
		
		list.handle(new TaskAdded(id, "buy bread"));
		
		assertThat(list.getList()).contains(new TaskDisplayed(id, "buy bread"));
	}
	
	@Test
	public void haveTaskInListWhenHistoryContainsTaskAdded() {
		List<IEvent> history = new ArrayList<IEvent>();
		UUID id = UUID.randomUUID();
		history.add(new TaskAdded(id, "buy bread"));
		
		TaskList list = new TaskList(history);

		assertThat(list.getList()).contains(new TaskDisplayed(id, "buy bread"));
	}

}
