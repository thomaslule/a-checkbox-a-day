package fr.lule.acad.event;

import java.util.UUID;

import fr.lule.acad.aggregate.ItemType;

public class ItemAdded implements IItemEvent {

	private static final long serialVersionUID = 1L;
	private final UUID aggregateId;
	private String text;
	private String month;
	private ItemType type;

	public ItemAdded(UUID aggregateId, String text, String month, ItemType type) {
		this.aggregateId = aggregateId;
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
	public UUID getAggregateId() {
		return this.aggregateId;
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
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ItemAdded other = (ItemAdded) obj;
		if (aggregateId == null) {
			if (other.aggregateId != null)
				return false;
		} else if (!aggregateId.equals(other.aggregateId))
			return false;
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
