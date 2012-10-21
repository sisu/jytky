#!/bin/sh
set -e
ndk-build
ant debug
adb install -r bin/jytky-debug.apk
