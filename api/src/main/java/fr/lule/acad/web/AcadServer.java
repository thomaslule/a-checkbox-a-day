package fr.lule.acad.web;

import java.io.File;
import java.util.UUID;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.projection.JournalProjection;
import fr.lule.acad.store.FileEventStore;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.store.ItemEventSaver;
import fr.lule.acad.store.JournalEventSaver;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) throws ParseException {

		Options options = new Options();
		options.addOption("s", "store", true, "Path to store folder");

		CommandLine cmd = new DefaultParser().parse(options, args);

		IEventStore<IItemEvent, UUID> itemEventStore;
		IEventStore<IJournalEvent, String> journalEventStore;

		if (cmd.hasOption("store")) {
			itemEventStore = new FileEventStore<IItemEvent, UUID>(
					cmd.getOptionValue("store") + File.separator + "items");
			journalEventStore = new FileEventStore<IJournalEvent, String>(
					cmd.getOptionValue("store") + File.separator + "journal");
		} else {
			itemEventStore = new InMemoryEventStore<IItemEvent, UUID>();
			journalEventStore = new InMemoryEventStore<IJournalEvent, String>();
		}

		EventBus bus = new EventBus();
		bus.register(new ItemEventSaver(itemEventStore));
		bus.register(new JournalEventSaver(journalEventStore));

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
