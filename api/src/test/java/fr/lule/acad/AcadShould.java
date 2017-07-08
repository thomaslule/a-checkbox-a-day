package fr.lule.acad;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.aggregate.Item;
import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.projection.ItemDisplayed;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;

public class AcadShould {
	
	@Test
	public void displayItemInItemListWhenAddItem() {
		EventsBus bus = new EventsBus(new InMemoryEventStore<IItemEvent>());
		ItemList list = new ItemList(new ArrayList<IItemEvent>());
		list.subscribeTo(bus);
		
		UUID id = Item.add(bus, "buy bread", "2017-05", ItemType.TASK);
		
		assertThat(list.getList("2017-05")).contains(new ItemDisplayed(id, ItemType.TASK, "buy bread", "2017-05", false, false));
	}
	

}
