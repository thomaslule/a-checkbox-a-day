angular.module("item")
.factory("Item", ["$resource", function($resource) {
	return $resource("api/item/:id", {}, {
		byMonth: {
			method:"GET",
			url: "api/item/month/:month",
			params: { month: "@month" },
			isArray:true
		},
		save: { method: "POST", params: { id: null }},
		update: { method: "PUT", params: {id: "@id" }},
		delete: { method: "DELETE", params: { id: "@id" }}
	});
}]);
