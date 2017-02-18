# Flowdroid Tutorial
Jar and config file compilation for UBC EECE571J

Command for running flowdroid

-- Windows:
```
java -Xmx4g -cp soot-trunk.jar;soot-infoflow.jar;soot-infoflow-android.jar;slf4j-api-1.7.5.jar;slf4j-simple-1.7.5.jar;axml-2.0.jar     soot.jimple.infoflow.android.TestApps.Test "C:\mobile571\flowdroid\nightlyBuilds\leakyApp.apk" C:\Users\sampr\AppData\Local\Android\sdk\platforms

```




-------------------------------------------------

APK file location: /Users/dora/FlowDroid/APKs
SDK platform location: /Users/dora/Library/Android/sdk/platforms 
FlowDroid location: /Users/dora/FlowDroid/flowdroid 

-- Mac:

java -Xmx4g -cp soot-trunk.jar:soot-infoflow.jar:soot-infoflow-android.jar:slf4j-api-1.7.5.jar:slf4j-simple-1.7.5.jar:axml-2.0.jar     soot.jimple.infoflow.android.TestApps.Test "/Users/dora/FlowDroid/APKs/exactlysame.apk" /Users/dora/Library/Android/sdk/platforms

	--TIMEOUT n Time out after n seconds
	--SYSTIMEOUT n Hard time out (kill process) after n seconds, Unix only
	--SINGLEFLOW Stop after finding first leak
	--IMPLICIT Enable implicit flows
	--NOSTATIC Disable static field tracking
	--NOEXCEPTIONS Disable exception tracking
	--APLENGTH n Set access path length to n
	--CGALGO x Use callgraph algorithm x
	--NOCALLBACKS Disable callback analysis
	--LAYOUTMODE x Set UI control analysis mode to x
	--ALIASFLOWINS Use a flow insensitive alias search
	--NOPATHS Do not compute result paths
	--AGGRESSIVETW Use taint wrapper in aggressive mode
	--PATHALGO Use path reconstruction algorithm x
	--LIBSUMTW Use library summary taint wrapper
	--SUMMARYPATH Path to library summaries
	--SYSFLOWS Also analyze classes in system packages
	--NOTAINTWRAPPER Disables the use of taint wrappers
	--NOTYPETIGHTENING Disables the use of taint wrappers
	--LOGSOURCESANDSINKS Print out concrete source/sink instances
	--CALLBACKANALYZER x Uses callback analysis algorithm x
	--MAXTHREADNUM x Sets the maximum number of threads to be used by the analysis to x

Supported callgraph algorithms: AUTO, CHA, RTA, VTA, SPARK, GEOM
Supported layout mode algorithms: NONE, PWD, ALL
Supported path algorithms: CONTEXTSENSITIVE, CONTEXTINSENSITIVE, SOURCESONLY
Supported callback algorithms: DEFAULT, FAST
