describe("errorService", function() {

	beforeEach(module("error"));

	var error, $rootScope;

	beforeEach(inject(function (_error_, _$rootScope_) {
		error = _error_;
		$rootScope = _$rootScope_;
		spyOn($rootScope, '$broadcast');
	}));

	it("should display the specific error message of a known error code", function() {
		error.ajaxError({ status: 400, data: { error: "invalid_month" }});
		expect($rootScope.$broadcast).toHaveBeenCalledWith("error", { text: "Mois invalide" });
	});

	it("should display the specific error message of a known http status", function() {
		error.ajaxError({ status: 400, data: { error: "nope" }});
		expect($rootScope.$broadcast).toHaveBeenCalledWith("error", { text: "Requête invalide" });
		error.ajaxError({ status: 400 });
		expect($rootScope.$broadcast).toHaveBeenCalledWith("error", { text: "Requête invalide" });
	});

	it("should display the generic error message for an unknown http status", function() {
		error.ajaxError({ status: 666, data: { error: "nope" }});
		expect($rootScope.$broadcast).toHaveBeenCalledWith("error", { text: "Une erreur est survenue" });
		error.ajaxError({ status: 666 });
		expect($rootScope.$broadcast).toHaveBeenCalledWith("error", { text: "Une erreur est survenue" });
	});

});
