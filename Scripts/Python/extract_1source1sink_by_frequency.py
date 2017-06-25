# -*- coding: utf-8 -*-
import os
import csv
## This is a script used to extract 1 most used source and sink.
## 1. We first extract all sources and sinks from analysis results on 17 FDroid apps, with FlowDroid, and DroidSafe's long sources/sinks list.
## 2. We then count the frequency of each source and sink method, and categorize all sources/sinks by its frequency in descending order. 
## 3. The source/sink on the top of list is what we are looking for.

ROOT_DIR="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/FlowDroid-FDroid-DSSASL-ContextSensitive-Nolayoutmode-For1S1S"
SOURCES_LOG_TXT="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/sources-new.txt"
SINKS_LOG_TXT="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/sinks-each-app.txt"
SOURCES_FREQ_MAP_LOG_CSV="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/sources_freq-new.csv"
SINKS_FREQ_MAP_LOG_CSV="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/sinks_freq-each-app-FlowDroid.csv"

FLOWS_LOG_TXT="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/flows_withICCSource.txt"
FLOWS_FREQ_MAP_LOG_CSV="/Volumes/LinaQiuHD/Master/EECE571J/1Source1Sink/1Source1Sink/flows_freq_withICCSource1.csv"

class extract_1source1sink(object):
	"""docstring for extract_1source1sink"""
	def __init__(self):
		super(extract_1source1sink, self).__init__()
		self.SOURCES_FREQ_MAP = {}
		self.SINKS_FREQ_MAP = {}
		self.FLOW_FREQ_MAP = {}

	def ListAllSubFiles(self, rootDir):
		"""
		List all subfiles under a directory.
		"""
		return os.listdir(rootDir)

	def CheckFlowsForAllApps(self):
		results=self.ListAllSubFiles(ROOT_DIR)
		for result in results:
			self.CheckFlowsForSingleApp(ROOT_DIR+"/"+result)
		## Once we finished to check sources/sinks for all apps, then we write out the results.
		# print self.SOURCES_FREQ_MAP
		# print self.SINKS_FREQ_MAP
		flowsList=sorted(self.FLOW_FREQ_MAP, key=self.FLOW_FREQ_MAP.__getitem__, reverse=True)
		self.Log(flowsList, FLOWS_LOG_TXT)
		self.LogMap(self.FLOW_FREQ_MAP, FLOWS_FREQ_MAP_LOG_CSV)

	def CheckFlowsForSingleApp(self, infilePath):
		"""
		This method is used to count the frequency for flows from a FDroid app.
		"""
		START_OF_FOUND_A_FLOW=False
		# START_OF_MAX_MEMORY=False
		# START_OF_ON_PATH=False
		with open(infilePath) as infile:
			for line in infile:
				if "Collected sources" in line:
					break
				elif "on Path" in line:
					# START_OF_ON_PATH=True
					continue
				elif "Maximum memory consumption" in line:
					break
				elif "Found a flow to sink" in line:
					START_OF_FOUND_A_FLOW=True
					# START_OF_MAX_MEMORY=False
					START_OF_ON_PATH=False
					sink=self.ExtractSink(line)
				elif START_OF_FOUND_A_FLOW is True:
					source=self.ExtractSource(line)
					if source is None:
						continue
					else:
						self.CountFlowFreq(source, sink)

	def CountFlowFreq(self, source, sink):
		"""
		This is a method used to calculate frequency for flows. 
		"""
		if self.FLOW_FREQ_MAP.has_key((source, sink)):
			count=self.FLOW_FREQ_MAP[(source, sink)]
			count+=1
			self.FLOW_FREQ_MAP[(source, sink)]=count
		else:
			self.FLOW_FREQ_MAP[(source, sink)]=1

	def ExtractSink(self, line):
		"""
		This method is used to extract sink method out from a line of record.
		"""
		print "sink:"+line
		return line[line.index("<"):line.index(">")+1]

	def ExtractSource(self, line):
		"""
		This method is used to extract source method out from a line of record.
		An example of source line: 
		- $r6 = virtualinvoke $r5.<android.telephony.TelephonyManager: java.lang.String getDeviceId()>() (in <lina.ubc.inappcomponents.MainActivity: void onCreate(android.os.Bundle)>)
		"""
		print "source:"+line
		recordHasSource=(line.split("(in "))[0]
		print "recordHasSource:"+recordHasSource
		if "<" in recordHasSource:
			source=recordHasSource[recordHasSource.index("<"):recordHasSource.index(">")+1]		
		#elif ": " in recordHasSource:
		#	source=(recordHasSource[recordHasSource.index(": ")+2:len(recordHasSource)]).replace("\n","")
		else:
			return None
		return source

	def CheckSourcesSinksForAllApps(self):
		results=self.ListAllSubFiles(ROOT_DIR)
		for result in results:
			self.CheckSourceSinkForSingleApp(ROOT_DIR+"/"+result, result[0:result.index(".txt")])
		## Once we finished to check sources/sinks for all apps, then we write out the results.
		# print self.SOURCES_FREQ_MAP
		# print self.SINKS_FREQ_MAP
		self.OrderAndLogSourceSinkByFreq(SOURCES_LOG_TXT, SINKS_LOG_TXT)
		self.LogMap(self.SOURCES_FREQ_MAP, SOURCES_FREQ_MAP_LOG_CSV)
		# self.LogMap(self.SINKS_FREQ_MAP, SINKS_FREQ_MAP_LOG_CSV)
		self.LogSinksMap(self.SINKS_FREQ_MAP, SINKS_FREQ_MAP_LOG_CSV)

	def CheckSourceSinkForSingleApp(self, infilePath, appName):
		"""
		This method is used to count frequency for sources and sinks from a FDroid app.
		"""
		START_OF_COLLECTED_SOURCES=False
		START_OF_COLLECTED_SINKS=False
		with open(infilePath) as infile:
			for line in infile:
				if "Collected sources:" not in line and START_OF_COLLECTED_SOURCES is False:
					pass
				elif "Collected sources:" in line:
					START_OF_COLLECTED_SOURCES=True
				elif "Collected sinks:" in line:
					START_OF_COLLECTED_SINKS=True
				elif START_OF_COLLECTED_SOURCES is True and START_OF_COLLECTED_SINKS is False:
					## Starts to extract sources information here.
					self.CountSourceSinkFreq(line, True, appName)
				elif START_OF_COLLECTED_SINKS is True:
					## Starts to extract sinks information here.
					self.CountSourceSinkFreq(line, False, appName)

	def CountSourceSinkFreq(self, line, isSource, appName):
		"""
		Extract and count the number of sources and sinks by reading each record line.
		"""
		if "<" in line and ">" in line:
			## Extract method sig., and count frequency for each source/sink method.
			methodSig=line[line.index("<"):line.index(">")+1] 
			if isSource:
				## Append to sources info. map.
				if self.SOURCES_FREQ_MAP.has_key(methodSig):
					count=self.SOURCES_FREQ_MAP[methodSig]
					count+=1
					self.SOURCES_FREQ_MAP[methodSig]=count
				else:
					self.SOURCES_FREQ_MAP[methodSig]=1
			else:
				## Append to sinks info. map.
				if self.SINKS_FREQ_MAP.has_key(appName):
					if self.SINKS_FREQ_MAP[appName].has_key(methodSig):
						self.SINKS_FREQ_MAP[appName][methodSig]+=1
					else:
						self.SINKS_FREQ_MAP[appName][methodSig]=1
				else:
					self.SINKS_FREQ_MAP[appName]={methodSig:1}
		else:
			pass

	def OrderAndLogSourceSinkByFreq(self, sourceFilePath, sinkFilePath):
		"""
		After reading sources and sinks info. for all FDroid apps, get an ordered sources and sinks list, 
		and write out, both the sources/sinks ordered list, and the corresponding map.
		"""
		sourcesList=sorted(self.SOURCES_FREQ_MAP, key=self.SOURCES_FREQ_MAP.__getitem__, reverse=True)
		# sinksList=sorted(self.SINKS_FREQ_MAP, key=self.SINKS_FREQ_MAP.__getitem__, reverse=True)
		self.Log(sourcesList, sourceFilePath)
		# self.Log(sinksList, sinkFilePath)

	def LogSinksMap(self, sinkMap, outfilePath):
		with open(outfilePath, "w") as outfile:
			writer=csv.writer(outfile)
			writer.writerow(['appName','signatures','frequency'])
			for appName in sinkMap:
				for sink in sinkMap[appName]:
					writer.writerow([appName]+[sink]+[sinkMap[appName][sink]])


	def Log(self, datList, outfilePath):
		"""
		Method used to write all items in sources/sinks list out to a text file. 
		"""
		with open(outfilePath, "w") as outfile:
			for items in datList:
				outfile.write(items[0]+" --> "+items[1]+"\n")

	def LogMap(self, mapToLog, outfilePath):
		"""
		Method used to write all sources/sinks info., along with their frequency out to a csv file.
		"""
		with open(outfilePath, "wb") as outcsvfile:
			writer=csv.writer(outcsvfile)
			writer.writerow(['signatures','frequency'])
			for key in mapToLog.keys():
				writer.writerow([key]+[mapToLog[key]])

			
e1s1s=extract_1source1sink()
e1s1s.CheckSourcesSinksForAllApps()
# e1s1s.CheckFlowsForAllApps()







		