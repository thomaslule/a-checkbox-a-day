package fr.lule.acad.web;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) {
		
		InMemoryEventStore<IItemEvent> itemEventStore = new InMemoryEventStore<IItemEvent>();
		ItemList list = new ItemList(itemEventStore.getAllEvents());
		EventsBus bus = new EventsBus(itemEventStore);
		bus.subscribe(list);
		
		new WebServer()
		.configure(routes -> {
			routes.add(new ItemController(list, bus, itemEventStore));
		})
		.start();
	}
	
}
