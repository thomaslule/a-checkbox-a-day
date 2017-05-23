package fr.lule.acad.stream;

import fr.lule.acad.event.IEvent;

public class EventSubscriber<TEvent extends IEvent> implements IEventSubscriberParam<IEvent> {
	
	private boolean isCalled;
	
	public void handle(IEvent event) {
		isCalled = true;
	}

	/**
	 * @return the isCalled
	 */
	public boolean isCalled() {
		return isCalled;
	}

}
