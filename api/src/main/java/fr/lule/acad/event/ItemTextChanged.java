package fr.lule.acad.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemTextChanged extends ItemEvent {

	private final String newText;

	@JsonCreator
	public ItemTextChanged(@JsonProperty("newText") String newText, @JsonProperty("aggregateId") ItemId aggregateId,
			@JsonProperty("date") Date date) {
		super(aggregateId, date);
		this.newText = newText;
	}

	public String getNewText() {
		return newText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((newText == null) ? 0 : newText.hashCode());
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
		ItemTextChanged other = (ItemTextChanged) obj;
		if (newText == null) {
			if (other.newText != null)
				return false;
		} else if (!newText.equals(other.newText))
			return false;
		return true;
	}

}
