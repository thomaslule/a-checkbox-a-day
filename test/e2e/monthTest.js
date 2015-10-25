var assert = require("assert");

describe("month page", function() {

	beforeEach(function() {
		browser.get('http://localhost:8080/admin/clear');
		browser.get('http://localhost:8080/#/month/201509');
	});

	it("can create task", function() {
		element(by.css("#add-item-form [name='name']")).sendKeys("test");
		element(by.css("#add-item-form [type='submit']")).click();
		expect(element(by.css(".item .item-name")).getText()).toEqual("test");
	});

});
