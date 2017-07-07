package fr.lule.acad.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemCancelled;
import fr.lule.acad.event.ItemRestored;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;

public class ItemListShould {
	
	@Test
	public void addItemInListWhenItemAdded() {
		ItemList list = new ItemList(new ArrayList<IItemEvent>());
		UUID id = UUID.randomUUID();
		
		list.handle(new ItemAdded(id, "buy bread", "2017-05", ItemType.TASK));
		
		assertThat(list.getList("2017-05")).contains(new ItemDisplayed(id, ItemType.TASK, "buy bread", "2017-05", false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}
	
	@Test
	public void haveItemInListWhenHistoryContainsItemAdded() {
		List<IItemEvent> history = new ArrayList<IItemEvent>();
		UUID id = UUID.randomUUID();
		history.add(new ItemAdded(id, "buy bread", "2017-05", ItemType.TASK));
		
		ItemList list = new ItemList(history);

		assertThat(list.getList("2017-05")).contains(new ItemDisplayed(id, ItemType.TASK, "buy bread", "2017-05", false, false));
		assertThat(list.getList("2017-06")).isEmpty();
	}

	@Test
	public void setItemCancelledWhenItemCancelled() {
		List<IItemEvent> history = new ArrayList<IItemEvent>();
		UUID id = UUID.randomUUID();
		history.add(new ItemAdded(id, "buy bread", "2017-05", ItemType.TASK));
		ItemList list = new ItemList(history);
		
		list.handle(new ItemCancelled(id));
		
		assertThat(list.getList("2017-05").stream().filter(t -> t.getId().equals(id)).findFirst().get().isCancelled()).isTrue();
	}

	@Test
	public void setItemRestoredWhenItemRestored() {
		List<IItemEvent> history = new ArrayList<IItemEvent>();
		UUID id = UUID.randomUUID();
		history.add(new ItemAdded(id, "buy bread", "2017-05", ItemType.TASK));
		history.add(new ItemCancelled(id));
		ItemList list = new ItemList(history);
		
		list.handle(new ItemRestored(id));
		
		assertThat(list.getList("2017-05").stream().filter(t -> t.getId().equals(id)).findFirst().get().isCancelled()).isFalse();
	}

	@Test
	public void setTaskCompletedWhenTaskCompleted() {
		List<IItemEvent> history = new ArrayList<IItemEvent>();
		UUID id1 = UUID.randomUUID();
		history.add(new ItemAdded(id1, "buy bread", "2017-05", ItemType.TASK));
		UUID id2 = UUID.randomUUID();
		history.add(new ItemAdded(id2, "pet cat", "2017-05", ItemType.TASK));
		ItemList list = new ItemList(history);
		
		list.handle(new TaskCompleted(id1));
		
		assertThat(list.getList("2017-05").stream().filter(t -> t.getId().equals(id1)).findFirst().get().isCompleted()).isTrue();
	}

	@Test
	public void setTaskUncompletedWhenTaskUncompleted() {
		List<IItemEvent> history = new ArrayList<IItemEvent>();
		UUID id = UUID.randomUUID();
		history.add(new ItemAdded(id, "buy bread", "2017-05", ItemType.TASK));
		history.add(new TaskCompleted(id));
		ItemList list = new ItemList(history);
		
		list.handle(new TaskUncompleted(id));
		
		assertThat(list.getList("2017-05").stream().filter(t -> t.getId().equals(id)).findFirst().get().isCompleted()).isFalse();
	}

}
