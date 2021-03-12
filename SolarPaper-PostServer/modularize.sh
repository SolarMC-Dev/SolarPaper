#!/bin/bash

JAR_PATH=$1
MODULE_NAME=$2
JDK_VERSION=$3
IS_PREVIEW=$4

MULTI_RELEASE=$JDK_VERSION

PREVIEW=""

if [ "$IS_PREVIEW" = "true" ] ; then
  PREVIEW=" --enable-preview"
  echo "Enabled preview features"
fi

mkdir -p target/module-gen/
JAR_PATH_REMAP="target/module-gen/$MODULE_NAME.jar"

mv $JAR_PATH $JAR_PATH_REMAP
JAR_PATH=$JAR_PATH_REMAP

# Generate module-info.java
jdeps --module-path target/dependency/ --multi-release $MULTI_RELEASE --generate-module-info target/module-gen $JAR_PATH

MODULE_INFO_JAVA="target/module-gen/$MODULE_NAME/versions/$MULTI_RELEASE/module-info.java"
# Remove exports directives
echo "sed -i 's/exports/\/\/ exports/' $MODULE_INFO_JAVA"
sed -i 's/exports/\/\/ exports/' $MODULE_INFO_JAVA
# Export gg.solarmc.solarserver.config to dazzleconf
echo "sed -i '1i exports gg.solarmc.solarserver.config to space.arim.dazzleconf;' $MODULE_INFO_JAVA"
sed -i '2i exports gg.solarmc.solarserver.config to space.arim.dazzleconf;' $MODULE_INFO_JAVA

# Compile module-info.java
javac --module-path target/dependency/$PREVIEW --release $JDK_VERSION --patch-module $MODULE_NAME=$JAR_PATH $MODULE_INFO_JAVA

# Reseal jar
cd target/module-gen/$MODULE_NAME/
echo "Creating final jar"
UPPER_JAR_PATH="../../../$JAR_PATH"
jar --update --file $UPPER_JAR_PATH -C . module-info.class
cd ../../../

