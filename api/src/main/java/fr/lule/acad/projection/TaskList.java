package fr.lule.acad.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.stream.IEventSubscriber;

public class TaskList implements IEventSubscriber {
	
	private List<TaskDisplayed> list = new ArrayList<TaskDisplayed>();

	public TaskList(List<ITaskEvent> history) {
		history.forEach(this::handle);
	}

	public List<TaskDisplayed> getList(String month) {
		return list.stream().filter(t -> t.getMonth().equals(month)).collect(Collectors.toList());
	}
	
	public TaskDisplayed getTask(UUID id) {
		return list.stream().filter(t -> t.getId().equals(id)).findFirst().get();
	}

	@Override
	public void handle(IEvent event) {
		if (event instanceof TaskAdded) {
			TaskAdded taskAdded = (TaskAdded) event;
			list.add(new TaskDisplayed(taskAdded.getAggregateId(), taskAdded.getTodo(), taskAdded.getMonth(), false));
		}
		if (event instanceof TaskCompleted) {
			UUID id = ((TaskCompleted) event).getAggregateId();
			list.stream().filter(t -> t.getId().equals(id)).findFirst().ifPresent(t -> t.setCompleted(true));
		}
	}

}
