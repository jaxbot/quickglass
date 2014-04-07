var builder = require('./builder');

if (process.argv.length < 4) {
	console.log("usage: build <appName> <voiceTrigger> <htmlData>");
} else {
	builder(process.argv[1], process.argv[2], process.argv[3]);
}

