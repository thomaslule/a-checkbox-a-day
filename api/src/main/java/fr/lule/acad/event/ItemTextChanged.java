package fr.lule.acad.event;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemTextChanged implements IItemEvent {

	private final UUID aggregateId;
	private final String newText;

	@JsonCreator
	public ItemTextChanged(@JsonProperty("aggregateId") UUID aggregateId, @JsonProperty("newText") String newText) {
		this.aggregateId = aggregateId;
		this.newText = newText;
	}

	@Override
	public UUID getAggregateId() {
		return aggregateId;
	}

	public String getNewText() {
		return newText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aggregateId == null) ? 0 : aggregateId.hashCode());
		result = prime * result + ((newText == null) ? 0 : newText.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemTextChanged other = (ItemTextChanged) obj;
		if (aggregateId == null) {
			if (other.aggregateId != null)
				return false;
		} else if (!aggregateId.equals(other.aggregateId))
			return false;
		if (newText == null) {
			if (other.newText != null)
				return false;
		} else if (!newText.equals(other.newText))
			return false;
		return true;
	}

}
