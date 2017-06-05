import os

DS_SOURCE_SINK_PATH="/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/DroidSafe/SourcesAndSinks_DroidSafe.txt"
INTERSECT_SOURCE_SINK_PATH="/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/FlowDroid/SourcesAndSinks_Intersect.txt"
AMANDROID_SOURCE_SINK_LONG_PATH="/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/Amandroid/SourcesAndSinks_long_Amandroid.txt"
AMANDROID_SOURCE_SINK_SHORT_PATH="/Users/lina/Documents/EECE571J/Project/EECE571J-MobileAppAnalysis/Amandroid/SourcesAndSinks_short_Amandroid.txt"

class FormatSourcesSinks(object):
	"""This class does the job to transfer all sources/sinks method from FlowDroid format to Amandroid format."""
	def __init__(self):
		super(FormatSourcesSinks, self).__init__()
		self.TRANSFORMED_SOURCES_SINKS = []	## This is a list created to store all transformed source and sink methods.

	def LogLine(self, outfilePath, dataToLog):
		with open(outfilePath,"w") as outfile:
			if dataToLog:
				for item in dataToLog:
					outfile.write(item)

	def FormatSourceSinkToAmandroidVer(self, originalSourceSink):
		"""
		This function performs the duty to transfer all source methods from FlowDroid format to Amandroid format.
		Example1: java.lang.String getDeviceId()
		FlowDroid format: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
		Amandroid format: Landroid/telephony/TelephonyManager;.getDeviceId:()Ljava/lang/String; SENSITIVE_INFO -> _SOURCE_
		
		Example2: int Log.d(String,String)
		FlowDroid format: <android.util.Log: int d(java.lang.String,java.lang.String)> -> _SINK_
		Amandroid format: Landroid/util/Log;.d:(Ljava/lang/String;Ljava/lang/String;)I -> _SINK_
		"""
		indexOfLeftArrow=originalSourceSink.index('<')
		indexOfRightArrow=originalSourceSink.index('> ')
		signature=originalSourceSink[indexOfLeftArrow+1:indexOfRightArrow]
		signatures=signature.split(" ")
		classType=signatures[0][0:len(signatures[0])-1]
		returnType=signatures[1]
		
		methodSignature=signatures[2]
		indexOfLeftBracket=methodSignature.index("(")
		indexOfRightBracket=methodSignature.index(")")

		methodName=methodSignature[0:indexOfLeftBracket]
		paraSigs=(methodSignature[indexOfLeftBracket+1:indexOfRightBracket]).split(",")
		numOfPara=len(paraSigs)
		paraFinalSig=""
		if numOfPara>=1:
			for i in xrange(0,numOfPara):
				paraFinalSig+=self.FormatTypeToSignature(paraSigs[i])

		classSig=self.FormatTypeToSignature(classType)
		returnSig=self.FormatTypeToSignature(returnType)

		if "_SOURCE_" in originalSourceSink:
			return classSig+"."+methodName+":("+paraFinalSig+")"+returnSig+" SENSITIVE_INFO -> _SOURCE_"
		elif "_SINK_" in originalSourceSink:
			return classSig+"."+methodName+":("+paraFinalSig+")"+returnSig+" -> _SINK_"

	def FormatTypeToSignature(self, Type):
		"""
		This class is used to format type to signature, as what Amandroid has done.
		Examples:
		byte --> B, char --> C, etc.
		"""
		switcher={
			"byte": "B",
			"char": "C",
			"double": "D",
			"float": "F",
			"int": "I",
			"long": "J",
			"short": "S",
			"boolean": "Z",
			"void": "V"
		}
		paraDim, Type=self.CalArrayDimension(Type)
		paraSig=""
		while paraDim>0:
			paraSig+="["
			paraDim-=1

		paraSig+=switcher.get(Type,"L"+Type.replace(".","/")+";")
		
		return paraSig

	def CalArrayDimension(self, Type):
		"""
		This class is used to calculate the dimension of an array.
		Examples: String[] --> 1, String[][] --> 2
		"""
		counter=0
		if "[]" in Type:	
			firstSquareBracket=Type.index("[]")
			lastSquareBracket=Type.rindex("[]")
			if firstSquareBracket==lastSquareBracket:
				counter=1
			else:
				counter=(lastSquareBracket-firstSquareBracket)/2
		if counter>0:
			Type=Type[0:Type.index("[]")]		
		return counter, Type

	def Transformation(self, infilePath, outfilePath):
		self.TRANSFORMED_SOURCES_SINKS=[]
		with open(infilePath) as infile:
			for line in infile:
				# print line
				self.TRANSFORMED_SOURCES_SINKS.append(self.FormatSourceSinkToAmandroidVer(line)+"\n")
		self.LogLine(outfilePath, self.TRANSFORMED_SOURCES_SINKS)

fss=FormatSourcesSinks()
fss.Transformation(DS_SOURCE_SINK_PATH, AMANDROID_SOURCE_SINK_LONG_PATH)
fss.Transformation(INTERSECT_SOURCE_SINK_PATH, AMANDROID_SOURCE_SINK_SHORT_PATH)