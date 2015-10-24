angular.module("item")
.factory("Item", ["$resource", function($resource) {
	return $resource("api/item/:by/:id", {}, {
		byMonth: { method:"GET", params: { by: "month", id: "@id" }, isArray:true },
		save: { method: "POST", params: { by: null, id: null }},
		update: { method: "PUT", params: { by: null, id: "@id" }}
	});
}]);
