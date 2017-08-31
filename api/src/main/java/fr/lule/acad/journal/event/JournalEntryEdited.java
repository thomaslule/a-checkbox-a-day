package fr.lule.acad.journal.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lule.acad.journal.JournalDay;

public class JournalEntryEdited extends JournalEvent {

	private final String text;

	@JsonCreator
	public JournalEntryEdited(@JsonProperty("text") String text, @JsonProperty("aggregateId") JournalDay aggregateId,
			@JsonProperty("timestamp") Date timestamp) {
		super(aggregateId, timestamp);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		JournalEntryEdited other = (JournalEntryEdited) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
