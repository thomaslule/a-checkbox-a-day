package fr.lule.acad.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.stream.IEventSubscriber;

public class TaskList implements IEventSubscriber {
	
	private List<TaskDisplayed> list = new ArrayList<TaskDisplayed>();

	public TaskList(List<IEvent> history) {
		history.forEach(this::handle);
	}

	public List<TaskDisplayed> getList() {
		return list;
	}

	@Override
	public void handle(IEvent event) {
		if (event instanceof TaskAdded) {
			TaskAdded taskAdded = (TaskAdded) event;
			list.add(new TaskDisplayed(taskAdded.getId(), taskAdded.getTodo(), false));
		}
		if (event instanceof TaskCompleted) {
			UUID id = ((TaskCompleted) event).getId();
			list.stream().filter(t -> t.getId().equals(id)).findFirst().ifPresent(t -> t.setCompleted(true));
		}
	}

}
