#!/bin/bash

# Change working directory 
cd $EECE571J_APK_PATH

echo "Start to reverse engineering all apk files in $EECE571J_APK_PATH now..."
echo $EECE571J_APK_PATH

for apk in "${EECE571J_APK_PATH}/"*; do
	if [[ $apk="*.apk" ]];then
		echo "Begin to reverse engineering $apk ..."
        	apktool d $apk
		echo "Finished."
	fi
done
echo "Finished to reverse enigneering all apk files in $EECE571J_APK_PATH."
