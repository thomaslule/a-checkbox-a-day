package fr.lule.acad.event;

import java.io.Serializable;

public interface IEvent<ID> extends Serializable {

	public ID getAggregateId();

}
