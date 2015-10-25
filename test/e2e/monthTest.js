var assert = require("assert");

describe("month page", function() {

	beforeEach(function() {
		browser.get('http://localhost:8080/#/month/201509');
	});

	it("can create task", function() {
		// browser.wait(until.elementLocated(By.css("#add-item-form [name='name']")), 10000);
		browser.findElement(By.css("#add-item-form [name='name']")).sendKeys("test");
		browser.findElement(By.css("#add-item-form [type='submit']")).click();
		browser.findElement(By.css(".item .item-name")).getText().then(function(itemName) {
			assert.equal("test", itemName);
		});
	});

});
