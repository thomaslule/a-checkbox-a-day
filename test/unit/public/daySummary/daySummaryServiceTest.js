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
			expectDateIs(days[0].date, "10-1");
			expect(days[0].text).toEqual("");
			expectDateIs(days[30].date, "10-31");
			expect(days[30].text).toEqual("");
		});

		it("should insert provided daySummaries into returned month", function() {
			var days = daySummary.allMonthDays("201510",
				[ getDaySummary("20151001", "1er jour"),
				getDaySummary("20151015", "milieu"),
				getDaySummary("20151031", "dernier jour")]);
			expect(days.length).toEqual(31);
			expectDateIs(days[0].date, "10-1");
			expect(days[0].text).toEqual("1er jour");
			expectDateIs(days[1].date, "10-2");
			expect(days[1].text).toEqual("");
			expectDateIs(days[14].date, "10-15");
			expect(days[14].text).toEqual("milieu");
			expectDateIs(days[30].date, "10-31");
			expect(days[30].text).toEqual("dernier jour");
		});

		function expectDateIs(date, expected) {
			expect(moment(date, "YYYYMMDD").format("M-D")).toEqual(expected);
		}

		function getDaySummary(date, text) {
			return {
				date: date,
				text: text
			};
		}

	});

});
