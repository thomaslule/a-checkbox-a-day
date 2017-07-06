package fr.lule.acad.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.projection.TaskDisplayed;
import fr.lule.acad.projection.TaskList;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;
import fr.lule.acad.web.TasksController.AddTaskCommand;
import fr.lule.acad.web.TasksController.CompleteTaskCommand;
import fr.lule.acad.web.TasksController.GetMonthCommand;
import net.codestory.http.payload.Payload;

@SuppressWarnings("unchecked")
public class TasksControllerShould {

	private InMemoryEventStore<ITaskEvent> taskEventStore;
	private TaskList list;
	private EventsBus bus;
	private TasksController controller;
	UUID eventId;

	@Before
	public void before() {
		taskEventStore = new InMemoryEventStore<ITaskEvent>();
		eventId = UUID.randomUUID();
		taskEventStore.add(new TaskAdded(eventId, "todo", "2017-01"));
		list = new TaskList(taskEventStore.getAllEvents());
		bus = new EventsBus(taskEventStore);
		bus.subscribe(list);
		controller = new TasksController(list, bus, taskEventStore);
	}

	@Test
	public void getTasksReturnsList() {
		Payload res = controller.getTasks(new GetMonthCommand("2017-01"));
		assertThat(res.isSuccess()).isTrue();
		assertThat(((List<TaskDisplayed>) res.rawContent()).get(0).getTodo()).isEqualTo("todo");
	}

	@Test
	public void getTasksFailsForInvalidCommand() {
		Payload res = controller.getTasks(new GetMonthCommand("2017-1"));
		assertThat(res.isError()).isTrue();
	}

	@Test
	public void addTaskReturnsTask() {
		AddTaskCommand command = new AddTaskCommand();
		command.month = "2017-01";
		command.todo = "todo";
		Payload res = controller.addTask(command);
		assertThat(res.isSuccess()).isTrue();
		assertThat(list.getList("2017-01").size()).isEqualTo(2);
	}

	@Test
	public void addTaskFailsForInvalidCommand() {
		AddTaskCommand command = new AddTaskCommand();
		command.month = "2017-1";
		command.todo = "todo";
		Payload res = controller.addTask(command);
		assertThat(res.isError()).isTrue();
		assertThat(list.getList("2017-01").size()).isEqualTo(1);
	}

	@Test
	public void completeTaskReturnsOk() {
		CompleteTaskCommand command = new CompleteTaskCommand();
		command.id = eventId;
		Payload res = controller.completeTask(command);
		assertThat(res.isSuccess()).isTrue();
		assertThat(list.getList("2017-01").get(0).isCompleted()).isTrue();
	}

	@Test
	public void completeTaskFailsForInvalidUUID() {
		CompleteTaskCommand command = new CompleteTaskCommand();
		command.id = UUID.randomUUID(); // bad uuid
		Payload res = controller.completeTask(command);
		assertThat(res.isError()).isTrue();
		assertThat(list.getList("2017-01").get(0).isCompleted()).isFalse();
	}

	@Test
	public void completeTaskFailsForNullUUID() {
		CompleteTaskCommand command = new CompleteTaskCommand();
		Payload res = controller.completeTask(command);
		assertThat(res.isError()).isTrue();
		assertThat(list.getList("2017-01").get(0).isCompleted()).isFalse();
	}

}
