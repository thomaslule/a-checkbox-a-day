package fr.lule.acad.item;

import java.util.Arrays;

import fr.lule.acad.event.FileEventStore;
import fr.lule.acad.item.event.ItemAdded;
import fr.lule.acad.item.event.ItemCancelled;
import fr.lule.acad.item.event.ItemDeleted;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.event.ItemMoved;
import fr.lule.acad.item.event.ItemRestored;
import fr.lule.acad.item.event.ItemTextChanged;
import fr.lule.acad.item.event.TaskCompleted;
import fr.lule.acad.item.event.TaskUncompleted;

public class ItemEventStore extends FileEventStore<ItemEvent, ItemId> {

	public ItemEventStore(String filePath) {
		super(filePath, Arrays.asList(ItemAdded.class, ItemCancelled.class, ItemDeleted.class, ItemMoved.class,
				ItemRestored.class, ItemTextChanged.class, TaskCompleted.class, TaskUncompleted.class));
	}

}
