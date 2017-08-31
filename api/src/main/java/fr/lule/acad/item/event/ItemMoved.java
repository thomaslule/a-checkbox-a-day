package fr.lule.acad.item.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lule.acad.item.ItemId;

public class ItemMoved extends ItemEvent {

	private final ItemId childItemAddedId;
	private final String movedToMonth;

	@JsonCreator
	public ItemMoved(@JsonProperty("childItemAddedId") ItemId childItemAddedId,
			@JsonProperty("movedToMonth") String movedToMonth, @JsonProperty("aggregateId") ItemId aggregateId,
			@JsonProperty("timestamp") Date timestamp) {
		super(aggregateId, timestamp);
		this.childItemAddedId = childItemAddedId;
		this.movedToMonth = movedToMonth;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((childItemAddedId == null) ? 0 : childItemAddedId.hashCode());
		result = prime * result + ((movedToMonth == null) ? 0 : movedToMonth.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemMoved other = (ItemMoved) obj;
		if (childItemAddedId == null) {
			if (other.childItemAddedId != null)
				return false;
		} else if (!childItemAddedId.equals(other.childItemAddedId))
			return false;
		if (movedToMonth == null) {
			if (other.movedToMonth != null)
				return false;
		} else if (!movedToMonth.equals(other.movedToMonth))
			return false;
		return true;
	}

}
