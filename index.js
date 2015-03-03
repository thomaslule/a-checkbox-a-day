var express = require('express');
var retry = require('retry');
var nconf = require('nconf');
var bodyParser = require('body-parser');
var storage = require('./storage/storage');
var fs = require('fs-extra');
var jade = require('jade');

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
var jadeTemplates = fs.readFileSync('node_modules/jade/runtime.js').toString() + jade.compileFileClient('views/task.jade', {name: 'jadeTaskTemplate'});

var monthController = require('./controllers/monthController.js');

app.get('/', function(req, res) {
    res.location('/month');
    res.sendStatus(301);
})
.get('/month', monthController.get)
.post('/task', monthController.newTask)
.post('/task/:id', monthController.editTask)
.get('/jade_templates.js', function(req, res) {
    res.type('text/javascript');
    res.send(jadeTemplates);
});

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
