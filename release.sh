#!/bin/bash

VERSION=$1
mvn versions:set -DnewVersion=$VERSION

mvn clean deploy || exit 1

git add pom.xml && git add SolarPaper-Assistant/pom.xml && git commit -m "Release $VERSION"
cd SolarPaper-API && git add pom.xml && git commit -m "Release $VERSION" && cd ..
cd SolarPaper-Server && git add pom.xml && git commit -m "Release $VERSION" && cd ..

git push && cd SolarPaper-API && git push && cd ../SolarPaper-Server && git push && cd ..
