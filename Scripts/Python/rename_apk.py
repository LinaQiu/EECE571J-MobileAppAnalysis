import os, csv

APK_ROOT_FOLDER = "/Users/lina/Documents/EECE571J/Project/APK_Julia"
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


	def RetrieveAPKSize(self, APKFolderPath):
		os.chdir(APKFolderPath)
		initAPKNames = os.listdir(APKFolderPath)
		for apk in initAPKNames:
			if "apk" in apk:
				apkInfo = os.stat(apk)
				apkSize = apkInfo.st_size
				self.apk_size_mapping.append([apk,self.ConstructSizeStringFromBytes(apkSize)])
			else:
				continue
				

	def RenameAPKWithSize(self, APKFolderPath):
		os.chdir(APKFolderPath)
		self.RetrieveAPKSize(APKFolderPath)
	 	for apkInfo in enumerate(self.apk_size_mapping):
	 		print apkInfo[1][0]
	 		print apkInfo[1][1]
	 		os.rename(apkInfo[1][0],apkInfo[1][1]+"_"+apkInfo[1][0])

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

	def ListSmaliAndAPKSize(self, APKFolderPath):
		os.chdir(APKFolderPath)
		reversedAPKList = os.listdir(APKFolderPath)
		for reversedAPK in reversedAPKList:
			if ".csv" in reversedAPK or ".DS_Store" in reversedAPK:
				continue
			else:
				reversedAPKSize = reversedAPK[:reversedAPK.index("_")]
				reversedAPKPath = APKFolderPath+"/"+reversedAPK
				os.chdir(reversedAPKPath)
				fileList = os.listdir(reversedAPKPath)
				for item in fileList:
					if ".apk" in item:
						apkName = item[item.index("_")+1:len(item)]
						originalAPKSize = os.stat(item).st_size
					elif "dex2jar" in item:
						originaldex2jarSize = self._total_size(reversedAPKPath+"/dex2jar")
						dex2jarSize = self.ConstructSizeStringFromBytes(originaldex2jarSize)
				self.apk_dex2jar_size_mapping.append([apkName,originalAPKSize,reversedAPKSize,originaldex2jarSize,dex2jarSize])
		self.WriteLogs(self.apk_dex2jar_size_mapping, APKFolderPath+"/apk_info.csv")

droidsafeHelper = DroidsafeHelper()
#droidsafeHelper.RenameAPKWithSize(APK_ROOT_FOLDER)
droidsafeHelper.ListSmaliAndAPKSize(APK_ROOT_FOLDER)		
