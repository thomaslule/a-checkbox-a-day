package fr.lule.acad.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.format.DateTimeFormat;

public class DayValidator implements ConstraintValidator<Day, String> {

	@Override
	public void initialize(Day constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		if (value.length() != 10) // joda allows 2017-1-1 and we don't
			return false;
		try {
			DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(value);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
