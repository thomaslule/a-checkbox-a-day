angular.module("daySummary")
.directive("daySummary", function() {
	return {
		scope: {
			model: "="
		},
		templateUrl: "/daySummary/daySummary.html",
		controller: "daySummaryCtrl"
	};
});
