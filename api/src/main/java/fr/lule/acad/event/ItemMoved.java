package fr.lule.acad.event;

import java.util.UUID;

public class ItemMoved implements IItemEvent {

	private static final long serialVersionUID = 1L;
	private final UUID aggregateId;
	private final UUID childItem;
	private final String movedToMonth;

	public ItemMoved(UUID aggregateId, UUID childItem, String movedToMonth) {
		this.aggregateId = aggregateId;
		this.childItem = childItem;
		this.movedToMonth = movedToMonth;
	}

	@Override
	public UUID getAggregateId() {
		return aggregateId;
	}

	public UUID getChildItem() {
		return childItem;
	}

	public String getMovedToMonth() {
		return movedToMonth;
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
		result = prime * result + ((childItem == null) ? 0 : childItem.hashCode());
		result = prime * result + ((movedToMonth == null) ? 0 : movedToMonth.hashCode());
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
		ItemMoved other = (ItemMoved) obj;
		if (aggregateId == null) {
			if (other.aggregateId != null)
				return false;
		} else if (!aggregateId.equals(other.aggregateId))
			return false;
		if (childItem == null) {
			if (other.childItem != null)
				return false;
		} else if (!childItem.equals(other.childItem))
			return false;
		if (movedToMonth == null) {
			if (other.movedToMonth != null)
				return false;
		} else if (!movedToMonth.equals(other.movedToMonth))
			return false;
		return true;
	}

}
