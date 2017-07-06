package fr.lule.acad.web;

import java.util.UUID;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fr.lule.acad.aggregate.Task;
import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.projection.TaskList;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.stream.IEventPublisher;
import fr.lule.acad.web.validation.CommandRunner;
import fr.lule.acad.web.validation.Month;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.constants.HttpStatus;
import net.codestory.http.payload.Payload;

@Prefix("/api")
public class TasksController {

	private TaskList list;
	private IEventPublisher publisher;
	private IEventStore<ITaskEvent> taskEventStore;
	private Validator validator;

	public TasksController(TaskList list, IEventPublisher publisher, IEventStore<ITaskEvent> taskEventStore) {
		this.list = list;
		this.publisher = publisher;
		this.taskEventStore = taskEventStore;
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Get("/Tasks/:month")
	public Payload getTasks(GetMonthCommand month) {
		return CommandRunner.ifValid(month, validator, (c) -> {
			return new Payload(null, list.getList(month.month));
		});
	}

	@Post("/AddTask")
	public Payload addTask(AddTaskCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			UUID id = Task.add(publisher, c.todo, c.month);
			// TODO use a repository instead of getting the task from the list
			return new Payload(null, list.getTask(id), HttpStatus.CREATED);
		});
	}

	@Post("/CompleteTask")
	public Payload completeTask(CompleteTaskCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Task task = new Task(taskEventStore.getEventsFor(command.id));
			if (task.complete(publisher)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	public static class GetMonthCommand {
		@NotNull
		@Month
		public String month;

		public GetMonthCommand(String month) {
			this.month = month;
		}

	}

	public static class AddTaskCommand {
		@NotNull
		@Size(min = 1)
		public String todo;

		@NotNull
		@Month
		public String month;
	}

	public static class CompleteTaskCommand {
		@NotNull
		public UUID id;
	}

}
