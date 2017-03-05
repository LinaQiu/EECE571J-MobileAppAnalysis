import os

APK_ROOT_FOLDER = "/Users/lina/Documents/EECE571J/Project/APK-Julia"
MB = 1000000
KB = 1000

class DroidsafeHelper(object):
	"""docstring for DroidsafeHelper"""
	def __init__(self):
		super(DroidsafeHelper, self).__init__()
		self.apk_size_mapping = []
		self.apk_new_name = ""

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

droidsafeHelper = DroidsafeHelper()
droidsafeHelper.RenameAPKWithSize(APK_ROOT_FOLDER)
		
