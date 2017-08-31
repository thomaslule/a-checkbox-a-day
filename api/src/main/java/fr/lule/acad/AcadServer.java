package fr.lule.acad;

import java.io.File;

import org.apache.commons.cli.ParseException;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.FileEventStore;
import fr.lule.acad.event.IEventStore;
import fr.lule.acad.event.InMemoryEventStore;
import fr.lule.acad.item.ItemController;
import fr.lule.acad.item.ItemEventSaver;
import fr.lule.acad.item.ItemId;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.projection.ItemListProjection;
import fr.lule.acad.journal.JournalController;
import fr.lule.acad.journal.JournalDay;
import fr.lule.acad.journal.JournalEventSaver;
import fr.lule.acad.journal.event.JournalEvent;
import fr.lule.acad.journal.projection.JournalProjection;
import fr.lule.acad.util.DateFactory;
import fr.lule.acad.util.IDateFactory;
import fr.lule.acad.util.IIdFactory;
import fr.lule.acad.util.IdFactory;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) throws ParseException {

		IDateFactory dateFactory = new DateFactory();
		IIdFactory idFactory = new IdFactory();

		AcadOptions options = new AcadOptions(args);

		IEventStore<ItemEvent, ItemId> itemEventStore;
		IEventStore<JournalEvent, JournalDay> journalEventStore;

		if (options.hasStore()) {
			itemEventStore = new FileEventStore<ItemEvent, ItemId>(options.getStore() + File.separator + "items.json");
			journalEventStore = new FileEventStore<JournalEvent, JournalDay>(
					options.getStore() + File.separator + "journal.json");
		} else {
			itemEventStore = new InMemoryEventStore<ItemEvent, ItemId>();
			journalEventStore = new InMemoryEventStore<JournalEvent, JournalDay>();
		}

		EventBus bus = new EventBus();
		bus.register(new ItemEventSaver(itemEventStore));
		bus.register(new JournalEventSaver(journalEventStore));

		ItemListProjection itemListProjection = new ItemListProjection(itemEventStore.getAllEvents());
		bus.register(itemListProjection);

		JournalProjection journalProjection = new JournalProjection(journalEventStore.getAllEvents());
		bus.register(journalProjection);

		new WebServer().configure(routes -> {
			routes.add(new ItemController(itemListProjection, bus, itemEventStore, dateFactory, idFactory));
			routes.add(new JournalController(bus, journalEventStore, journalProjection, dateFactory));
		}).start();
	}

}
