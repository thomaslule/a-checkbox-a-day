package fr.lule.acad.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemCancelled;
import fr.lule.acad.event.ItemDeleted;
import fr.lule.acad.event.ItemRestored;
import fr.lule.acad.event.ItemTextChanged;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;

public class ItemListShould {

	private List<IItemEvent> history;
	private UUID id;
	private ItemList list;

	@Before
	public void before() {
		history = new ArrayList<IItemEvent>();
		id = UUID.randomUUID();
		history.add(new ItemAdded(id, "buy bread", "2017-05", ItemType.TASK));
	}

	private ItemDisplayed getItem() {
		return list.getList("2017-05").stream().filter(t -> t.getId().equals(id)).findFirst().get();
	}

	@Test
	public void addItemInListWhenItemAdded() {
		list = new ItemList(history);

		UUID id = UUID.randomUUID();

		list.handleItemAdded(new ItemAdded(id, "something", "2017-05", ItemType.TASK));

		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id, ItemType.TASK, "something", "2017-05", false, false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}

	@Test
	public void haveItemInListWhenHistoryContainsItemAdded() {
		list = new ItemList(history);
		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id, ItemType.TASK, "buy bread", "2017-05", false, false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}

	@Test
	public void setItemCancelledWhenItemCancelled() {
		list = new ItemList(history);
		list.handleItemCancelled(new ItemCancelled(id));

		assertThat(getItem().isCancelled()).isTrue();
	}

	@Test
	public void setItemRestoredWhenItemRestored() {
		history.add(new ItemCancelled(id));
		list = new ItemList(history);

		list.handleItemRestored(new ItemRestored(id));

		assertThat(getItem().isCancelled()).isFalse();
	}

	@Test
	public void removeItemWhenItemDeleted() {
		list = new ItemList(history);

		list.handleItemDeleted(new ItemDeleted(id));

		assertThat(list.getList("2017-05")).isEmpty();
	}

	@Test
	public void setTaskCompletedWhenTaskCompleted() {
		UUID id2 = UUID.randomUUID();
		history.add(new ItemAdded(id2, "pet cat", "2017-05", ItemType.TASK));
		list = new ItemList(history);

		list.handleTaskCompleted(new TaskCompleted(id));

		assertThat(list.getList("2017-05").stream().filter(t -> t.getId().equals(id2)).findFirst().get().isCompleted())
				.isFalse();
		assertThat(getItem().isCompleted()).isTrue();
	}

	@Test
	public void setTaskUncompletedWhenTaskUncompleted() {
		history.add(new TaskCompleted(id));
		list = new ItemList(history);

		list.handleTaskUncompleted(new TaskUncompleted(id));

		assertThat(getItem().isCompleted()).isFalse();
	}

	@Test
	public void updateTextWhenItemTextChanged() {
		list = new ItemList(history);

		list.handleItemTextChanged(new ItemTextChanged(id, "new text"));

		assertThat(getItem().getText()).isEqualTo("new text");
	}

}
