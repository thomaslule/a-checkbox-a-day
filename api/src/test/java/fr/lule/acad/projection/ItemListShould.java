package fr.lule.acad.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.TaskCompleted;

public class ItemListShould {
	
	@Test
	public void addItemInListWhenItemAdded() {
		ItemList list = new ItemList(new ArrayList<IItemEvent>());
		UUID id = UUID.randomUUID();
		
		list.handle(new ItemAdded(id, "buy bread", "2017-05"));
		
		assertThat(list.getList("2017-05")).contains(new ItemDisplayed(id, "buy bread", "2017-05", false));
		assertThat(list.getList("2017-06")).isEmpty();
	}
	
	@Test
	public void haveItemInListWhenHistoryContainsItemAdded() {
		List<IItemEvent> history = new ArrayList<IItemEvent>();
		UUID id = UUID.randomUUID();
		history.add(new ItemAdded(id, "buy bread", "2017-05"));
		
		ItemList list = new ItemList(history);

		assertThat(list.getList("2017-05")).contains(new ItemDisplayed(id, "buy bread", "2017-05", false));
		assertThat(list.getList("2017-06")).isEmpty();
	}
	
	@Test
	public void setTaskCompletedWhenTaskCompleted() {
		List<IItemEvent> history = new ArrayList<IItemEvent>();
		UUID id1 = UUID.randomUUID();
		history.add(new ItemAdded(id1, "buy bread", "2017-05"));
		UUID id2 = UUID.randomUUID();
		history.add(new ItemAdded(id2, "pet cat", "2017-05"));
		ItemList list = new ItemList(history);
		
		list.handle(new TaskCompleted(id1));
		
		assertThat(list.getList("2017-05").stream().filter(t -> t.getId().equals(id1)).findFirst().get().isCompleted()).isTrue();
	}

}
