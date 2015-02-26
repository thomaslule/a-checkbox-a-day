var express = require('express');
var nconf = require('nconf');
var bodyParser = require('body-parser');
var fs = require('fs-extra');
var jade = require('jade');

nconf.argv().env().file('local.json');

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

app.get('/', monthController.get)
.get('/all', monthController.getAll)
.post('/new', monthController.postNew)
.post('/edit', monthController.postEdit)
.post('/delete', monthController.postDelete)
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
