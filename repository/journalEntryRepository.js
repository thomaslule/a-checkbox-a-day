var storage = require("../storage/storage");
var moment = require("moment");

var journalEntryRepository = {};

// Get stored entries for month
// month is a MMMMDDYY string
journalEntryRepository.getForMonth = function(month, callback) {
	var monthMoment = moment(month, "YYYYMM");
	storage.query("select `date`, `text` from journal_entry where `date` between ? and ?",
		[ monthMoment.format("YYYY-MM-DD"), monthMoment.endOf("month").format("YYYY-MM-DD") ],
		function(err, results) {
			if (err) return callback(err);
			// database returns ISO dates, transform them to YYYYMMDD strings
			var entries = results.map(function(entry) {
				entry.date = moment(entry.date).format("YYYYMMDD");
				return entry;
			});
			callback(null, entries);
		});
}

// Save an entry
journalEntryRepository.store = function(journalEntry, callback) {
	storage.query('insert into journal_entry (`date`, `text`) values (?, ?) on duplicate key update `date` = VALUES(`date`), `text` = VALUES(`text`)',
		[ moment(journalEntry.date, "YYYYMMDD").format("YYYY-MM-DD"), journalEntry.text ],
		function(err, results) {
	        if (err) return callback(err);
	        callback(null, journalEntry);
	    });
}

module.exports = journalEntryRepository;
