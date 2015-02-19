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

app.get('/', function(req, res) {
	res.render('month');
})
.post('/', function(req, res) {
    console.log(req.body.new_task);
})
.listen(nconf.get('port'));
