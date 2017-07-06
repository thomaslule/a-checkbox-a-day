package fr.lule.acad.web.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import net.codestory.http.constants.HttpStatus;
import net.codestory.http.payload.Payload;

public interface CommandRunner<COMMAND> {
	
	public Payload run(COMMAND command);
	
	public static <T> Payload ifValid(T command, Validator validator, CommandRunner<T> runner) {
		Set<ConstraintViolation<T>> errors = validator.validate(command);
		if (errors.size() == 0) {
			return runner.run(command);
		} else {
			return new Payload(null,
					errors.stream().map(error -> String.format("%s: %s", error.getPropertyPath().toString(), error.getMessage())).toArray(),
					HttpStatus.BAD_REQUEST);
		}
	}

}
