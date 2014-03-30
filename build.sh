cp -R -f app/quickglass/build/assets input/
cp -R -f app/quickglass/build/manifests/debug/* input/
cp -R -f app/quickglass/build/res/all/debug/* input/res/

aapt package -v -f --no-crunch -M input/AndroidManifest.xml -I `cat .android.jar` -A input/assets --auto-add-overlay --generate-dependencies -S input/res -F output.apk app/quickglass/build/dex/debug

jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ~/.android/debug.keystore output.apk androiddebugkey -storepass android

