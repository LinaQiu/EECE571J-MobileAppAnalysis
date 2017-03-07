#!/bin/bash

# Change working directory 
cd $EECE571J_APK_PATH

echo "Start to reverse engineering all apk files in $EECE571J_APK_PATH now..."
echo $EECE571J_APK_PATH

# Define folder names used to store relevant outputs
unzip="unzip"
apktool="apktool"
dex2jar="dex2jar"

for apk in "${EECE571J_APK_PATH}/"*; do
	if [[ $apk="*.apk" ]];then
		echo "Begin to reverse engineering $apk ..."
		appFolder=${apk%.*}
		mkdir $appFolder
		cd $appFolder
		mkdir $apktool
		mkdir $unzip
		mkdir $dex2jar
		cd $apktool
		apktool d $apk
		cd ..
		cd $unzip
		unzip $apk -d .
		cd ..
		cd $dex2jar
		d2j-dex2jar.sh $apk
		cd ..
		echo "Finished."
		mv $apk .
		cd $EECE571J_APK_PATH
	fi
done
echo "Finished to reverse enigneering all apk files in $EECE571J_APK_PATH."
