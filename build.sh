#!/bin/bash

# Package using these resources
aapt package -v -f --no-crunch -M $1/input/AndroidManifest.xml -I `cat .android.jar` -A $1/input/assets --auto-add-overlay --generate-dependencies -S $1/input/res -F $1/output.apk app/quickglass/build/dex/debug

# Sign the new package with a debug key
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ~/.android/debug.keystore -storepass android $1/output.apk androiddebugkey

