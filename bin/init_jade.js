var fs = require('fs-extra');
var jade = require('jade');

fs.ensureDirSync('public/js/jade');
fs.copySync('node_modules/jade/runtime.js', 'public/js/jade/runtime.js');

// compile client-side jade templates to public/js/jade
fs.writeFileSync("public/js/jade/taskTemplate.js", jade.compileFileClient('views/task.jade', {name: 'jadeTaskTemplate'}));

process.exit();
