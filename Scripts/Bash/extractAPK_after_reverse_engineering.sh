#!/bin/bash

# Create a new folder to store all extracted apk files
FOLDER_TO_STORE_RESULTS=/Volumes/SeagateBackupPlusDrive/Master/EECE571J/googleplay-onlyapk
mkdir $FOLDER_TO_STORE_RESULTS

# Change woring directory to the folder where we store all reverse engineering results
REVERSE_ENGINEERING_RESULTS_PATH=/Volumes/SeagateBackupPlusDrive/Master/EECE571J/googleplay-toplist_20150130

cd $REVERSE_ENGINEERING_RESULTS_PATH

echo "Start to cp apk files in $REVERSE_ENGINEERING_RESULTS_PATH now..."

for file in "${REVERSE_ENGINEERING_RESULTS_PATH}/"*; do
	if [[ -d $file ]]; then
		cd $file
		for subfile in "${file}/"*; do 
			if [[ $subfile="*.apk" ]]; then
				echo "Begin to copy $subfile ..."
				cp $subfile $FOLDER_TO_STORE_RESULTS
			fi
		done
	elif [[ -f $file ]]; then
		cd $REVERSE_ENGINEERING_RESULTS_PATH
		if [[ $file="*.csv" ]]; then
			echo "Copy the apk_info.csv file..."
			cp $file $FOLDER_TO_STORE_RESULTS
		fi
	fi
done
echo "Finished to copy all apk file to $FOLDER_TO_STORE_RESULTS."

