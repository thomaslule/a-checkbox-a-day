package fr.lule.acad.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lule.acad.aggregate.ItemType;

public class ItemAdded extends ItemEvent {

	private final String text;
	private final String month;
	private final ItemType type;

	@JsonCreator
	public ItemAdded(@JsonProperty("text") String text, @JsonProperty("month") String month,
			@JsonProperty("type") ItemType type, @JsonProperty("aggregateId") ItemId aggregateId,
			@JsonProperty("date") Date date) {
		super(aggregateId, date);
		this.text = text;
		this.month = month;
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public String getMonth() {
		return month;
	}

	public ItemType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ItemAdded other = (ItemAdded) obj;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
