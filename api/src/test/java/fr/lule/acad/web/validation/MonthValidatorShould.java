package fr.lule.acad.web.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MonthValidatorShould {
	
	@Test
	public void beOKForValidMonth() {
		MonthValidator validator = new MonthValidator();
		assertThat(validator.isValid("2017-07", null)).isEqualTo(true);
	}
	
	@Test
	public void beKOForInvalidMonth() {
		MonthValidator validator = new MonthValidator();
		assertThat(validator.isValid("", null)).isEqualTo(false);
		assertThat(validator.isValid("2017", null)).isEqualTo(false);
		assertThat(validator.isValid("abc", null)).isEqualTo(false);
		assertThat(validator.isValid("2017-13", null)).isEqualTo(false);
		assertThat(validator.isValid("2017-1", null)).isEqualTo(false);
	}

}
