#!/bin/bash

VERSION=$1

echo "Releasing version $VERSION"

./update-submodules.sh || exit 1

mvn versions:set -DnewVersion=$VERSION

mvn clean deploy || exit 1

cd SolarPaper-API && git add pom.xml && git commit -m "Release $VERSION" && cd ..
cd SolarPaper-Server && git add pom.xml && git commit -m "Release $VERSION" && cd ..
git add . && git commit -m "Release $VERSION"
git tag $VERSION

git push && cd SolarPaper-API && git push && cd ../SolarPaper-Server && git push && cd ..
