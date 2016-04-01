#!/bin/bash

# This script exports Lider Console plugin for Linux (x32, x64), Windows (x32, x64) and MacOSX,
# Lider plugin for Karaf & Ahenk plugin for Ahenk Core
#
# Exported Lider Console plugin can be found under lider-console-usb/target/products directory.
# Exported Lider & Ahenk plugins can also be found under their sub-module target directories.

cd ..
mvn clean install -DskipTests

