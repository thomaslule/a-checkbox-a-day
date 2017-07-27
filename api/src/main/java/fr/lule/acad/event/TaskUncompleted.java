package fr.lule.acad.event;

import java.util.UUID;

public class TaskUncompleted implements IItemEvent {

	private static final long serialVersionUID = 1L;
	private final UUID aggregateId;

	public TaskUncompleted(UUID aggregateId) {
		this.aggregateId = aggregateId;
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
		TaskUncompleted other = (TaskUncompleted) obj;
		if (aggregateId == null) {
			if (other.aggregateId != null)
				return false;
		} else if (!aggregateId.equals(other.aggregateId))
			return false;
		return true;
	}

}
