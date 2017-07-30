package fr.lule.acad.web;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.ItemEvent;
import fr.lule.acad.event.ItemId;
import fr.lule.acad.event.JournalDay;
import fr.lule.acad.event.JournalEvent;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.projection.JournalProjection;
import fr.lule.acad.store.FileEventStore;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.store.ItemEventSaver;
import fr.lule.acad.store.JournalEventSaver;
import fr.lule.acad.util.DateFactory;
import fr.lule.acad.util.IDateFactory;
import fr.lule.acad.util.IIdFactory;
import fr.lule.acad.util.IdFactory;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) throws ParseException {

		IDateFactory dateFactory = new DateFactory();
		IIdFactory idFactory = new IdFactory();

		Options options = new Options();
		options.addOption("s", "store", true, "Path to store folder");

		CommandLine cmd = new DefaultParser().parse(options, args);

		IEventStore<ItemEvent, ItemId> itemEventStore;
		IEventStore<JournalEvent, JournalDay> journalEventStore;

		if (cmd.hasOption("store")) {
			itemEventStore = new FileEventStore<ItemEvent, ItemId>(
					cmd.getOptionValue("store") + File.separator + "items.json");
			journalEventStore = new FileEventStore<JournalEvent, JournalDay>(
					cmd.getOptionValue("store") + File.separator + "journal.json");
		} else {
			itemEventStore = new InMemoryEventStore<ItemEvent, ItemId>();
			journalEventStore = new InMemoryEventStore<JournalEvent, JournalDay>();
		}

		EventBus bus = new EventBus();
		bus.register(new ItemEventSaver(itemEventStore));
		bus.register(new JournalEventSaver(journalEventStore));

		ItemList list = new ItemList(itemEventStore.getAllEvents());
		bus.register(list);

		JournalProjection journalProjection = new JournalProjection(journalEventStore.getAllEvents());
		bus.register(journalProjection);

		new WebServer().configure(routes -> {
			routes.add(new ItemController(list, bus, itemEventStore, dateFactory, idFactory));
			routes.add(new JournalController(bus, journalEventStore, journalProjection, dateFactory));
		}).start();
	}

}
