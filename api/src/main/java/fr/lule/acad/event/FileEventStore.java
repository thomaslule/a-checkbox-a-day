package fr.lule.acad.event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Functions;

public class FileEventStore<TAggregateEvent extends IEvent, TID> implements IEventStore<TAggregateEvent, TID> {

	private static final Logger logger = LogManager.getLogger(FileEventStore.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private final File file;
	private final List<TAggregateEvent> eventsCache;

	public FileEventStore(String filePath, List<Class<? extends TAggregateEvent>> eventsList) {

		file = new File(filePath);
		if (file.exists()) {
			eventsCache = getEventsFromFile(eventsList);
		} else {
			try {
				com.google.common.io.Files.createParentDirs(file);
				com.google.common.io.Files.touch(file);
			} catch (IOException e) {
				throw new RuntimeException("could not create the store at " + file.getAbsolutePath(), e);
			}
			eventsCache = new ArrayList<TAggregateEvent>();
		}

		logger.debug("Storing events to: {}", file.getAbsolutePath());
	}

	@Override
	public void add(TAggregateEvent event) {
		eventsCache.add(event);
		addEventToFile(event);
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
		file.delete();
	}

	private List<TAggregateEvent> getEventsFromFile(List<Class<? extends TAggregateEvent>> eventsList) {
		Map<String, Class<? extends TAggregateEvent>> eventNameToClass = listToMap(eventsList);

		try (Stream<String> stream = Files.lines(file.toPath())) {
			return stream.filter(line -> line.length() > 0).map(line -> line.split(" ", 2)).map(nameAndData -> {
				Class<? extends TAggregateEvent> eventClass = eventNameToClass.get(nameAndData[0]);
				try {
					return MAPPER.readValue(nameAndData[1], eventClass);
				} catch (IOException e) {
					throw new RuntimeException("could not read the store at " + file.getAbsolutePath(), e);
				}
			}).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException("could not read the store at " + file.getAbsolutePath(), e);
		}
	}

	private Map<String, Class<? extends TAggregateEvent>> listToMap(List<Class<? extends TAggregateEvent>> list) {
		return list.stream().collect(Collectors.toMap(Class::getSimpleName, Functions.identity()));
	}

	private void addEventToFile(TAggregateEvent event) {
		try (BufferedWriter output = new BufferedWriter(new FileWriter(file, true))) {
			output.write(event.getClass().getSimpleName() + " " + MAPPER.writeValueAsString(event));
			output.newLine();
			output.flush();
		} catch (IOException e) {
			throw new RuntimeException("could not save event to " + file.getAbsolutePath(), e);
		}
	}

}
