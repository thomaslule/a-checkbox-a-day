package fr.lule.acad.web;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import fr.lule.acad.aggregate.Journal;
import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.projection.JournalProjection;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.stream.IEventPublisher;
import fr.lule.acad.web.validation.CommandRunner;
import fr.lule.acad.web.validation.Day;
import fr.lule.acad.web.validation.Month;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.payload.Payload;

@Prefix("/api")
public class JournalController {

	private IEventPublisher publisher;
	private Validator validator;
	private JournalProjection journalProjection;

	public JournalController(IEventPublisher publisher, IEventStore<IJournalEvent> journalEventStore,
			JournalProjection journalProjection) {
		this.publisher = publisher;
		this.journalProjection = journalProjection;
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Get("/Journal/:month")
	public Payload GetJournal(GetJournalCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			return new Payload(null, journalProjection.getJournal(command.month));
		});
	}

	@Post("/EditJournalEntry")
	public Payload EditJournalEntry(EditJournalEntryCommand command) {
		return CommandRunner.ifValid(command, validator, (c) -> {
			if (Journal.editJournalEntry(publisher, command.day, command.text)) {
				return Payload.ok();
			} else {
				return Payload.badRequest();
			}
		});
	}

	public static class GetJournalCommand {
		@NotNull
		@Month
		public String month;

		public GetJournalCommand(String month) {
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
