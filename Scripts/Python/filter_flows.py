## This is a script used to filter out all flows that we care about for DroidSafe.
import os

ROOT_PATH="/Volumes/SeagateBackupPlusDrive/Master/EECE571J/FDroid_results"
DROIDSAFE_RESULTS="/FDroid-DroidSafe-Results"
#DROIDSAFE_FILTERED_RESULTS="/FDroid-DroidSafe-Filtered"
SOURCES_SINKS_LIST="SourcesAndSinks_Intersect.txt"

class FilterFlows(object):
	"""docstring for FilterFlows"""
	def __init__(self):
		super(FilterFlows, self).__init__()
		self.SOURCES=[]
		self.SINKS=[]

	def CreateFolder(self, folderPath):
		"""
		This function is used to create a folder.
		"""
		if not os.path.exists(folderPath):
			os.makedirs(folderPath)

	def ListAllSubFiles(self, rootDir):
		"""
		This function is used to help list all files under folder rootDir, and store the file names into a list.
		"""
		return os.listdir(rootDir)

	def LogOutFlows(self, dataList, outfilePath):
		"""
		This function is used to help write data list out to a txt file.
		"""
		if dataList:
			print ""
			print "Write filtered flows to "+outfilePath+" file..."
			try:
				with open(outfilePath, 'w') as outfile:
					for row in dataList:
						outfile.write(row)
			except Exception, e:
				print "Failed to write filtered flows out for "+outfilePath+" file."
				raise e

	def GetSourceSinkFromStatement(self, statement):
		"""
		This function is used to get the actual source and sink from the statement.
		The pattern of a source and sink is something like this: <...>
		"""
		if "<" in statement and ">" in statement:
			return statement[statement.index("<"):statement.index(">")+1]
		else:
			print "Invalid source and sink statement."
			print statement
			return None

	def ReadSourcesSinks(self, infilePath):
		"""
		This function is used to read all FlowDroid sources and sinks that we care about in to a list.
		"""
		## Initialize the SOURCES and SINKS list everytime when we call this function.
		self.SOURCES=[]
		self.SINKS=[]
		sourceCounter=0
		sinkCounter=0
		with open(infilePath) as infile:
			for line in infile:
				if "_SOURCE_" in line:
					sourceCounter+=1
					source=(line.split("->"))[0]
					print "Source: "+source
					self.SOURCES.append(source)
				elif "_SINK_" in line:
					sinkCounter+=1
					sink=(line.split("->"))[0]
					print "Sink: "+sink
					self.SINKS.append(line)
				else:
					pass
		print "Appended all sources and sinks to our source and sink list."
		print "We have "+str(sourceCounter)+" sources in total."
		print "We have "+str(sinkCounter)+" in sinks total."
		
	def FilterDroidSafeFlows(self, infileFolder):
		"""
		This function is used to filter all droidsafe flows with the generated sources and sinks list.
		"""
		## Get all FlowDroid sources and sinks
		self.ReadSourcesSinks(ROOT_PATH+"/"+SOURCES_SINKS_LIST)

		## Create a folder to store all filtered flows.
		#self.CreateFolder(outfileFolder)

		## Get the name of a list of files that are under infileFolder.
		subfolders=self.ListAllSubFiles(infileFolder)
		print subfolders
		for subfolder in subfolders:
			filteredFlows=[]
			infoFlowFilePath=ROOT_PATH+DROIDSAFE_RESULTS+"/"+subfolder+"/droidsafe-gen/info-flow-results.txt"
			outfilePath=ROOT_PATH+DROIDSAFE_RESULTS+"/"+subfolder+"/droidsafe-gen/info-flow-filtered-results.txt"
			with open(infoFlowFilePath) as infile:
				for line in infile:
					if "Entry Point:" in line or "Sources:" in line or "Lines" in line:
						filteredFlows.append(line)
					elif line=="" or line==None:
						filteredFlows.append(line)
					elif "Sink:" in line:
						sink=self.GetSourceSinkFromStatement(line)
						if sink in self.SINKS:
							filteredFlows.append(line)
						else:
							filteredFlows.append("SINK not in list.\n")
					## $ is a symbol for the start of actual sources
					elif "$" in line:
						source=self.GetSourceSinkFromStatement(line)
						if source in self.SOURCES:
							filteredFlows.append(line)
						else:
							filteredFlows.append("SOURCE not in list.\n")
					elif "FLOW:" in line:
						sourceAndSink=((line.split("|"))[1]).split("<=")
						source=self.GetSourceSinkFromStatement(sourceAndSink[1])
						sink=self.GetSourceSinkFromStatement(sourceAndSink[0])
						if source in self.SOURCES and sink in self.SINKS:
							filteredFlows.append(line)
						else:
							filteredFlows.append("SOURCE or SINK not in list.\n")
					elif line=="\n":
						continue
					else:
						print "Weird line. Need investigation. "+line
			self.LogOutFlows(filteredFlows,outfilePath)
		print "Finished filtering droidsafe flows."
						

droidsafeFilter=FilterFlows()
droidsafeFilter.FilterDroidSafeFlows(ROOT_PATH+"/"+DROIDSAFE_RESULTS)






