package fr.lule.acad.item;

import java.util.UUID;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.IEventStore;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.projection.ItemDisplayed;
import fr.lule.acad.item.projection.ItemListProjection;
import fr.lule.acad.util.IDateFactory;
import fr.lule.acad.util.IIdFactory;
import fr.lule.acad.web.validation.CommandRunner;
import fr.lule.acad.web.validation.Month;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.constants.HttpStatus;
import net.codestory.http.payload.Payload;

@Prefix("/api/Item")
public class ItemController {

	private final IDateFactory dateFactory;
	private final IIdFactory idFactory;

	private ItemListProjection list;
	private EventBus bus;
	private IEventStore<ItemEvent, ItemId> itemEventStore;
	private Validator validator;

	public ItemController(ItemListProjection list, EventBus bus, IEventStore<ItemEvent, ItemId> itemEventStore,
			IDateFactory dateFactory, IIdFactory idFactory) {
		this.list = list;
		this.bus = bus;
		this.itemEventStore = itemEventStore;
		this.dateFactory = dateFactory;
		this.idFactory = idFactory;
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
			ItemId id = Item.add(bus, c.text, c.month, c.itemType, dateFactory, idFactory);
			return new Payload(null, new ItemDisplayed(id.getId(), c.itemType, c.text, c.month, false, false, false),
					HttpStatus.CREATED);
		});
	}

	@Post("/CancelItem")
	public Payload cancelItem(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item item = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory, idFactory);
			if (item.cancel(bus)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/MoveItem")
	public Payload moveItem(MoveItemCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item item = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory, idFactory);
			if (item.moveItem(bus, command.newMonth)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/RestoreItem")
	public Payload restoreItem(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item item = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory, idFactory);
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
			Item item = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory, idFactory);
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
			Item task = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory, idFactory);
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
			Item task = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory, idFactory);
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
			Item task = new Item(itemEventStore.getEventsFor(new ItemId(command.id)), dateFactory, idFactory);
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

	public static class MoveItemCommand {
		@NotNull
		public UUID id;

		@NotNull
		@Month
		public String newMonth;
	}

	public static class ChangeItemTextCommand {
		@NotNull
		public UUID id;

		@NotNull
		@Size(min = 1)
		public String newText;
	}
}
