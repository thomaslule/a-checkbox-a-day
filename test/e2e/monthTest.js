var assert = require("assert");

describe("month page", function() {

	beforeEach(function() {
		browser.get('http://localhost:8080/#/month/201509');
	});

	it("can create task", function() {
		element(by.css("#add-item-form [name='name']")).sendKeys("test");
		element(by.css("#add-item-form [type='submit']")).click();
		element(by.css(".item .item-name")).getText().then(function(itemName) {
			assert.equal("test", itemName);
		});
	});

});
