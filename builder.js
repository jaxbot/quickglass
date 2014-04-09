module.exports = function(appName, voiceTrigger, htmlData, voicePrompt) {
	var spawn = require('child_process').spawn,
		fs = require('fs'),
		prepare = spawn('./prepare.sh');

	prepare.stdout.on('data', function(data) {
		var tmpdir = data.toString().replace(/\n/,'');

		// manipulate the temporary files
		var strings = tmpdir + "/input/res/values/values.xml";
		var stringxml = fs.readFileSync(strings).toString();

		stringxml = stringxml.replace("{{APPNAME}}", appName);
		stringxml = stringxml.replace("{{VOICETRIGGER}}", voiceTrigger);

		if (voicePrompt) {
			stringxml = stringxml.replace("{{VOICEPROMPT}}", voicePrompt);

			var triggerFile = tmpdir + "/input/res/xml/voice_trigger.xml";
			var triggerData = fs.readFileSync(triggerFile).toString().replace("<!-- input", "<input").replace("/ -->", "/>");
			fs.writeFileSync(triggerFile, triggerData);
			console.log(triggerData);
		}

		fs.writeFileSync(strings, stringxml);

		fs.writeFileSync(tmpdir + "/input/res/raw/card.html", htmlData);
		
		var build = spawn('./build.sh', [tmpdir]);

		build.stdout.on('data', function(data) {
			console.log(data.toString());
		});
		build.stderr.on('data', function(data) {
			console.log(data.toString());
		});
	});
}

