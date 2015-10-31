angular.module("daySummary")
.factory("DaySummary", ["$resource", function($resource) {
	return $resource("api/daySummary/:by/:id", {}, {
		byMonth: { method:"GET", params: { by: "month", id: "@id" }, isArray:true },
		save: { method: "POST", params: { by: null, id: null }},
		update: { method: "PUT", params: { by: null, id: "@id" }},
		delete: { method: "DELETE", params: { by: null, id: "@id" }}
	});
}]);
