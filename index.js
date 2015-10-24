var express = require('express');

var app = express();

// make public folder visible
app.use(express.static(__dirname + '/public'));

app.listen(8080, function() {
    console.log("Launched a-checkbox-a-day on port 8080");
});
