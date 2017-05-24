package fr.lule.acad;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.aggregate.Task;
import fr.lule.acad.event.IEvent;
import fr.lule.acad.projection.TaskDisplayed;
import fr.lule.acad.projection.TaskList;
import fr.lule.acad.stream.EventsBus;
import fr.lule.acad.stream.MemoryEventStream;

public class AcadShould {
	
	
	@Test
	public void displayTaskInTaskListWhenAddTask() {
		TaskList list = new TaskList(new ArrayList<IEvent>());
		EventsBus bus = new EventsBus(new MemoryEventStream());
		bus.subscribe(list);
		
		UUID id = Task.add(bus, "buy bread");
		
		assertThat(list.getList()).contains(new TaskDisplayed(id, "buy bread", false));
	}
	

}
