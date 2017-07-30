package fr.lule.acad.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.TestUtils;
import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemEvent;
import fr.lule.acad.event.ItemId;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.projection.ItemDisplayed;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.web.ItemController.AddItemCommand;
import fr.lule.acad.web.ItemController.GetMonthItemsCommand;
import fr.lule.acad.web.ItemController.IdCommand;
import net.codestory.http.payload.Payload;

@SuppressWarnings("unchecked")
public class ItemControllerShould {

	private InMemoryEventStore<ItemEvent, ItemId> itemEventStore;
	private ItemList list;
	private EventBus bus;
	private ItemController controller;
	private ItemId eventId;

	@Before
	public void before() {
		itemEventStore = new InMemoryEventStore<ItemEvent, ItemId>();
		eventId = new ItemId(UUID.randomUUID());
		itemEventStore.add(new ItemAdded("todo", "2017-01", ItemType.TASK, eventId, TestUtils.DATE_ZERO));
		bus = new EventBus();
		bus.register(itemEventStore);
		list = new ItemList(itemEventStore.getAllEvents());
		bus.register(list);
		controller = new ItemController(list, bus, itemEventStore, TestUtils.DATE_ZERO_FACTORY,
				TestUtils.createIdFactory(new UUID(0, 0)));
	}

	@Test
	public void getItemsReturnsList() {
		Payload res = controller.getMonthItems(new GetMonthItemsCommand("2017-01"));
		assertThat(res.isSuccess()).isTrue();
		assertThat(((List<ItemDisplayed>) res.rawContent()))
				.contains(new ItemDisplayed(eventId.getId(), ItemType.TASK, "todo", "2017-01", false, false, false));
	}

	@Test
	public void getItemsFailsForInvalidCommand() {
		Payload res = controller.getMonthItems(new GetMonthItemsCommand("2017-1"));
		assertThat(res.isError()).isTrue();
	}

	@Test
	public void addItemReturnsItem() {
		AddItemCommand command = new AddItemCommand();
		command.month = "2017-01";
		command.text = "item added";
		command.itemType = ItemType.TASK;
		Payload res = controller.addItem(command);
		assertThat(res.isSuccess()).isTrue();
		assertThat((ItemDisplayed) res.rawContent()).isEqualTo(
				new ItemDisplayed(new UUID(0, 0), ItemType.TASK, "item added", "2017-01", false, false, false));
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
		command.id = eventId.getId();
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
		itemEventStore.add(new TaskCompleted(eventId, new Date(0)));
		IdCommand command = new IdCommand();
		command.id = eventId.getId();
		Payload res = controller.uncompleteTask(command);
		assertThat(res.isSuccess()).isTrue();
		assertThat(list.getList("2017-01").get(0).isCompleted()).isFalse();
	}

}
