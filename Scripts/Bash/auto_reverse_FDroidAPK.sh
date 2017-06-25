#!/bin/bash

FDROID_APK_ROOT_PATH="/Volumes/LinaQiuHD/Master/EECE571J/Apk/FDroid-new"

# Change working directory 
cd $FDROID_APK_ROOT_PATH

echo "Start to reverse engineering all apk files in $FDROID_APK_ROOT_PATH now..."
echo $FDROID_APK_ROOT_PATH

# Define folder names used to store relevant outputs
#unzip="unzip"
#apktool="apktool"
dex2jar="dex2jar"

for subdir in "${FDROID_APK_ROOT_PATH}/"*; do
	if [ -d "$subdir" ]; then
		echo "$subdir"
		cd $subdir
		for apk in "${subdir}/"*; do
			if [[ $apk="*.apk" ]];then
				echo "Begin to reverse engineer $apk ..."
				appFolder=${apk%.*}
				mkdir $appFolder
				cd $appFolder
				mkdir $dex2jar
				cd $dex2jar
                		d2j-dex2jar.sh $apk
				cd ..
				mv $apk .
				echo "Finished."
				cd $subdir
			fi
		done
	fi
done
echo "Finished to reverse enigneering all apk files in $FDROID_APK_ROOT_PATH."
