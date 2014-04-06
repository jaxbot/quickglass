#!/bin/bash

# Package using these resources
aapt package -v -f --no-crunch -M $1/AndroidManifest.xml -I `cat .android.jar` -A input/assets --auto-add-overlay --generate-dependencies -S input/res -F $1/output.apk app/quickglass/build/dex/debug

# Sign the new package with a debug key
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ~/.android/debug.keystore $1/output.apk androiddebugkey -storepass android

