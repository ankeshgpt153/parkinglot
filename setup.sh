#!/bin/sh

## Check java version
java -version

## Build and install dependency

mvn clean install -DskipTests=true

## Run test cases
./run_functional_tests

## Run program with ./src/main/resources/parking_lot_file_inputs.txt as input file
./parking_lot.sh ./src/main/resources/parking_lot_file_inputs.txt