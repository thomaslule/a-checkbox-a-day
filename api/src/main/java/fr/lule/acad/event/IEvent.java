package fr.lule.acad.event;

import java.util.Date;

public interface IEvent {

	public IEventId getAggregateId();

	public Date getTimestamp();

}
