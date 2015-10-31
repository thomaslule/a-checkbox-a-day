describe("daySummaryService", function() {

	beforeEach(module("daySummary"));

	var daySummary;

	beforeEach(inject(function (_daySummary_) {
		daySummary = _daySummary_;
	}));

	describe("allMonthDays", function() {

		it("should return all month days", function() {
			var days = daySummary.allMonthDays("201510", []);
			expect(days.length).toEqual(31);
			expect(moment(days[0].date).format("M-D")).toEqual("10-1");
			expect(days[0].text).toEqual("");
			expect(moment(days[30].date).format("M-D")).toEqual("10-31");
			expect(days[30].text).toEqual("");
		});

	});

});
