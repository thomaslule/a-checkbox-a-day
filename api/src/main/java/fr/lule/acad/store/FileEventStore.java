package fr.lule.acad.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lule.acad.event.IEvent;

public class FileEventStore<TAggregateEvent extends IEvent<?>, TID> implements IEventStore<TAggregateEvent, TID> {

	private static final Logger logger = LogManager.getLogger(FileEventStore.class);

	private final File file;
	private final List<TAggregateEvent> eventsCache;
	private final static ObjectMapper MAPPER = new ObjectMapper();
	{
		MAPPER.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	}

	@SuppressWarnings("unchecked")
	public FileEventStore(String filePath) {
		this.file = new File(filePath);
		if (file.exists() && file.length() > 0) {
			try {
				eventsCache = MAPPER.readValue(file, ArrayList.class);
			} catch (IOException e) {
				throw new RuntimeException("could not read the store at " + file.getAbsolutePath(), e);
			}
		} else {
			file.getParentFile().mkdirs();
			eventsCache = new ArrayList<TAggregateEvent>();
		}

		logger.debug("Storing events to: {}", file.getAbsolutePath());
	}

	@Override
	public void add(TAggregateEvent event) {
		eventsCache.add(event);
		try {
			MAPPER.writerWithDefaultPrettyPrinter().writeValue(file, eventsCache);
		} catch (IOException e) {
			throw new RuntimeException("could not save event to " + file.getAbsolutePath(), e);
		}
		logger.debug("Event {} stored to {}", event, file.getAbsolutePath());
	}

	@Override
	public List<TAggregateEvent> getAllEvents() {
		return new ArrayList<TAggregateEvent>(eventsCache);
	}

	@Override
	public List<TAggregateEvent> getEventsFor(TID aggregateId) {
		return eventsCache.stream().filter(event -> event.getAggregateId().equals(aggregateId))
				.collect(Collectors.toList());
	}

	public void emptyStore() {
		eventsCache.clear();
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
		}
	}

}
