#!/bin/sh

mvn clean package
mvn dependency:copy-dependencies -DoutputDirectory=bin/lib
cp target/scala-code-gen-*.jar bin/lib

