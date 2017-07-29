package fr.lule.acad.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemCancelled;
import fr.lule.acad.event.ItemDeleted;
import fr.lule.acad.event.ItemEvent;
import fr.lule.acad.event.ItemId;
import fr.lule.acad.event.ItemRestored;
import fr.lule.acad.event.ItemTextChanged;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;

public class ItemListShould {

	private final Date DATE_ZERO = new Date(0);

	private List<ItemEvent> history;
	private ItemId id;
	private ItemList list;

	@Before
	public void before() {
		history = new ArrayList<ItemEvent>();
		id = new ItemId(UUID.randomUUID());
		history.add(new ItemAdded("buy bread", "2017-05", ItemType.TASK, id, DATE_ZERO));
	}

	private ItemDisplayed getItem() {
		return list.getList("2017-05").stream().filter(t -> t.getId().equals(id.getId())).findFirst().get();
	}

	@Test
	public void addItemInListWhenItemAdded() {
		list = new ItemList(history);

		ItemId id2 = new ItemId(UUID.randomUUID());

		list.handleItemAdded(new ItemAdded("something", "2017-05", ItemType.TASK, id2, DATE_ZERO));

		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id2.getId(), ItemType.TASK, "something", "2017-05", false, false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}

	@Test
	public void haveItemInListWhenHistoryContainsItemAdded() {
		list = new ItemList(history);
		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id.getId(), ItemType.TASK, "buy bread", "2017-05", false, false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}

	@Test
	public void setItemCancelledWhenItemCancelled() {
		list = new ItemList(history);
		list.handleItemCancelled(new ItemCancelled(id, DATE_ZERO));

		assertThat(getItem().isCancelled()).isTrue();
	}

	@Test
	public void setItemRestoredWhenItemRestored() {
		history.add(new ItemCancelled(id, DATE_ZERO));
		list = new ItemList(history);

		list.handleItemRestored(new ItemRestored(id, DATE_ZERO));

		assertThat(getItem().isCancelled()).isFalse();
	}

	@Test
	public void removeItemWhenItemDeleted() {
		list = new ItemList(history);

		list.handleItemDeleted(new ItemDeleted(id, DATE_ZERO));

		assertThat(list.getList("2017-05")).isEmpty();
	}

	@Test
	public void setTaskCompletedWhenTaskCompleted() {
		ItemId id2 = new ItemId(UUID.randomUUID());
		history.add(new ItemAdded("pet cat", "2017-05", ItemType.TASK, id2, DATE_ZERO));
		list = new ItemList(history);

		list.handleTaskCompleted(new TaskCompleted(id, DATE_ZERO));

		assertThat(list.getList("2017-05").stream().filter(t -> t.getId().equals(id2.getId())).findFirst().get()
				.isCompleted()).isFalse();
		assertThat(getItem().isCompleted()).isTrue();
	}

	@Test
	public void setTaskUncompletedWhenTaskUncompleted() {
		history.add(new TaskCompleted(id, DATE_ZERO));
		list = new ItemList(history);

		list.handleTaskUncompleted(new TaskUncompleted(id, DATE_ZERO));

		assertThat(getItem().isCompleted()).isFalse();
	}

	@Test
	public void updateTextWhenItemTextChanged() {
		list = new ItemList(history);

		list.handleItemTextChanged(new ItemTextChanged("new text", id, DATE_ZERO));

		assertThat(getItem().getText()).isEqualTo("new text");
	}

}
