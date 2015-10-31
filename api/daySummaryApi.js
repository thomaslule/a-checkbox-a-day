var daySummaryRepository = require("../repository/daySummaryRepository");
var moment = require("moment");

var daySummaryApi = {};

daySummaryApi.getForMonth = function(req, res, next) {
    var month = moment(req.params.month, "YYYYMM", true);
    if (!month.isValid()) return next({ statusCode: 400, text: "invalid_month" });
    daySummaryRepository.getForMonth(month, function(err, result) {
    	if (err) return next(err);
    	res.json(result);
    });
};

daySummaryApi.post = function(req, res, next) {
	var daySummary = req.body;
	daySummaryRepository.store(daySummary, function(err) {
		if (err) return next(err);
		res.json(daySummary);
	});
};

module.exports = daySummaryApi;
