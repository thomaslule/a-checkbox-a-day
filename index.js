var bodyParser = require("body-parser");
var express = require("express");
var nconf = require("nconf");
var retry = require("retry");
var storage = require("./storage/storage");

nconf.argv().env().file("local.json");

// We try to initialize the database 5 times
var operation = retry.operation();
operation.attempt(function () {
    storage.initDb(function (err) {
        if (operation.retry(err)) {
            console.log(err.message);
            return;
        }
        console.log("Database initialized");
    });
});

var app = express();

// set a middleware to parse request body
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// make public folder visible
app.use(express.static(__dirname + "/public"));

var itemApi = require("./api/itemApi");
var journalEntryApi = require("./api/journalEntryApi");

app
.get("/api/journalEntry/month/:month", journalEntryApi.getForMonth)
.post("/api/journalEntry", journalEntryApi.post)
.get("/api/item/month/:month", itemApi.getForMonth)
.post("/api/item", itemApi.post)
.put("/api/item/:id/month/:month", itemApi.move)
.put("/api/item/:id", itemApi.put)
.delete("/api/item/:id", itemApi.delete)
.get("/admin/clear", function(req, res, next) {
    storage.clearDb(function(err) {
        if (err) {
            next(err);
            return;
        }
        res.sendStatus(200);
    })
});

// error handling
app.use(function(err, req, res, next) {
    if (err.statusCode) {
        console.error("Error " + err.statusCode + ": " + err.text);
        res.status(err.statusCode);
        res.json({ "error": err.text });
    } else {
        console.error(err.stack ? err.stack : err);
        res.status(500);
        if (req.xhr) {
            res.json({ "error": (err.message ? err.message : err) });
        } else {
            res.send("Internal error: " + (err.message ? err.message : err));
        }
    }
});

app.listen(nconf.get("port"), function() {
    console.log("Launched a-checkbox-a-day on port %s", nconf.get("port"));
});
