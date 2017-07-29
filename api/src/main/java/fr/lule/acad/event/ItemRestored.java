package fr.lule.acad.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemRestored extends ItemEvent {

	@JsonCreator
	public ItemRestored(@JsonProperty("aggregateId") ItemId aggregateId, @JsonProperty("date") Date date) {
		super(aggregateId, date);
	}

}
