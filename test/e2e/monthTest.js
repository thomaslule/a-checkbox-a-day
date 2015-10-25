var assert = require("assert");

describe("month page", function() {

	beforeEach(function() {
		browser.driver.get('http://localhost:8080/admin/clear');
		browser.get('http://localhost:8080/#/month/204509');
	});

	var hasClass = function (element, cls) {
	    return element.getAttribute('class').then(function (classes) {
	        return classes.split(' ').indexOf(cls) !== -1;
	    });
	};

	var persisted = function(check) {
		check();
		browser.refresh();
		check();
	}

	var createTask = function(name) {
		element(by.css("#add-item-form [name='name']")).sendKeys(name);
		element(by.css("#add-item-form [type='submit']")).click();
	}

	it("can create task", function() {
		createTask("test");
		persisted(function() {
			expect(element(by.css(".item .item-name")).getText()).toEqual("test");
		});
	});

	it("can check task", function() {
		createTask("test");
		element(by.css(".item [type='checkbox']")).click();
		persisted(function() {
			expect(element(by.css(".item [type='checkbox']")).isSelected()).toBe(true);
		});
	});

	it("can edit task", function() {
		createTask("test");
		element(by.css(".item .item-name")).sendKeys("re");
		persisted(function() {
			expect(element(by.css(".item .item-name")).getText()).toEqual("retest");
		});
	});

	it("can cancel, restore and delete task", function() {
		createTask("test");
		element(by.css(".item .cancel-item-button")).click();
		persisted(function() {
			expect(hasClass(element(by.css(".item")), 'cancelled')).toBe(true);
		});
		element(by.css(".item .restore-item-button")).click();
		persisted(function() {
			expect(hasClass(element(by.css(".item")), 'active')).toBe(true);
		});
		element(by.css(".item .cancel-item-button")).click();
		element(by.css(".item .delete-item-button")).click();
		browser.refresh();
		expect(element(by.css(".item")).isPresent()).toBe(false);
	});

	it("can create event", function() {
		element(by.css("#choose-type-button")).click();
		element(by.css("#choose-event-button")).click();
		element(by.css("#add-item-form [name='name']")).sendKeys("test");
		element(by.css("#add-item-form [type='submit']")).click();
		persisted(function() {
			expect(element(by.css(".event .item-name")).getText()).toEqual("test");
		});
	});

	it("can create note", function() {
		element(by.css("#choose-type-button")).click();
		element(by.css("#choose-note-button")).click();
		element(by.css("#add-item-form [name='name']")).sendKeys("test");
		element(by.css("#add-item-form [type='submit']")).click();
		persisted(function() {
			expect(element(by.css(".note .item-name")).getText()).toEqual("test");
		});
	});

});
