package fr.lule.acad.journal.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.lule.acad.event.Event;
import fr.lule.acad.journal.JournalDay;

public abstract class JournalEvent extends Event {

	private final JournalDay aggregateId;

	public JournalEvent(JournalDay aggregateId, Date timestamp) {
		super(timestamp);
		this.aggregateId = aggregateId;
	}

	@Override
	public JournalDay getAggregateId() {
		return aggregateId;
	}

	@JsonIgnore
	public String getDay() {
		return aggregateId.getId();
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
		JournalEvent other = (JournalEvent) obj;
		if (aggregateId == null) {
			if (other.aggregateId != null)
				return false;
		} else if (!aggregateId.equals(other.aggregateId))
			return false;
		return true;
	}

}
