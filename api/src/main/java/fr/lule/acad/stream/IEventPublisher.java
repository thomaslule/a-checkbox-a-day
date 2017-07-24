package fr.lule.acad.stream;

import java.util.function.Consumer;

import fr.lule.acad.event.IEvent;

public interface IEventPublisher {

	public <T extends IEvent<?>> void publish(T event);

	public <T extends IEvent<?>> void on(Class<T> eventClass, Consumer<T> listener);

}
