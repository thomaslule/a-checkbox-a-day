package fr.lule.acad.util;

import java.util.Date;

public class DateFactory implements IDateFactory {

	@Override
	public Date now() {
		return new Date();
	}

}
