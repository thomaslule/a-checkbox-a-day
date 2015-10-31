angular.module("daySummary")
.factory("DaySummary", ["$resource", function($resource) {
	return $resource("api/daySummary/:by/:id", {}, {
		byMonth: { method:"GET", params: { by: "month", id: "@id" }, isArray:true },
		save: { method: "POST", params: { by: null, id: null }},
		update: { method: "PUT", params: { by: null, id: "@id" }},
		delete: { method: "DELETE", params: { by: null, id: "@id" }}
	});
}])
.factory("daySummary", function() {
	return {
		allMonthDays: function(month, daySummaries) {
			var start = moment(month, "YYYYMM", true);
			var dayRunner = moment(start);
			var days = [];
			// add every day of month
			while (dayRunner.month() == start.month()) {
				var dayToSet = { date: dayRunner.format(), text: "" };
				// check if this day is in the provided array
				daySummaries.forEach(function(daySummary) {
					if (moment(daySummary.date).isSame(dayRunner)) {
						dayToSet = daySummary;
					}
				});
				days.push(dayToSet);
				dayRunner.add(1, "days");
			}
			return days;
		}
	};
});
