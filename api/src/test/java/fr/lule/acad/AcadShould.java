package fr.lule.acad;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.aggregate.Task;
import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.projection.TaskDisplayed;
import fr.lule.acad.projection.TaskList;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;

public class AcadShould {
	
	@Test
	public void displayTaskInTaskListWhenAddTask() {
		TaskList list = new TaskList(new ArrayList<ITaskEvent>());
		EventsBus bus = new EventsBus(new InMemoryEventStore<ITaskEvent>());
		bus.subscribe(list);
		
		UUID id = Task.add(bus, "buy bread", "2017-05");
		
		assertThat(list.getList("2017-05")).contains(new TaskDisplayed(id, "buy bread", "2017-05", false));
	}
	

}
