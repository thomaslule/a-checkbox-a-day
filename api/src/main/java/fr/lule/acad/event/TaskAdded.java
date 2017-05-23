package fr.lule.acad.event;

import java.util.UUID;

public class TaskAdded implements IEvent {
	
	private final UUID id;
	private String todo;

	public TaskAdded(UUID id, String todo) {
		this.id = id;
		this.todo = todo;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public UUID getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (todo == null) {
			if (other.todo != null)
				return false;
		} else if (!todo.equals(other.todo))
			return false;
		return true;
	}

}
