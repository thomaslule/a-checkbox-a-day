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
			while (dayRunner.month() == start.month()) {
				days.push({ date: dayRunner.format(), text: "" });
				dayRunner.add(1, "days");
			}
			return days;
		}
	};
});
