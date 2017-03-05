#!/bin/bash

# copy all apk files from /home/ubuntu
cp -r /home/ubuntu/APK_Julia $DROIDSAFE_SRC_HOME/android-apps/examples/

# create folder for each apk file in APK-Julia
APK_JULIA_PATH=$DROIDSAFE_SRC_HOME/android-apps/examples/APK_Julia
cd $APK_JULIA_PATH

for apk in "${APK_JULIA_PATH}/"*;do
	if [ $apk="*.apk" ];then
		NEW_APK_FOLDER=${apk%.*}
		APP_NAME=${NEW_APK_FOLDER##\/*\/}
		echo "Create folder $NEW_APK_FOLDER"
		mkdir $NEW_APK_FOLDER
		mv $apk $NEW_APK_FOLDER
		cp $DROIDSAFE_SRC_HOME/android-apps/Makefile_apk $NEW_APK_FOLDER/Makefile
		#sed -ie 's/APPNAME/'$NEW_APK_FOLDER'/' $NEW_APK_FOLDER/Makefile
		echo $APP_NAME
		sed -i 's,^\(NAME := \).*,\1'$APP_NAME',' $NEW_APK_FOLDER/Makefile
	fi
done



