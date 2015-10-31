var storage = require("../storage/storage");
var moment = require("moment");

var daySummaryRepository = {};

daySummaryRepository.getForMonth = function(month, callback) {
	storage.query("select `date`, `text` from day_summary where `date` between ? and ?",
		[ month.format(), month.endOf("month").format() ],
		callback);
}

daySummaryRepository.store = function(daySummary, callback) {
	storage.query('insert into day_summary (`date`, `text`) values (?, ?)',
		[ moment(daySummary.date).format("YYYY-MM-DD"), daySummary.text ],
		function(err, results) {
	        if (err) return callback(err);
	        daySummary.id = results.insertId;
	        callback(null);
	    });
}

module.exports = daySummaryRepository;
