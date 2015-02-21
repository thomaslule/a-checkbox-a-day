var express = require('express');
var nconf = require('nconf');
var bodyParser = require('body-parser');

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

var monthController = require('./controllers/monthController.js');

app.get('/', monthController.get)
.get('/all', monthController.getAll)
.post('/new', monthController.postNew)
.listen(nconf.get('port'));
