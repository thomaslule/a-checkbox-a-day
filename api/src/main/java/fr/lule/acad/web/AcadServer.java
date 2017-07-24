package fr.lule.acad.web;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.projection.JournalProjection;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) {

		InMemoryEventStore<IItemEvent> itemEventStore = new InMemoryEventStore<IItemEvent>();
		InMemoryEventStore<IJournalEvent> journalEventStore = new InMemoryEventStore<IJournalEvent>();

		EventsBus bus = new EventsBus(itemEventStore, journalEventStore);

		ItemList list = new ItemList(itemEventStore.getAllEvents());
		list.subscribeTo(bus);

		JournalProjection journalProjection = new JournalProjection(journalEventStore.getAllEvents());
		journalProjection.subscribeTo(bus);

		new WebServer().configure(routes -> {
			routes.add(new ItemController(list, bus, itemEventStore));
			routes.add(new JournalController(bus, journalEventStore, journalProjection));
		}).start();
	}

}
