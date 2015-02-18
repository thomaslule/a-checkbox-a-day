var express = require('express');
var nconf = require('nconf');

nconf.argv().env().file('local.json');

var app = express();

app.set('views', './views');
app.set('view engine', 'jade');

app.get('/', function(req, res) {
	res.render('month');
})
.listen(nconf.get('port'));
