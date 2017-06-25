import os

DROIDSAFE_LONG_LIST_PATH="/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/DroidSafe/SourcesAndSinks_DroidSafe.txt"
AMANDROID_LONG_LIST_PATH="/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/Amandroid/SourcesAndSinks_long_Amandroid.txt"
DROIDSAFE_LONG_SOURCE_PATH="/Volumes/LinaQiuHD/Master/EECE571J/Results/New SASL/FlowDroid-format/SourcesAndSinks_AS1S.txt"
AMANDROID_LONG_SOURCE_PATH="/Volumes/LinaQiuHD/Master/EECE571J/Results/New SASL/AmanDroid/TaintSourcesAndSinks_AS1S.txt"

DROIDSAFE_SOURCES_FROM_LONG_LIST=[]
AMANDROID_SOURCES_FROM_LONG_LIST=[]

with open(DROIDSAFE_LONG_LIST_PATH) as infile:
	for line in infile:
		if "_SOURCE_" in line:
			DROIDSAFE_SOURCES_FROM_LONG_LIST.append(line)

with open(AMANDROID_LONG_LIST_PATH) as infile:
	for line in infile:
		if "_SOURCE_" in line:
			AMANDROID_SOURCES_FROM_LONG_LIST.append(line)

with open(DROIDSAFE_LONG_SOURCE_PATH, 'w') as outfile:
	for item in DROIDSAFE_SOURCES_FROM_LONG_LIST:
		outfile.write(item)
	outfile.write("<android.content.SharedPreferences$Editor: android.content.SharedPreferences$Editor putInt(java.lang.String,int)> -> _SINK_")

with open(AMANDROID_LONG_SOURCE_PATH, 'w') as outfile:
	for item in AMANDROID_SOURCES_FROM_LONG_LIST:
		outfile.write(item)
	outfile.write("Landroid/content/SharedPreferences$Editor;.putInt:(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor; -> _SINK_")


			