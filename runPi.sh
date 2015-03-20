#!/bin/bash

mainClass=$1

echo
echo " ----------------- Lets run this awesome pi ------------------"
echo "Running: $mainClass"

cd target
sudo java -cp .:classes:/opt/pi4j/lib/'*':sunfounder-raspi-4j-*.jar $mainClass

