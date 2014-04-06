#!/bin/bash

# Make a temp directory to store our stuff in
TMPDIR=$(mktemp -d)

mkdir $TMPDIR/input

# Copy over the latest version of QuickGlass's compiled core
cp -R -f app/quickglass/build/assets $TMPDIR/input
cp -R -f app/quickglass/build/manifests/debug/* $TMPDIR/input/
cp -R -f app/quickglass/build/res/all/debug/* $TMPDIR/input/res/

# Echo out where we're doing our work
echo $TMPDIR

