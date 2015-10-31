var storage = require("../storage/storage");
var moment = require("moment");

var daySummaryRepository = {};

// Get stored daySummaries for month
// month is a MMMMDDYY string
daySummaryRepository.getForMonth = function(month, callback) {
	var monthMoment = moment(month, "YYYYMM");
	storage.query("select `date`, `text` from day_summary where `date` between ? and ?",
		[ monthMoment.format("YYYY-MM-DD"), monthMoment.endOf("month").format("YYYY-MM-DD") ],
		function(err, results) {
			if (err) return callback(err);
			// database returns ISO dates, transform them to YYYYMMDD strings
			var daySummaries = results.map(function(daySummary) {
				daySummary.date = moment(daySummary.date).format("YYYYMMDD");
				return daySummary;
			});
			callback(null, daySummaries);
		});
}

// Save a daySummary
daySummaryRepository.store = function(daySummary, callback) {
	storage.query('insert into day_summary (`date`, `text`) values (?, ?)',
		[ moment(daySummary.date, "YYYYMMDD").format("YYYY-MM-DD"), daySummary.text ],
		function(err, results) {
	        if (err) return callback(err);
	        daySummary.id = results.insertId;
	        callback(null);
	    });
}

module.exports = daySummaryRepository;
