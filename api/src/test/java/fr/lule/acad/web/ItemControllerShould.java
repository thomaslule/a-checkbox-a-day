package fr.lule.acad.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.projection.ItemDisplayed;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;
import fr.lule.acad.web.ItemController.AddItemCommand;
import fr.lule.acad.web.ItemController.IdCommand;
import fr.lule.acad.web.ItemController.GetMonthItemsCommand;
import net.codestory.http.payload.Payload;

@SuppressWarnings("unchecked")
public class ItemControllerShould {

	private InMemoryEventStore<IItemEvent> itemEventStore;
	private ItemList list;
	private EventsBus bus;
	private ItemController controller;
	UUID eventId;

	@Before
	public void before() {
		itemEventStore = new InMemoryEventStore<IItemEvent>();
		eventId = UUID.randomUUID();
		itemEventStore.add(new ItemAdded(eventId, "todo", "2017-01", ItemType.TASK));
		list = new ItemList(itemEventStore.getAllEvents());
		bus = new EventsBus(itemEventStore);
		bus.subscribe(list);
		controller = new ItemController(list, bus, itemEventStore);
	}

	@Test
	public void getItemsReturnsList() {
		Payload res = controller.getItems(new GetMonthItemsCommand("2017-01"));
		assertThat(res.isSuccess()).isTrue();
		assertThat(((List<ItemDisplayed>) res.rawContent()).get(0).getText()).isEqualTo("todo");
	}

	@Test
	public void getItemsFailsForInvalidCommand() {
		Payload res = controller.getItems(new GetMonthItemsCommand("2017-1"));
		assertThat(res.isError()).isTrue();
	}

	@Test
	public void addItemReturnsItem() {
		AddItemCommand command = new AddItemCommand();
		command.month = "2017-01";
		command.text = "todo";
		command.itemType = ItemType.TASK;
		Payload res = controller.addItem(command);
		assertThat(res.isSuccess()).isTrue();
		assertThat(list.getList("2017-01").size()).isEqualTo(2);
	}

	@Test
	public void addItemFailsForInvalidCommand() {
		AddItemCommand command = new AddItemCommand();
		command.month = "2017-1";
		command.text = "todo";
		Payload res = controller.addItem(command);
		assertThat(res.isError()).isTrue();
		assertThat(list.getList("2017-01").size()).isEqualTo(1);
	}

	@Test
	public void completeTaskReturnsOk() {
		IdCommand command = new IdCommand();
		command.id = eventId;
		Payload res = controller.completeTask(command);
		assertThat(res.isSuccess()).isTrue();
		assertThat(list.getList("2017-01").get(0).isCompleted()).isTrue();
	}

	@Test
	public void completeTaskFailsForInvalidUUID() {
		IdCommand command = new IdCommand();
		command.id = UUID.randomUUID(); // bad uuid
		Payload res = controller.completeTask(command);
		assertThat(res.isError()).isTrue();
		assertThat(list.getList("2017-01").get(0).isCompleted()).isFalse();
	}

	@Test
	public void completeTaskFailsForNullUUID() {
		IdCommand command = new IdCommand();
		Payload res = controller.completeTask(command);
		assertThat(res.isError()).isTrue();
		assertThat(list.getList("2017-01").get(0).isCompleted()).isFalse();
	}

	@Test
	public void uncompleteTaskReturnsOk() {
		itemEventStore.add(new TaskCompleted(eventId));
		IdCommand command = new IdCommand();
		command.id = eventId;
		Payload res = controller.uncompleteTask(command);
		assertThat(res.isSuccess()).isTrue();
		assertThat(list.getList("2017-01").get(0).isCompleted()).isFalse();
	}

}
