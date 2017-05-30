## This file is used to get the intersection for sources and sinks list of flowdroid and droidsafe.
## Flowdroid's sources and sinks list is stored at /Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/FlowDroid/SourcesAndSinks_FlowDroid.txt
## Droidsafe's sources and sinks list is stored at /Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/DroidSafe/SourcesAndSinks_DroidSafe.txt

FLOWDROID_PATH="/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/FlowDroid/SourcesAndSinks_FlowDroid.txt"
DROIDSAFE_PATH="/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/DroidSafe/SourcesAndSinks_DroidSafe.txt"
INTERSECT_PATH="/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/FlowDroid/SourcesAndSinks_Intersect.txt"
INTERSECT_LIST=[]
DROIDSAFE_LIST=[]

def nonblank_lines(f):
    for l in f:
        line = l.rstrip()
        if line:
            yield line

with open(DROIDSAFE_PATH) as infile:
	for line in infile:
		DROIDSAFE_LIST.append(line.replace("\r\n",""))

## Read the flowdroid's sources and sinks, and then check whether it exists in droidsafe's sources and sinks list.
## Note that we do have special source and sink lines, like the following:
## <android.telephony.TelephonyManager: java.lang.String getDeviceId()> android.permission.READ_PHONE_STATE -> _SOURCE_
## And droidsafe store this source as:
## <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
with open(FLOWDROID_PATH) as infile:
	print DROIDSAFE_LIST
	for line in nonblank_lines(infile):
		if "%" in line:
			continue
		else:
			if "permission" in line:
				itemsToCheck=line[line.index("<"):line.index(">")+1]+line[line.index("-")-1:len(line)+1]
			else:
				itemsToCheck=line
			
			if itemsToCheck in DROIDSAFE_LIST:
				INTERSECT_LIST.append(line)

## Log out the intersection of sources and sinks
sourceCounter=0
sinkCounter=0
with open(INTERSECT_PATH,'wb') as outfile:
	for item in INTERSECT_LIST:
		outfile.write(item+"\n")
		if "_SOURCE_" in item:
			sourceCounter+=1
		elif "_SINK_" in item:
			sinkCounter+=1
	print "Write out "+str(sourceCounter)+" sources."
	print "Write out "+str(sinkCounter)+" sinks."





