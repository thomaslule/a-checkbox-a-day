package fr.lule.acad.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.format.DateTimeFormat;

public class MonthValidator implements ConstraintValidator<Month, String> {

	@Override
	public void initialize(Month constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		if (value.length() != 7) // joda allows 2017-1 and we don't
			return false;
		try {
			DateTimeFormat.forPattern("yyyy-MM").parseDateTime(value);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
