#!/bin/bash

# created by Lina Mar 29, 2017
# modified by Yingying Mar 30, 2017


# Directory that stores all selected FDroid apk
SELECTED_FDROID_PATH="/Volumes/DORA\'S/FDroid_Selected"

# Directory to store all selected FDroid apk files. Only store apk file.
FDROID_SELECTED_ONLY_APK="/Users/dora/Desktop/FDROID_SELECTED_ONLY_APK"




# Create the folder
mkdir $FDROID_SELECTED_ONLY_APK

# Change working directory
#cd $SELECTED_FDROID_PATH



# walk through app in each category
for CATEGORY_DIR in ${SELECTED_FDROID_PATH}/* ;do
	# get the CATEGORY
	CATEGORY=`basename "$CATEGORY_DIR"`
	echo "Processing the category: $CATEGORY ...................."
	# app base dir
	for APP_DIR in $CATEGORY_DIR/* ;do
		# get each APK full path
		for APK in $APP_DIR/*.apk ; do
			# get the APK name
			APK_NAME=`basename "$APK"`
			echo "Coping $APK_NAME ....."
			cp $APK  $FDROID_SELECTED_ONLY_APK/${CATEGORY}_$APK_NAME
			#echo $FDROID_SELECTED_ONLY_APK/${CATEGORY}_$APK_NAME
			#echo $APK_NAME
		done
	done
	echo "The category $CATEGORY processed finished! ...................."
done


echo "Done."
echo "all apks are in: $FDROID_SELECTED_ONLY_APK"


