package fr.lule.acad.event;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemCancelled implements IItemEvent {

	private final UUID aggregateId;

	@JsonCreator
	public ItemCancelled(@JsonProperty("aggregateId") UUID aggregateId) {
		this.aggregateId = aggregateId;
	}

	/**
	 * @return the aggregateId
	 */
	@Override
	public UUID getAggregateId() {
		return aggregateId;
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
		ItemCancelled other = (ItemCancelled) obj;
		if (aggregateId == null) {
			if (other.aggregateId != null)
				return false;
		} else if (!aggregateId.equals(other.aggregateId))
			return false;
		return true;
	}

}
