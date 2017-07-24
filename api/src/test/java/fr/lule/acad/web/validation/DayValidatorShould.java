package fr.lule.acad.web.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DayValidatorShould {

	@Test
	public void beOKForValidDay() {
		DayValidator validator = new DayValidator();
		assertThat(validator.isValid("2017-07-19", null)).isEqualTo(true);
		assertThat(validator.isValid("2000-02-29", null)).isEqualTo(true);
	}

	@Test
	public void beKOForInvalidDay() {
		DayValidator validator = new DayValidator();
		assertThat(validator.isValid("", null)).isEqualTo(false);
		assertThat(validator.isValid("2017", null)).isEqualTo(false);
		assertThat(validator.isValid("abc", null)).isEqualTo(false);
		assertThat(validator.isValid("2017-01-35", null)).isEqualTo(false);
		assertThat(validator.isValid("2017-1-01", null)).isEqualTo(false);
		assertThat(validator.isValid("2017-01-1", null)).isEqualTo(false);
		assertThat(validator.isValid("2017-02-29", null)).isEqualTo(false);
	}

}
