import os

FDroid_DOWNLOAD_PATH="/Volumes/SeagateBackupPlusDrive/Master/EECE571J/FDroid"

class CountAPK(object):
	"""docstring for CountAPK"""
	def __init__(self):
		super(CountAPK, self).__init__()
		
	def counterAPK(self, rootPath):
		dirs=os.walk(rootPath)
		categories=[]
		for directory in dirs:
			categories.append(directory[0])
		for category in categories:
			self.counter(category)

	def counter(self, apkFolder):
		files=os.walk(apkFolder)
		counter=0
		for apkfile in files:
			apks=apkfile[2]
			for apk in apks:
				if ".apk" in apk:
					counter+=1
		print "In "+apkFolder+", we have "+str(counter)+" apk files."
		
counter=CountAPK()
counter.counterAPK(FDroid_DOWNLOAD_PATH)