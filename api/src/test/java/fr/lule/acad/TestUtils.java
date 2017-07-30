package fr.lule.acad;

import java.util.Date;
import java.util.UUID;

import fr.lule.acad.util.IDateFactory;
import fr.lule.acad.util.IIdFactory;

public class TestUtils {

	public static final Date DATE_ZERO = new Date(0);
	public static final IDateFactory DATE_ZERO_FACTORY = () -> DATE_ZERO;

	public static IIdFactory createIdFactory(UUID id) {
		return () -> id;
	}

}
