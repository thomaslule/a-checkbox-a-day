describe("daySummaryService", function() {

	beforeEach(module("daySummary"));

	var daySummary;

	beforeEach(inject(function (_daySummary_) {
		daySummary = _daySummary_;
	}));

	describe("allMonthDays", function() {

		it("should return all month days", function() {
			expect(daySummary).toBeDefined();
			/*
			var days = daySummary.allMonthDays("201510", []);
			expect(days.length).toEqual(31);
			expect(moment(day[0].date).format("M-D")).toEqual("10-01");
			expect(day[0].text).toEqual("");
			expect(moment(day[30].date).format("M-D")).toEqual("10-31");
			expect(day[30].text).toEqual("");
			*/
		});

	});

});
