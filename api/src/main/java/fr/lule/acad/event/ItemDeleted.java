package fr.lule.acad.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemDeleted extends ItemEvent {

	@JsonCreator
	public ItemDeleted(@JsonProperty("aggregateId") ItemId aggregateId, @JsonProperty("date") Date date) {
		super(aggregateId, date);
	}

}
