#!/bin/bash

mvn -f ./cpnami2-Releng/pom.xml validate clean package -Ptravis-build -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
