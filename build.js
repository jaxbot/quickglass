var builder = require('./builder');
var fs = require('fs');

if (process.argv.length < 2) {
	console.log("usage: build <configurationdir>");
} else {
	builder(require('./' + process.argv[2] + '/app.json'), fs.readFileSync(process.argv[2] + '/card.html'), function(dir) {
		console.log(dir + "/output.apk");
	});
}

