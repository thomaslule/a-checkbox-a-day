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
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;

public class ItemListShould {
	
	private List<IItemEvent> history;
	private UUID id;

	@Before
	public void before() {
		history = new ArrayList<IItemEvent>();
		id = UUID.randomUUID();
		history.add(new ItemAdded(id, "buy bread", "2017-05", ItemType.TASK));
	}
	
	private ItemDisplayed getItem(ItemList list) {
		return list.getList("2017-05").stream().filter(t -> t.getId().equals(id)).findFirst().get();
	}

	@Test
	public void addItemInListWhenItemAdded() {
		ItemList list = new ItemList(history);
		UUID id = UUID.randomUUID();

		list.handle(new ItemAdded(id, "something", "2017-05", ItemType.TASK));

		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id, ItemType.TASK, "something", "2017-05", false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}

	@Test
	public void haveItemInListWhenHistoryContainsItemAdded() {
		ItemList list = new ItemList(history);
		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id, ItemType.TASK, "buy bread", "2017-05", false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}

	@Test
	public void setItemCancelledWhenItemCancelled() {
		ItemList list = new ItemList(history);
		list.handle(new ItemCancelled(id));

		assertThat(getItem(list).isCancelled()).isTrue();
	}

	@Test
	public void setItemRestoredWhenItemRestored() {
		history.add(new ItemCancelled(id));
		ItemList list = new ItemList(history);

		list.handle(new ItemRestored(id));

		assertThat(getItem(list).isCancelled()).isFalse();
	}
	
	@Test
	public void removeItemWhenItemDeleted() {
		ItemList list = new ItemList(history);
		
		list.handle(new ItemDeleted(id));
		
		assertThat(list.getList("2017-05")).isEmpty();
	}

	@Test
	public void setTaskCompletedWhenTaskCompleted() {
		UUID id2 = UUID.randomUUID();
		history.add(new ItemAdded(id2, "pet cat", "2017-05", ItemType.TASK));
		ItemList list = new ItemList(history);

		list.handle(new TaskCompleted(id));

		assertThat(list.getList("2017-05").stream().filter(t -> t.getId().equals(id2)).findFirst().get().isCompleted())
				.isFalse();
		assertThat(getItem(list).isCompleted()).isTrue();
	}

	@Test
	public void setTaskUncompletedWhenTaskUncompleted() {
		history.add(new TaskCompleted(id));
		ItemList list = new ItemList(history);

		list.handle(new TaskUncompleted(id));

		assertThat(getItem(list).isCompleted()).isFalse();
	}

}
