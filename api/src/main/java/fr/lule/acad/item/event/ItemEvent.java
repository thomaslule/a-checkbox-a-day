package fr.lule.acad.item.event;

import java.util.Date;

import fr.lule.acad.event.Event;
import fr.lule.acad.item.ItemId;

public abstract class ItemEvent extends Event {

	private final ItemId aggregateId;

	public ItemEvent(ItemId aggregateId, Date timestamp) {
		super(timestamp);
		this.aggregateId = aggregateId;
	}

	@Override
	public ItemId getAggregateId() {
		return aggregateId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aggregateId == null) ? 0 : aggregateId.hashCode());
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
		ItemEvent other = (ItemEvent) obj;
		if (aggregateId == null) {
			if (other.aggregateId != null)
				return false;
		} else if (!aggregateId.equals(other.aggregateId))
			return false;
		return true;
	}

}
