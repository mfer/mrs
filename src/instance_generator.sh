#!/bin/bash

minLength=0.1
maxLength=1.0
minZ=1.0
maxZ=1.1
escala=3.0

for nLinks in 16 32 64 128
do
  for instance in {1..50}
  do
     FILENAME="nLinks_"$nLinks"__minLength_"$minLength"__maxLength_"$maxLength"__minZ_"$minZ"__maxZ_"$maxZ"__escala_"$escala"__instance_"$instance
     python gerador.py ./instances/$FILENAME $nLinks $minLength $maxLength $minZ $maxZ $escala
  done
done
