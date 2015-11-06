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

	var getTitle = function() {
		return element(by.css("h1 span")).getText();
	}

	var previousMonth = function() {
		element(by.css("h1 .previous")).click();
	}

	var nextMonth = function() {
		element(by.css("h1 .next")).click();
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

	it("can create journal entries", function() {
		var entry = element(by.css(".journal-entry [contenteditable]"));
		entry.click();
		entry.sendKeys("test");
		entry.sendKeys(protractor.Key.ENTER);
		persisted(function() {
			expect(element(by.css(".journal-entry [contenteditable]")).getText()).toEqual("test");
		});
	});

	it("can change month", function() {
		expect(getTitle()).toEqual("septembre 2045");
		previousMonth();
		expect(getTitle()).toEqual("août 2045");
		nextMonth();
		expect(getTitle()).toEqual("septembre 2045");
	});

	it("can move a task", function() {
		createTask("test");
		element(by.css(".item .move-item-button")).click();
		element(by.linkText("octobre 2045")).click();
		persisted(function() {
			expect(element(by.css(".item")).getAttribute("class")).toContain("moved");
		});
		nextMonth();
		expect(element(by.css(".item .item-name")).getText()).toEqual("test");
	});

});
