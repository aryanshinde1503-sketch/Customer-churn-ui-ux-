#!/bin/sh
basedir=$(dirname "$0")
export JAVA_HOME=${JAVA_HOME:-/Library/Java/JavaVirtualMachines/jdk-25.jdk/Contents/Home}
exec "$basedir/../apache-maven-3.9.6/bin/mvn" "$@"

