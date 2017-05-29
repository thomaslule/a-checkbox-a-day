package fr.lule.acad.event;

import java.util.UUID;

public class TaskAdded implements ITaskEvent {
	
	private final UUID aggregateId;
	private String todo;
	private String month;

	public TaskAdded(UUID aggregateId, String todo, String month) {
		this.aggregateId = aggregateId;
		this.todo = todo;
		this.month = month;
	}

	public String getTodo() {
		return todo;
	}
	
	public String getMonth() {
		return month;
	}

	@Override
	public UUID getAggregateId() {
		return this.aggregateId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aggregateId == null) ? 0 : aggregateId.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((todo == null) ? 0 : todo.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		TaskAdded other = (TaskAdded) obj;
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
		if (todo == null) {
			if (other.todo != null)
				return false;
		} else if (!todo.equals(other.todo))
			return false;
		return true;
	}

}
