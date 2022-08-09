@echo off
echo Buliding Java..
javac BirdyNote.java
pause
rmdir /S /Q "./Bulid"
mkdir Bulid
cd "./RES"
copy *.* "./../Bulid"
cd ..
move *.class "./Bulid"
echo Generating Jar..
cd "./bulid"
jar -cvmf ../BulidManifest.mf BirdyNote.jar *.*
echo Starting From Bulid..
java -cp . BirdyNote
cd ./..