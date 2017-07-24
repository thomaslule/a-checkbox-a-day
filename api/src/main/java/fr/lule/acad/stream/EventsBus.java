package fr.lule.acad.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.store.IEventStore;

public class EventsBus implements IEventPublisher {

	private IEventStore<IItemEvent> itemEventStore;
	private IEventStore<IJournalEvent> journalEventStore;

	private Map<Class<? extends IEvent<?>>, List<Consumer<? extends IEvent<?>>>> eventClassToListener = new HashMap<Class<? extends IEvent<?>>, List<Consumer<? extends IEvent<?>>>>();

	public EventsBus(IEventStore<IItemEvent> itemEventStore, IEventStore<IJournalEvent> journalEventStore) {
		this.itemEventStore = itemEventStore;
		this.journalEventStore = journalEventStore;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends IEvent<?>> void publish(T event) {
		if (IItemEvent.class.isAssignableFrom(event.getClass())) {
			itemEventStore.add((IItemEvent) event);
		}
		if (IJournalEvent.class.isAssignableFrom(event.getClass())) {
			journalEventStore.add((IJournalEvent) event);
		}

		List unsafe = eventClassToListener.get(event.getClass());
		if (unsafe != null) {
			List<Consumer<T>> listeners = unsafe;
			listeners.forEach(listener -> listener.accept(event));
		}
	}

	@Override
	public <T extends IEvent<?>> void on(Class<T> eventClass, Consumer<T> listener) {
		if (eventClassToListener.get(eventClass) == null) {
			eventClassToListener.put(eventClass, new ArrayList<Consumer<? extends IEvent<?>>>());
		}
		eventClassToListener.get(eventClass).add(listener);
	}
}
