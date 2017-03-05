#!/bin/bash

# copy all apk files from /home/ubuntu
sudo mv -r /home/ubuntu/APK-Julia $DROIDSAFE_SRC_HOME/android-apps/examples/

# create folder for each apk file in APK-Julia
APK_JULIA_PATH=$DROIDSAFE_SRC_HOME/android-apps/examples/APK_Julia
cd APK_JULIA_PATH

for apk in "${APK_JULIA_PATH}/"*;do
	if [[ $apk="*.apk" ]];then
		NEW_APK_FOLDER=${apk%.*}
		echo "Create folder $NEW_APK_FOLDER"
		mkdir $NEW_APK_FOLDER
		mv $apk $NEW_APK_FOLDER
		cp $DROIDSAFE_SRC_HOME/android-apps/Makefile_apk Makefile
		sed -i -e s/APPNAME/${apk%.*}/ Makefile



