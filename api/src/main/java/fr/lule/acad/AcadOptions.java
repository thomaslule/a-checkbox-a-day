package fr.lule.acad;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class AcadOptions {

	public final CommandLine cmd;

	public AcadOptions(String[] args) {
		Options options = new Options();
		options.addOption("s", "store", true, "Path to store folder");
		try {
			cmd = new DefaultParser().parse(options, args);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean hasStore() {
		return cmd.hasOption("store");
	}

	public String getStore() {
		return cmd.getOptionValue("store");
	}

}
