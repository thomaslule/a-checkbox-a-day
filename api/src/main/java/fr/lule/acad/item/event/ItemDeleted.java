package fr.lule.acad.item.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lule.acad.item.ItemId;

public class ItemDeleted extends ItemEvent {

	@JsonCreator
	public ItemDeleted(@JsonProperty("aggregateId") ItemId aggregateId, @JsonProperty("timestamp") Date timestamp) {
		super(aggregateId, timestamp);
	}

}
