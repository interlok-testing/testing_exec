# Exec Testing

[![license](https://img.shields.io/github/license/interlok-testing/testing_exec.svg)](https://github.com/interlok-testing/testing_exec/blob/develop/LICENSE)
[![Actions Status](https://github.com/interlok-testing/testing_exec/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/interlok-testing/testing_exec/actions/workflows/gradle-build.yml)

Project tests interlok-exec features

## What it does

This project contains a single Interlok instance that demonstrates executing scripts on the host operating system.

We have a single workflow that will trigger every five seconds creating a new file in the __failed_messages__ directory.  Then every thirty seconds a windows batch script will execute a __del__ of that directory.

```mermaid
graph LR
	HTTP --> Interlok("Interlok")
	subgraph Host
		direction LR
		Interlok --> OS("OS")
	end
```
 
## Getting started

* `./gradlew clean build`
* `(cd ./build/distribution && java -jar lib/interlok-boot.jar --failover bootstrap.properties)`

