describe("errorService", function() {

	beforeEach(module("error"));

	var error;

	beforeEach(inject(function (_error_) {
		error = _error_;
	}));

	it("should test", function() {
		expect(error.ajaxError("blah")).toEqual("bluh");
	});

});
