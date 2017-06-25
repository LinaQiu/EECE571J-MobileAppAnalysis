import os, csv

ROOT_DIR="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink"
ICCTA_ROOT_DIR="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/IccTA-FDroid-longSASL-ContextSensitive-Defaultlayoutmode"
AMANDROID_ROOT_DIR="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/Amandroid-FDroid-longSASL-For1S"

SINKS_FREQ_MAP_CSV_ICCTA=ROOT_DIR+"/sinks_freq-each-app-IccTA.csv"
SINKS_FREQ_MAP_CSV_AMANDROID=ROOT_DIR+"/sinks_freq-each-app-Amandroid.csv"
SINKS_FREQ_MAP_WITHOUT_SINKPARA_CSV_AMANDROID=ROOT_DIR+"/sinks_freq-each-app-Amandroid-withoutSinkPara.csv"

class extract_1sink_by_frequency_ICCTA_Amandroid(object):
	"""docstring for extract_1sink_by_frequency_ICCTA_Amandroid"""
	def __init__(self):
		super(extract_1sink_by_frequency_ICCTA_Amandroid, self).__init__()
		self.SINKS_FREQ_MAP_ICCTA={}
		self.SINKS_FREQ_MAP_AMANDROID={}
		self.SINKS_FREQ_MAP_AMANDROID_WITHOUT_SINK_PARA={}
		self.START_OF_SINKS_AMANDROID=False
		self.START_OF_TAINT_PATH_AMANDROID=False
		self.APP_NAME=[]
		self.ICCTA_SINKS=[]
		self.AMANDROID_SINKS=[]
		self.AMANDROID_SINKS_WITHOUT_PARA=[]
		self.AMANDROID_APP_SINKS={}
		self.ICCTA_APP_SINKS={}

	def ListAllSubFiles(self, rootDir):
		"""
		List all subfiles under a directory.
		"""
		return os.listdir(rootDir)

	def CheckSinkForAllAppsForIccTA(self):
		results=self.ListAllSubFiles(ICCTA_ROOT_DIR)
		for result in results:
			self.CheckSinkForSingleAppForIccTA(ICCTA_ROOT_DIR+"/"+result, result[0:result.index("_long.txt")])
		self.LogSinksMap(self.SINKS_FREQ_MAP_ICCTA, SINKS_FREQ_MAP_CSV_ICCTA)

	def CheckSinkForAllAppsForAmandroid(self):
		results=self.ListAllSubFiles(AMANDROID_ROOT_DIR)
		for result in results:
			self.CheckSinkForSingleAppForAmandroid(AMANDROID_ROOT_DIR+"/"+result, result[0:result.index(".txt")])
		self.LogSinksMap(self.SINKS_FREQ_MAP_AMANDROID, SINKS_FREQ_MAP_CSV_AMANDROID)
		self.LogSinksMap(self.SINKS_FREQ_MAP_AMANDROID_WITHOUT_SINK_PARA, SINKS_FREQ_MAP_WITHOUT_SINKPARA_CSV_AMANDROID)

	def CheckSinkForSingleAppForIccTA(self, infilePath, appName):
		with open(infilePath) as infile:
			self.APP_NAME.append(appName)
			for line in infile:
				if "[main] INFO soot.jimple.infoflow.Infoflow - The sink" in line:
					## Extract sink
					self.CountSinkFreq(line, appName, True)
				else:
					pass

	def CheckSinkForSingleAppForAmandroid(self, infilePath, appName):
		with open(infilePath) as infile:
			for line in infile:
				if "Sinks found:" in line:
					self.START_OF_SINKS_AMANDROID=True
					self.START_OF_TAINT_PATH_AMANDROID=False
				elif "Discovered taint paths are listed below:" in line:
					self.START_OF_TAINT_PATH_AMANDROID=True
				elif self.START_OF_SINKS_AMANDROID is True and self.START_OF_TAINT_PATH_AMANDROID is False:
					self.CountSinkFreq(line, appName, False)

	def CountSinkFreq(self, line, appName, isICCTA):
		"""
		Extract and count the number of sources and sinks by reading each record line.
		"""
		if "<" in line and ">" in line:
			## Extract method sig., and count frequency for each source/sink method.
			methodSig=line[line.index("<"):line.index(">")+1] 
			## Append to sinks info. map.
			if isICCTA:
				## Append to ICCTA's sink map. 
				if methodSig in self.ICCTA_SINKS:
					pass
				else:
					self.ICCTA_SINKS.append(methodSig)
				self.UpdateSinksMap(appName, methodSig, self.SINKS_FREQ_MAP_ICCTA)
			else:
				## Append to Amandroid's sink map. 
				if methodSig in self.AMANDROID_SINKS:
					pass
				else:
					self.AMANDROID_SINKS.append(methodSig)
				self.UpdateSinksMap(appName, methodSig, self.SINKS_FREQ_MAP_AMANDROID)
				methodSigWithoutSinkPara=methodSig[0:methodSig.index(">")-2]+">"
				if methodSigWithoutSinkPara in self.AMANDROID_SINKS_WITHOUT_PARA:
					pass
				else:
					self.AMANDROID_SINKS_WITHOUT_PARA.append(methodSigWithoutSinkPara)
				self.UpdateSinksMap(appName, methodSigWithoutSinkPara, self.SINKS_FREQ_MAP_AMANDROID_WITHOUT_SINK_PARA)
		else:
			pass

	def UpdateSinksMap(self, appName, methodSig, mapToRecord):
		if mapToRecord.has_key(appName):
			if mapToRecord[appName].has_key(methodSig):
				mapToRecord[appName][methodSig]+=1
			else:
				mapToRecord[appName][methodSig]=1
		else:
			mapToRecord[appName]={methodSig:1}

	def LogSinksMap(self, sinkMap, outfilePath):
		with open(outfilePath, "w") as outfile:
			writer=csv.writer(outfile)
			writer.writerow(['appName','signatures','frequency'])
			for appName in sinkMap:
				for sink in sinkMap[appName]:
					writer.writerow([appName]+[sink]+[sinkMap[appName][sink]])

	def GerAppSinkTable(self, appList, sinkList):
		for app in appList:
			for sink in sinkList:
				counter

e1s=extract_1sink_by_frequency_ICCTA_Amandroid() 
e1s.CheckSinkForAllAppsForIccTA()
e1s.CheckSinkForAllAppsForAmandroid()
