package fr.lule.acad.item.projection;

import java.util.UUID;

import fr.lule.acad.item.ItemType;

public class ItemDisplayed {

	private UUID id;
	private ItemType itemType;
	private String text;
	private String month;
	private boolean cancelled;
	private boolean completed;
	private boolean moved;

	public ItemDisplayed(UUID id, ItemType type, String text, String month, boolean cancelled, boolean completed,
			boolean moved) {
		this.id = id;
		this.itemType = type;
		this.text = text;
		this.month = month;
		this.cancelled = cancelled;
		this.completed = completed;
		this.moved = moved;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the itemType
	 */
	public ItemType getItemType() {
		return itemType;
	}

	/**
	 * @param itemType
	 *            the type to set
	 */
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * @param cancelled
	 *            the cancelled to set
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return the moved
	 */
	public boolean isMoved() {
		return moved;
	}

	/**
	 * @param moved
	 *            the moved to set
	 */
	public void setMoved(boolean moved) {
		this.moved = moved;
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
		result = prime * result + (cancelled ? 1231 : 1237);
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + (moved ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		ItemDisplayed other = (ItemDisplayed) obj;
		if (cancelled != other.cancelled)
			return false;
		if (completed != other.completed)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemType != other.itemType)
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (moved != other.moved)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
