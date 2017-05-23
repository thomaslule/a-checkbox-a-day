package fr.lule.acad.projection;

import java.util.ArrayList;
import java.util.List;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.TaskAdded;

public class TaskList {
	
	private List<TaskDisplayed> list;

	public TaskList(List<IEvent> history) {
		list = new ArrayList<TaskDisplayed>();
		history.forEach(event -> {
			if (event instanceof TaskAdded) {
				handle((TaskAdded) event);
			}
		});
	}

	public void handle(TaskAdded taskAdded) {
		list.add(new TaskDisplayed(taskAdded.getId(), taskAdded.getTodo()));
	}

	public List<TaskDisplayed> getList() {
		return list;
	}

}
