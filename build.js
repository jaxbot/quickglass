var spawn = require('child_process').spawn,
	prepare = spawn('./prepare.sh');

prepare.stdout.on('data', function(data) {
	var tmpdir = data.toString(),
	    build = spawn('./build.sh', [tmpdir.replace(/\n/,'')]);

	build.stdout.on('data', function(data) {
		console.log(data.toString());
	});
	build.stderr.on('data', function(data) {
		console.log(data.toString());
	});
});

