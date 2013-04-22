#! /bin/bash
javac -d classes src/org/insurancedb/data/*.java
javac -d classes -cp classes src/org/insurancedb/model/*.java src/org/insurancedb/controller/*.java src/org/insurancedb/view/gui/*.java src/org/insurancedb/view/tui/*.java src/org/insurancedb/*.java