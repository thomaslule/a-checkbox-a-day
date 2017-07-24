package fr.lule.acad.web;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.projection.JournalProjection;
import fr.lule.acad.store.InMemoryEventStore;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) {

		InMemoryEventStore<IItemEvent> itemEventStore = new InMemoryEventStore<IItemEvent>();
		InMemoryEventStore<IJournalEvent> journalEventStore = new InMemoryEventStore<IJournalEvent>();

		EventBus bus = new EventBus();
		bus.register(itemEventStore);
		bus.register(journalEventStore);

		ItemList list = new ItemList(itemEventStore.getAllEvents());
		bus.register(list);

		JournalProjection journalProjection = new JournalProjection(journalEventStore.getAllEvents());
		bus.register(journalProjection);

		new WebServer().configure(routes -> {
			routes.add(new ItemController(list, bus, itemEventStore));
			routes.add(new JournalController(bus, journalEventStore, journalProjection));
		}).start();
	}

}
