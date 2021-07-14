#!/bin/bash

VERSION=$1

echo "Setting version $VERSION"

./update-submodules.sh || exit 1

mvn versions:set -DnewVersion=$VERSION || exit 1

cd SolarPaper-API && git add pom.xml && git commit -m "Update to $VERSION" && git push && cd ..
cd SolarPaper-Server && git add pom.xml && git commit -m "Update to $VERSION" && git push && cd ..
git add . && git commit -m "Update to $VERSION" && git push

