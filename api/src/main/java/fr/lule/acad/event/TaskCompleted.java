package fr.lule.acad.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskCompleted extends ItemEvent {

	@JsonCreator
	public TaskCompleted(@JsonProperty("aggregateId") ItemId aggregateId, @JsonProperty("date") Date date) {
		super(aggregateId, date);
	}

}
