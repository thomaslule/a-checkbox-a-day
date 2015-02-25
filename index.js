var express = require('express');
var fs = require('fs-extra');
var retry = require('retry');
var nconf = require('nconf');
var bodyParser = require('body-parser');
var storage = require('./storage/storage');

nconf.argv().env().file('local.json');

// We try to initialize the database 5 times
fs.readFile('./storage/init_db.sql', 'utf8', function(err, script) {
    if (err) throw err;
    var operation = retry.operation();
    operation.attempt(function () {
        storage.execute(script, function(err) {
            if (operation.retry(err)) {
                console.log(err.message);
                return ;
            }
            console.log('Database initialized');
        });
    });
});

var app = express();

// set a middleware to parse request body
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// make public folder visible
app.use(express.static(__dirname + '/public'));

app.set('views', './views');
app.set('view engine', 'jade');
// output indented html
app.locals.pretty = true;

var monthController = require('./controllers/monthController.js');

app.get('/', monthController.get)
.get('/all', monthController.getAll)
.post('/new', monthController.postNew)
.post('/edit', monthController.postEdit)
.post('/delete', monthController.postDelete);

var applicationHealth = require('./controllers/healthController.js');

app.get('/health', applicationHealth.get);

// error handling
app.use(function(err, req, res, next) {
    console.error(err.stack);
    res.status(500);
    if (req.xhr) {
        res.json({ 'error': err.message });
    } else {
        res.send('Internal error: ' + err.message);
    }
});

app.listen(nconf.get('port'), function() {
    console.log("Launched a-checkbox-a-day on port %s", nconf.get('port'));
});
