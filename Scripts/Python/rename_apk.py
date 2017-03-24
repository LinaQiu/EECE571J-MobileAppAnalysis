import os, csv

#APK_ROOT_FOLDER = "/Users/lina/Documents/EECE571J/Project/APK_Julia"
APK_ROOT_FOLDER = "/Volumes/SeagateBackupPlusDrive/Master/EECE571J/googleplay-toplist_20150130"
FDROID_APK_ROOT_FOLDER = "/Volumes/SeagateBackupPlusDrive/Master/EECE571J/FDroid"
MB = 1000000
KB = 1000

class DroidsafeHelper(object):
	"""docstring for DroidsafeHelper"""
	def __init__(self):
		super(DroidsafeHelper, self).__init__()
		self.apk_size_mapping = []
		self.apk_new_name = ""
		self.apk_dex2jar_size_mapping = []

	def ConstructSizeStringFromBytes(self, apkSize):
		if apkSize/MB>=1:
			return str(round(float(apkSize)/MB,1))+"M"
		elif apkSize/KB>0 and apkSize/MB<1:
			return str(int(round(float(apkSize)/KB,0)))+"K"
		else:
			print apkSize
			print "The APK file is either too small or too big."


	def RetrieveAPKSize(self, apkFilePath):
		os.chdir(apkFilePath)
		initAPKNames = os.listdir(apkFilePath)
		for apk in initAPKNames:
			if "apk" in apk:
				apkInfo = os.stat(apk)
				apkSize = apkInfo.st_size
				self.apk_size_mapping.append([apk,self.ConstructSizeStringFromBytes(apkSize)])
			else:
				continue
				

	def RenameAPKWithSize(self, apkFilePath):
		os.chdir(apkFilePath)
		self.RetrieveAPKSize(apkFilePath)
	 	for apkInfo in enumerate(self.apk_size_mapping):
	 		print apkInfo[1][0]
	 		print apkInfo[1][1]
	 		os.rename(apkInfo[1][0],apkInfo[1][1]+"_"+apkInfo[1][0])
	 		os.chdir(APKFolderPath)

	def RenameAPKWithSize(self, apkFilePath, originalAPKName, newAPKName):
		os.chdir(apkFilePath)
		print apkFilePath+"\n"+originalAPKName+"\n"+newAPKName
		os.rename(originalAPKName, newAPKName)

	def _total_size(self, source):
		total_size = os.path.getsize(source)
		for item in os.listdir(source):
			itempath = os.path.join(source, item)
			if os.path.isfile(itempath):                
				total_size += os.path.getsize(itempath)
			elif os.path.isdir(itempath):
				total_size += self._total_size(itempath)
		return total_size

	def WriteLogs(self, data, outfilePath):
		with open(outfilePath,"wb") as outfile:
			writer = csv.writer(outfile)
			writer.writerow(["apkName","apkSizeInByte","apkSizeReadable","dex2jarSizeInByte","dex2jarSizeReadable"])
			writer.writerows(data)
			print "Finished to write file to a csv."

	def ListJarAndAPKSize(self, APKFolderPath):
		os.chdir(APKFolderPath)
		reversedAPKList = os.listdir(APKFolderPath)
		for reversedAPK in reversedAPKList:
			if ".csv" in reversedAPK or ".DS_Store" in reversedAPK:
				continue
			else:
				#self.RenameAPKWithSize(reversedAPK)
				#reversedAPKSize = reversedAPK[:reversedAPK.index("_")]
				reversedAPKPath = APKFolderPath+"/"+reversedAPK
				os.chdir(reversedAPKPath)
				fileList = os.listdir(reversedAPKPath)
				for item in fileList:
					print item
					if ".apk" in item:
						#Uncomment the following line if the apk file name is renamed previously with apk size at the beginning
						#apkName = item[item.index("_")+1:len(item)]
						apkName=item
						originalAPKSize = os.stat(item).st_size
						apkSize = self.ConstructSizeStringFromBytes(originalAPKSize)
					elif "dex2jar" in item:
						originaldex2jarSize = self._total_size(reversedAPKPath+"/dex2jar")
						dex2jarSize = self.ConstructSizeStringFromBytes(originaldex2jarSize)
				# In case some apks were renamed successfully, while others were not. And the script was ran for several times.
				print "dex2jarSize: "
				print dex2jarSize
				if dex2jarSize is not None:
					originalAPKNames = apkName.split(str(dex2jarSize+"_"))
				else:
					print "No jar file found: "+subdirectory+"/"+apkName
					continue
				originalAPKName=originalAPKNames[len(originalAPKNames)-1]
				newAPKName = str(dex2jarSize)+"_"+originalAPKName
				self.RenameAPKWithSize(reversedAPKPath, apkName, newAPKName)
				self.apk_dex2jar_size_mapping.append([apkName,originalAPKSize,apkSize,originaldex2jarSize,dex2jarSize])
		self.WriteLogs(self.apk_dex2jar_size_mapping, APKFolderPath+"/apk_info.csv")

droidsafeHelper = DroidsafeHelper()
#droidsafeHelper.RenameAPKWithSize(APK_ROOT_FOLDER)
#droidsafeHelper.ListJarAndAPKSize(APK_ROOT_FOLDER)		

dirs=next(os.walk('/Volumes/SeagateBackupPlusDrive/Master/EECE571J/FDroid'))[1]
counter=0
for subdirectory in dirs:
	droidsafeHelper.apk_dex2jar_size_mapping = []
	subroot="/Volumes/SeagateBackupPlusDrive/Master/EECE571J/FDroid/"+subdirectory
	droidsafeHelper.ListJarAndAPKSize(subroot)
	counter+=1
print counter
print "Done."

