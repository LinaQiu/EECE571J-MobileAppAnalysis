# -*- coding: utf-8 -*-
import os
import csv
## This is a script used to extract 1 most used source and sink.
## 1. We first extract all sources and sinks from analysis results on 17 FDroid apps, with FlowDroid, and DroidSafe's long sources/sinks list.
## 2. We then count the frequency of each source and sink method, and categorize all sources/sinks by its frequency in descending order. 
## 3. The source/sink on the top of list is what we are looking for.

ROOT_DIR="/Volumes/SeagateBackupPlusDrive/Master/EECE571J/1Source1Sink/FlowDroid-FDroid-DSSASL-ContextSensitive-Nolayoutmode-For1S1S"
SOURCES_LOG_TXT="/Volumes/SeagateBackupPlusDrive/Master/EECE571J/1Source1Sink/Sources-Sinks/sources.txt"
SINKS_LOG_TXT="/Volumes/SeagateBackupPlusDrive/Master/EECE571J/1Source1Sink/Sources-Sinks/sinks.txt"
SOURCES_FREQ_MAP_LOG_CSV="/Volumes/SeagateBackupPlusDrive/Master/EECE571J/1Source1Sink/Sources-Sinks/sources_freq.csv"
SINKS_FREQ_MAP_LOG_CSV="/Volumes/SeagateBackupPlusDrive/Master/EECE571J/1Source1Sink/Sources-Sinks/sinks_freq.csv"


class extract_1source1sink(object):
	"""docstring for extract_1source1sink"""
	def __init__(self):
		super(extract_1source1sink, self).__init__()
		self.SOURCES_FREQ_MAP = {}
		self.SINKS_FREQ_MAP = {}

	def ListAllSubFiles(self, rootDir):
		"""
		List all subfiles under a directory.
		"""
		return os.listdir(rootDir)

	def CheckSourcesSinksForAllApps(self):
		results=self.ListAllSubFiles(ROOT_DIR)
		for result in results:
			self.CheckSourceSinkForSingleApp(ROOT_DIR+"/"+result)
		## Once we finished to check sources/sinks for all apps, then we write out the results.
		# print self.SOURCES_FREQ_MAP
		# print self.SINKS_FREQ_MAP
		self.OrderAndLogSourceSinkByFreq(SOURCES_LOG_TXT, SINKS_LOG_TXT)
		self.LogMap(self.SOURCES_FREQ_MAP, SOURCES_FREQ_MAP_LOG_CSV)
		self.LogMap(self.SINKS_FREQ_MAP, SINKS_FREQ_MAP_LOG_CSV)


	def CheckSourceSinkForSingleApp(self, infilePath):
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
					self.CountSourceSinkFreq(line, True)
				elif START_OF_COLLECTED_SINKS is True:
					## Starts to extract sinks information here.
					self.CountSourceSinkFreq(line, False)

	def CountSourceSinkFreq(self, line, isSource):
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
				if self.SINKS_FREQ_MAP.has_key(methodSig):
					count=self.SINKS_FREQ_MAP[methodSig]
					count+=1
					self.SINKS_FREQ_MAP[methodSig]=count
				else:
					self.SINKS_FREQ_MAP[methodSig]=1
		else:
			pass

	def OrderAndLogSourceSinkByFreq(self, sourceFilePath, sinkFilePath):
		"""
		After reading sources and sinks info. for all FDroid apps, get an ordered sources and sinks list, 
		and write out, both the sources/sinks ordered list, and the corresponding map.
		"""
		sourcesList=sorted(self.SOURCES_FREQ_MAP, key=self.SOURCES_FREQ_MAP.__getitem__, reverse=True)
		sinksList=sorted(self.SINKS_FREQ_MAP, key=self.SINKS_FREQ_MAP.__getitem__, reverse=True)
		self.Log(sourcesList, sourceFilePath)
		self.Log(sinksList, sinkFilePath)

	def Log(self, datList, outfilePath):
		"""
		Method used to write all items in sources/sinks list out to a text file. 
		"""
		with open(outfilePath, "w") as outfile:
			for items in datList:
				outfile.write(items+"\n")

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







		