#!/bin/sh
#
# Script to install the android sdk in a jenkins node.
# Requires an argument indicating where the sdk should be located.
#
sdk_home=$1
echo "Using android sdk: ${sdk_home}"

if [[ ! -d $sdk_home ]]; then
    echo "Android sdk not found under ${sdk_home}"
    echo "Installing android sdk..."

    mkdir -p $sdk_home
    cd $sdk_home

    if [ "$(uname)" == "Darwin" ]; then

        echo "Installing android sdk for Mac OS X"
        curl "http://dl.google.com/android/android-sdk_r24.0.2-macosx.zip" -o "android-sdk_r24.0.2-macosx.zip"
        unzip android-sdk_r24.0.2-macosx.zip
        mv android-sdk-macosx/* .
        rm -rf android-sdk_r24.0.2-macosx
        rm -rf android-sdk-macosx/
    elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then

        echo "Installing android sdk for Linux"
        wget http://dl.google.com/android/android-sdk_r24-linux.tgz
        tar -xzf android-sdk_r24-linux.tgz
        mv android-sdk-linux/* .
        rm -rf android-sdk_r24-linux.tgz
        rm -rf android-sdk-linux/
    fi

    echo "Android sdk installation done."

    $sdk_home/tools/android list sdk
    $sdk_home/tools/android list target
fi