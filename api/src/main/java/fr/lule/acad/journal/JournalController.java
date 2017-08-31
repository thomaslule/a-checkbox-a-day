package fr.lule.acad.journal;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.IEventStore;
import fr.lule.acad.journal.event.JournalEvent;
import fr.lule.acad.journal.projection.JournalProjection;
import fr.lule.acad.util.IDateFactory;
import fr.lule.acad.web.validation.CommandRunner;
import fr.lule.acad.web.validation.Day;
import fr.lule.acad.web.validation.Month;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.payload.Payload;

@Prefix("/api/Journal")
public class JournalController {

	private final IDateFactory dateFactory;

	private EventBus bus;
	private Validator validator;
	private JournalProjection journalProjection;

	public JournalController(EventBus bus, IEventStore<JournalEvent, JournalDay> journalEventStore,
			JournalProjection journalProjection, IDateFactory dateFactory) {
		this.bus = bus;
		this.journalProjection = journalProjection;
		this.dateFactory = dateFactory;
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Get("/GetMonthJournal/:month")
	public Payload getMonthJournal(GetMonthJournalCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			return new Payload(null, journalProjection.getJournal(command.month));
		});
	}

	@Post("/EditJournalEntry")
	public Payload editJournalEntry(EditJournalEntryCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			if (Journal.editJournalEntry(bus, command.day, command.text, dateFactory)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	public static class GetMonthJournalCommand {
		@NotNull
		@Month
		public String month;

		public GetMonthJournalCommand(String month) {
			this.month = month;
		}

	}

	public static class EditJournalEntryCommand {
		@NotNull
		@Day
		public String day;

		@NotNull
		public String text;
	}

}
