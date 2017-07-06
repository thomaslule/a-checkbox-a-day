package fr.lule.acad.web.validation;

import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.format.DateTimeFormat;

public class MonthValidator implements ConstraintValidator<Month, String> {

	private SimpleDateFormat sdf;

	public MonthValidator() {
		sdf = new SimpleDateFormat("yyyy-MM");
		sdf.setLenient(false);
	}

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
