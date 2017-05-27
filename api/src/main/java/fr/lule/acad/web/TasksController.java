package fr.lule.acad.web;

import java.util.List;
import java.util.UUID;

import fr.lule.acad.aggregate.Task;
import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.projection.TaskDisplayed;
import fr.lule.acad.projection.TaskList;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.stream.IEventPublisher;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.payload.Payload;

@Prefix("/api")
public class TasksController {
	
	private TaskList list;
	private IEventPublisher publisher;
	private IEventStore<ITaskEvent> taskEventStore;

	public TasksController(TaskList list, IEventPublisher publisher, IEventStore<ITaskEvent> taskEventStore) {
		this.list = list;
		this.publisher = publisher;
		this.taskEventStore = taskEventStore;
	}

	@Get("/Tasks")
	public List<TaskDisplayed> getTasks() {
		return list.getList();
	}
	
	@Post("/AddTask")
	public Payload addTask(AddTaskCommand command) {
		Task.add(publisher, command.todo);
		return Payload.created();
	}
	
	@Post("/CompleteTask")
	public Payload completeTask(CompleteTaskCommand command) {
		Task task = new Task(taskEventStore.getEventsFor(command.id));
		task.complete(publisher);
		return Payload.ok();
	}
	
	private static class AddTaskCommand {
		public String todo;
	}
	
	private static class CompleteTaskCommand {
		public UUID id;
	}
	

}
