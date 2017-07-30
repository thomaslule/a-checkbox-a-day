package fr.lule.acad.util;

import java.util.UUID;

public class IdFactory implements IIdFactory {

	@Override
	public UUID newId() {
		return UUID.randomUUID();
	}

}
