var journalEntryRepository = require("../repository/journalEntryRepository");
var moment = require("moment");

var journalEntryApi = {};

// GET: /journalEntry/month/20150101
journalEntryApi.getForMonth = function(req, res, next) {
    var month = moment(req.params.month, "YYYYMM", true);
    if (!month.isValid()) return next({ statusCode: 400, text: "invalid_month" });
    journalEntryRepository.getForMonth(month, function(err, result) {
    	if (err) return next(err);
    	res.json(result);
    });
};

// POST: /journalEntry
journalEntryApi.post = function(req, res, next) {
	var journalEntry = req.body;
	journalEntryRepository.store(journalEntry, function(err) {
		if (err) return next(err);
		res.json(journalEntry);
	});
};

module.exports = journalEntryApi;
