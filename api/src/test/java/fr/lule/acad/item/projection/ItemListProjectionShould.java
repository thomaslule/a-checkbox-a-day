package fr.lule.acad.item.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.item.ItemId;
import fr.lule.acad.item.ItemType;
import fr.lule.acad.item.event.ItemAdded;
import fr.lule.acad.item.event.ItemCancelled;
import fr.lule.acad.item.event.ItemDeleted;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.event.ItemRestored;
import fr.lule.acad.item.event.ItemTextChanged;
import fr.lule.acad.item.event.TaskCompleted;
import fr.lule.acad.item.event.TaskUncompleted;
import fr.lule.acad.item.projection.ItemDisplayed;
import fr.lule.acad.item.projection.ItemListProjection;

public class ItemListProjectionShould {

	private final Date DATE_ZERO = new Date(0);

	private List<ItemEvent> history;
	private ItemId id;
	private ItemListProjection list;

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
		list = new ItemListProjection(history);

		ItemId id2 = new ItemId(UUID.randomUUID());

		list.handleItemAdded(new ItemAdded("something", "2017-05", ItemType.TASK, id2, DATE_ZERO));

		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id2.getId(), ItemType.TASK, "something", "2017-05", false, false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}

	@Test
	public void haveItemInListWhenHistoryContainsItemAdded() {
		list = new ItemListProjection(history);
		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id.getId(), ItemType.TASK, "buy bread", "2017-05", false, false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}

	@Test
	public void setItemCancelledWhenItemCancelled() {
		list = new ItemListProjection(history);
		list.handleItemCancelled(new ItemCancelled(id, DATE_ZERO));

		assertThat(getItem().isCancelled()).isTrue();
	}

	@Test
	public void setItemRestoredWhenItemRestored() {
		history.add(new ItemCancelled(id, DATE_ZERO));
		list = new ItemListProjection(history);

		list.handleItemRestored(new ItemRestored(id, DATE_ZERO));

		assertThat(getItem().isCancelled()).isFalse();
	}

	@Test
	public void removeItemWhenItemDeleted() {
		list = new ItemListProjection(history);

		list.handleItemDeleted(new ItemDeleted(id, DATE_ZERO));

		assertThat(list.getList("2017-05")).isEmpty();
	}

	@Test
	public void setTaskCompletedWhenTaskCompleted() {
		ItemId id2 = new ItemId(UUID.randomUUID());
		history.add(new ItemAdded("pet cat", "2017-05", ItemType.TASK, id2, DATE_ZERO));
		list = new ItemListProjection(history);

		list.handleTaskCompleted(new TaskCompleted(id, DATE_ZERO));

		assertThat(list.getList("2017-05").stream().filter(t -> t.getId().equals(id2.getId())).findFirst().get()
				.isCompleted()).isFalse();
		assertThat(getItem().isCompleted()).isTrue();
	}

	@Test
	public void setTaskUncompletedWhenTaskUncompleted() {
		history.add(new TaskCompleted(id, DATE_ZERO));
		list = new ItemListProjection(history);

		list.handleTaskUncompleted(new TaskUncompleted(id, DATE_ZERO));

		assertThat(getItem().isCompleted()).isFalse();
	}

	@Test
	public void updateTextWhenItemTextChanged() {
		list = new ItemListProjection(history);

		list.handleItemTextChanged(new ItemTextChanged("new text", id, DATE_ZERO));

		assertThat(getItem().getText()).isEqualTo("new text");
	}

}
