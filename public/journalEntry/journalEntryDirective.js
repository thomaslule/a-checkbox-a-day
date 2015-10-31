angular.module("journalEntry")
.directive("journalEntry", function() {
	return {
		scope: {
			model: "="
		},
		templateUrl: "/journalEntry/journalEntry.html",
		controller: "journalEntryCtrl"
	};
});
