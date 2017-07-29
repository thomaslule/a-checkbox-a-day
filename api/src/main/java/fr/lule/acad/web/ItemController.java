package fr.lule.acad.web;

import java.util.UUID;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.aggregate.Item;
import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.ItemEvent;
import fr.lule.acad.event.ItemId;
import fr.lule.acad.projection.ItemDisplayed;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.util.DateFactory;
import fr.lule.acad.util.IDateFactory;
import fr.lule.acad.web.validation.CommandRunner;
import fr.lule.acad.web.validation.Month;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.constants.HttpStatus;
import net.codestory.http.payload.Payload;

@Prefix("/api/Item")
public class ItemController {

	private final IDateFactory dateFactory = new DateFactory();

	private ItemList list;
	private EventBus bus;
	private IEventStore<ItemEvent, ItemId> itemEventStore;
	private Validator validator;

	public ItemController(ItemList list, EventBus bus, IEventStore<ItemEvent, ItemId> itemEventStore) {
		this.list = list;
		this.bus = bus;
		this.itemEventStore = itemEventStore;
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Get("/GetMonthItems/:month")
	public Payload getMonthItems(GetMonthItemsCommand month) {
		return CommandRunner.ifValid(month, validator, (c) -> {
			return new Payload(null, list.getList(month.month));
		});
	}

	@Post("/AddItem")
	public Payload addItem(AddItemCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			ItemId id = Item.add(bus, c.text, c.month, c.itemType, dateFactory);
			return new Payload(null, new ItemDisplayed(id.getId(), c.itemType, c.text, c.month, false, false, false),
					HttpStatus.CREATED);
		});
	}

	@Post("/CancelItem")
	public Payload cancelItem(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item item = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory);
			if (item.cancel(bus)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/RestoreItem")
	public Payload restoreItem(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item item = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory);
			if (item.restore(bus)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/DeleteItem")
	public Payload deleteItem(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item item = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory);
			if (item.delete(bus)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/CompleteTask")
	public Payload completeTask(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item task = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory);
			if (task.completeTask(bus)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/UncompleteTask")
	public Payload uncompleteTask(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item task = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory);
			if (task.uncompleteTask(bus)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/ChangeItemText")
	public Payload changeItemText(ChangeItemTextCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item task = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory);
			if (task.changeItemText(bus, command.newText)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	public static class GetMonthItemsCommand {
		@NotNull
		@Month
		public String month;

		public GetMonthItemsCommand(String month) {
			this.month = month;
		}

	}

	public static class AddItemCommand {
		@NotNull
		@Size(min = 1)
		public String text;

		@NotNull
		@Month
		public String month;

		@NotNull
		public ItemType itemType;
	}

	public static class IdCommand {
		@NotNull
		public UUID id;
	}

	public static class ChangeItemTextCommand {
		@NotNull
		public UUID id;

		@NotNull
		@Size(min = 1)
		public String newText;
	}
}
