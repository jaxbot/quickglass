#!/bin/bash

# Copy over the latest version of QuickGlass's compiled core
cp -R -f app/quickglass/build/assets input/
cp -R -f app/quickglass/build/manifests/debug/* input/
cp -R -f app/quickglass/build/res/all/debug/* input/res/

# Make a temp directory to store our stuff in
TMPDIR=$(mktemp -d)

# Copy our manifest over to the temp dir for manipulation
cp input/AndroidManifest.xml $TMPDIR/

# Package using these resources
aapt package -v -f --no-crunch -M $TMPDIR/AndroidManifest.xml -I `cat .android.jar` -A input/assets --auto-add-overlay --generate-dependencies -S input/res -F $TMPDIR/output.apk app/quickglass/build/dex/debug

# Sign the new package with a debug key
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ~/.android/debug.keystore $TMPDIR/output.apk androiddebugkey -storepass android

