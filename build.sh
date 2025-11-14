#!/bin/bash
set -e

echo "Building 2D RPG Engine..."

# Create target directory
mkdir -p target/classes

# Compile all Java files
cd src/main/java
javac -d ../../../target/classes $(find . -name "*.java")
cd ../../..

echo "Build successful!"
echo "Run with: java -cp target/classes engine.core.GameEngine"
