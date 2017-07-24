package fr.lule.acad.web;

import java.util.UUID;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fr.lule.acad.aggregate.Item;
import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.stream.IEventPublisher;
import fr.lule.acad.web.validation.CommandRunner;
import fr.lule.acad.web.validation.Month;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.constants.HttpStatus;
import net.codestory.http.payload.Payload;

@Prefix("/api/Item")
public class ItemController {

	private ItemList list;
	private IEventPublisher publisher;
	private IEventStore<IItemEvent> itemEventStore;
	private Validator validator;

	public ItemController(ItemList list, IEventPublisher publisher, IEventStore<IItemEvent> itemEventStore) {
		this.list = list;
		this.publisher = publisher;
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
			UUID id = Item.add(publisher, c.text, c.month, c.itemType);
			// TODO use a repository instead of getting the item from the list
			return new Payload(null, list.getItem(id), HttpStatus.CREATED);
		});
	}

	@Post("/CancelItem")
	public Payload cancelItem(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item item = new Item(itemEventStore.getEventsFor(command.id));
			if (item.cancel(publisher)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/RestoreItem")
	public Payload restoreItem(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item item = new Item(itemEventStore.getEventsFor(command.id));
			if (item.restore(publisher)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/DeleteItem")
	public Payload deleteItem(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item item = new Item(itemEventStore.getEventsFor(command.id));
			if (item.delete(publisher)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/CompleteTask")
	public Payload completeTask(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item task = new Item(itemEventStore.getEventsFor(command.id));
			if (task.completeTask(publisher)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/UncompleteTask")
	public Payload uncompleteTask(IdCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item task = new Item(itemEventStore.getEventsFor(command.id));
			if (task.uncompleteTask(publisher)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	@Post("/ChangeItemText")
	public Payload changeItemText(ChangeItemTextCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			Item task = new Item(itemEventStore.getEventsFor(command.id));
			if (task.changeItemText(command.newText, publisher)) {
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
