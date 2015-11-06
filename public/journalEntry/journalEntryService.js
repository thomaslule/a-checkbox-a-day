angular.module("journalEntry")
.factory("JournalEntry", ["$resource", function($resource) {
	return $resource("api/journalEntry/:id", {}, {
		byMonth: {
			method:"GET",
			url: "api/journalEntry/month/:month",
			params: { id: "@month" },
			isArray:true
		},
		save: { method: "POST", params: { id: null } }
	});
}])
.factory("journalEntry", [ "JournalEntry", function(JournalEntry) {
	return {
		// Return all the days of a month, with provided journalEntries inserted inside
		// month is YYYYMMDD
		allMonthDays: function(month, journalEntries) {
			var start = moment(month, "YYYYMM", true);
			var dayRunner = moment(start);
			var days = [];
			// add every day of month
			while (dayRunner.month() == start.month()) {
				var dayToSet = null;
				// check if this day is in the provided array
				journalEntries.forEach(function(journalEntry) {
					if (journalEntry.date == dayRunner.format("YYYYMMDD")) {
						dayToSet = journalEntry;
					}
				});
				// if it hasnt been found, create a default one
				if (!dayToSet) {
					dayToSet = new JournalEntry();
					dayToSet.date = dayRunner.format("YYYYMMDD");
					dayToSet.text = "";
				}
				days.push(dayToSet);
				dayRunner.add(1, "days");
			}
			return days;
		}
	};
}]);
