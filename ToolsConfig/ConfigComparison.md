* params are case-insensitive
* params mentioned below would be parsed. 

* Un-defined parameters will be passed and the parsing will continue;
* But the parsing will fail and return error, if (1)the value of the params is out of range or (2)the type of the value of the params is wrong

#parsing command-line parameters


The *config* below is:
```
private static InfoflowAndroidConfiguration config = new InfoflowAndroidConfiguration();
```
the *InfoflowAndroidConfiguration* is: [soot.jimple.infoflow.android.InfoflowAndroidConfiguration.java](/soot-infoflow-android/src/soot.jimple.infoflow.android/InfoflowAndroidConfiguration.md)




**1.timeout** √ unit: minute --> manual says seconds ///BUG///
```
--timeout n 
    timeout = n
```
```
runAnalysisTimeout(file, androidjar)
```

**2.systimeout** √ 
```
--systimeout n
    systimeout = n
```
details see _Test.java_
```
runAnalysisSysTimeout(file,androidjar)
```

**3.singleflow** √
Sets whether the information flow analysis shall stop after the first flow has been found 
@param *stopAfterFirstFlow* *True* if the analysis shall stop after the first flow has been found, otherwise false.
```    
--singleflow
	config.setStopAfterFirstFlow(true);
```

```
public void setStopAfterFirstFlow(boolean stopAfterFirstFlow) {
	this.stopAfterFirstKFlows = stopAfterFirstFlow ? 1 : 0;
}
```


**4.implicit** --> `DroidSafe: -implicitFlow TRUE` (not sure whether to use this, maybe in some Solver??)
Sets whether the solver shall consider implicit flows. 
@param enableImplicitFlows True if implicit flows shall be considered, otherwise false.
```
--implicit:
	config.setEnableImplicitFlows(true);
```
```
	public void setEnableImplicitFlows(boolean enableImplicitFlows) {
		this.enableImplicitFlows = enableImplicitFlows;
	}
```


**5.nostatic** --> ? `DroidSafe: -noclinitcontext, -noclonestatics (default: disable static xxx)` √
default value in `infoflowConfiguration` is `private boolean enableStaticFields = true;`;
Use this parameter to disable static analysing; 


Sets whether the solver shall consider assignments to static fields 
@param enableStaticFields True if assignments to static fields shall be considered, otherwise false
```
--nostatic:
	config.setEnableStaticFieldTracking(false);
```
```
public void setEnableStaticFieldTracking(boolean enableStaticFields) {
	this.enableStaticFields = enableStaticFields;
}
```

Finally get called in `infoflow.runAnalysis(final ISourceSinkManager sourcesSinks, final Set<String> additionalSeeds)`
```
		if (config.getEnableStaticFieldTracking()
				&& InfoflowConfiguration.getAccessPathLength() == 0)
			throw new RuntimeException("Static field tracking must be disabled "
					+ "if the access path length is zero");
		if (InfoflowConfiguration.getAccessPathLength() < 0)
			throw new RuntimeException("The access path length may not be negative");

```


**6.aplength** (should be related with accessPathLengthParser staff, not sure yet)
Sets the maximum depth of the access paths. All paths will be truncated if they exceed the given size. 
@param accessPathLength the maximum value of an access path. If it gets longer than this value, it is truncated and all following fields are assumed as tainted  (which is imprecise but gains performance)
Default value is 5.

```
--aplength
    InfoflowAndroidConfiguration.setAccessPathLength(Integer.valueOf(args[i+1]));
```
```
	public static void setAccessPathLength(int accessPathLength) {
		InfoflowConfiguration.accessPathLength = accessPathLength;
	}
```


**7.cgalgo** --> `DroidSafe: -PTApackage [spark|paddle|geo]` (both FD and DS are based on Soot Spark) (√) [CallGraphSelection.md](/CallGraphSelection.md)
Sets the callgraph algorithm to be used by the data flow tracker 
@param algorithm The callgraph algorithm to be used by the data flow tracker
```
--cgalgo [AUTO|CHA|VTA|RTA|SPARK|GEOM]
    AUTO --> config.setCallgraphAlgorithm(CallgraphAlgorithm.AutomaticSelection);
	CHA -->	config.setCallgraphAlgorithm(CallgraphAlgorithm.CHA);
	VTA --> config.setCallgraphAlgorithm(CallgraphAlgorithm.VTA);
	RTA -->	config.setCallgraphAlgorithm(CallgraphAlgorithm.RTA);
	SPARK-->	config.setCallgraphAlgorithm(CallgraphAlgorithm.SPARK);
	GEOM-->	config.setCallgraphAlgorithm(CallgraphAlgorithm.GEOM);
	else --> "Invalid callgraph algorithm"
```	

```
	public void setCallgraphAlgorithm(CallgraphAlgorithm algorithm) {
    	this.callgraphAlgorithm = algorithm;
	}
```
This is set through Soot Options.



**8.nocallbacks** --> `DroidSafe: -nofallback (not tracking unreachable callbacks)`(√) 
in *package soot.jimple.infoflow.android.InfoflowAndroidConfiguration.java*
Sets whether the taint analysis shall consider callbacks 
@param enableCallbacks True if taints shall be tracked through callbacks, otherwise false
```
--nocallbacks
	config.setEnableCallbacks(false); 
```

```
	public void setEnableCallbacks(boolean enableCallbacks) {
		this.enableCallbacks = enableCallbacks;
	}
```
Then, in `SetupApplications,java`, when it calculates Sources,Sinks,EntryPoints, it considers whether enabling callbacks or not:
```
public void calculateSourcesSinksEntrypoints(ISourceSinkDefinitionProvider sourcesAndSinks)
			throws IOException, XmlPullParserException
```
Here is the line : 
```
		if (config.getEnableCallbacks()) {
		....
		
```


**9.noexceptions** --> `DS: -ignoreExceptionFlows (default: enable exception tracking, same as FD)`√
Sets whether the solver shall track taints of thrown exception objects 
@param enableExceptions True if taints associated with exceptions shall be tracked over try/catch construct, otherwise false
```
--noexceptions
	config.setEnableExceptionTracking(false);
```
```
	public void setEnableExceptionTracking(boolean enableExceptions) {
		this.enableExceptions = enableExceptions;
	}
```
**It is used in `infoflow.java` when doing `runanalysis`, reading the configuration while building  `cfgfactory`**
```
		iCfg = icfgFactory.buildBiDirICFG(config.getCallgraphAlgorithm(),
        		config.getEnableExceptionTracking());

```

**10.layoutmode** (√)

in *package soot.jimple.infoflow.android.InfoflowAndroidConfiguration.java*
refer to *soot.jimple.infoflow.android.source.AndroidSourceSinkManager.LayoutMatchingMode;*
Sets the mode to be used when deciding whether a UI control is a source or not @param mode
The mode to be used for classifying UI controls as sources
```
--layoutmode [NONE|PWD|ALL]
	NONE-->	config.setLayoutMatchingMode(LayoutMatchingMode.NoMatch);
	PWD-->	config.setLayoutMatchingMode(LayoutMatchingMode.MatchSensitiveOnly);
	ALL-->	config.setLayoutMatchingMode(LayoutMatchingMode.MatchAll);
	else--> "Invalid layout matching mode"
```
```
	public void setLayoutMatchingMode(LayoutMatchingMode mode) {
		this.layoutMatchingMode = mode;
	}
```

* in `setupApplication`, `calculateSourcesSinksEntrypoints(ISourceSinkDefinitionProvider sourcesAndSinks)`;
* When building `sourceSinkManager`, it creates an instance of `AccessPathBasedSourceSinkManager`;
* when initializing `AccessPathBasedSourceSinkManager`, there is `config.getLayoutMatchingMode()`

```
{
			Set<SootMethodAndClass> callbacks = new HashSet<>();
			for (Set<SootMethodAndClass> methods : this.callbackMethods.values())
				callbacks.addAll(methods);

			sourceSinkManager = new AccessPathBasedSourceSinkManager(
					this.sourceSinkProvider.getSources(),
					this.sourceSinkProvider.getSinks(),
					callbacks,
					config.getLayoutMatchingMode(),
					lfp == null ? null : lfp.getUserControlsByID());

			sourceSinkManager.setAppPackageName(this.appPackageName);
			sourceSinkManager.setResourcePackages(this.resourcePackages);
			sourceSinkManager.setEnableCallbackSources(this.config.getEnableCallbackSources());
		}
```
For more details, refer to `AccessPathBasedSourceSinkManager`, here is `LayoutMatchingMode`, belonging to  `AndroidSourceSinkManager.java`

```
	/**
	 * Possible modes for matching layout components as data flow sources
	 * 
	 * @author Steven Arzt
	 */
	public enum LayoutMatchingMode {
		/**
		 * Do not use Android layout components as sources
		 */
		NoMatch,

		/**
		 * Use all layout components as sources
		 */
		MatchAll,

		/**
		 * Only use sensitive layout components (e.g. password fields) as
		 * sources
		 */
		MatchSensitiveOnly
	}
```




**11.aliasflowins** √

Sets whether a flow sensitive aliasing algorithm shall be used 
@param flowSensitiveAliasing True if a flow sensitive aliasing algorithm shall be used, otherwise false
```
--aliasflowins
	config.setFlowSensitiveAliasing(false);
```
```
	public void setFlowSensitiveAliasing(boolean flowSensitiveAliasing) {
		this.flowSensitiveAliasing = flowSensitiveAliasing;
	}
```

**default value for the parameter is:**
```
	private boolean flowSensitiveAliasing = true;
```
**with this param, the configuration of `flowSensitiveAliasing` will be changed to ** 
```
	private boolean flowSensitiveAliasing = false;
```
which explained in `printSummary()`, that it means
```
if (!flowSensitiveAliasing)
			logger.warn("Using flow-insensitive alias tracking, results may be imprecise");
```			
			
			
**Also, you can manually change Aliasing Algo from `infoflowConfig.java` or `Test.java`**
**in `infoflowConfig.java`**
```
	/**
	 * Enumeration containing the aliasing algorithms supported by FlowDroid
	 */
	public enum AliasingAlgorithm {
		/**
		 * A fully flow-sensitive algorithm based on Andromeda
		 */
		FlowSensitive,
		/**
		 * A flow-insensitive algorithm based on Soot's point-to-sets
		 */
		PtsBased
	}
```
**and the default value is :**
```	
private AliasingAlgorithm aliasingAlgorithm = AliasingAlgorithm.FlowSensitive;
```


**12.paths** (√)
in *package soot.jimple.infoflow.android.InfoflowAndroidConfiguration.java*
Sets whether the exact paths between source and sink shall be computed. If this feature is disabled, only the source-and-sink pairs are reported. This option only applies if the selected path reconstruction algorithm supports path computations. 
@param computeResultPaths True if the exact propagation paths shall be computed, otherwise false
```
--paths
    config.setComputeResultPaths(true);
```

```
	public void setComputeResultPaths(boolean computeResultPaths) {
		this.computeResultPaths = computeResultPaths;
	}
```


When building `pathBuilder` in `SetupApplication`,
```
        Infoflow info;
		if (cfgFactory == null)
			info = new Infoflow(androidJar, forceAndroidJar, null,
					new DefaultPathBuilderFactory(config.getPathBuilder(),
							config.getComputeResultPaths()));
		else
			info = new Infoflow(androidJar, forceAndroidJar, cfgFactory,
					new DefaultPathBuilderFactory(config.getPathBuilder(),
							config.getComputeResultPaths()));
```

**The second params of `info` is `DefaultPathBuilderFactory` , here it define both `config.getPathBuilder()` and `config.getComputeResultPaths()` **
* For `config.getPathBuilder()` --> --pathAlgo (what path algorithm to use, refer to *15.pathalgo* )
* For `config.getComputeResultPaths()` --> --path / --nopath (create concrete path or not), and the default value is :
```
	private boolean computeResultPaths = true;
	// --> create concrete path
```

And the value of this config can be changed through terminal command:
The code snippet shows below are in `Test.java`:

```
            else if (args[i].equalsIgnoreCase("--paths")) {
				config.setComputeResultPaths(true);
				i++;
			}
			else if (args[i].equalsIgnoreCase("--nopaths")) {
				config.setComputeResultPaths(false);
				i++;
			}
```


**But the Value of this is also decided by what `PathAlgo` we use.**:
```
		case Recursive :
		case ContextSensitive :
		case ContextInsensitive :
			return reconstructPaths;
		case ContextInsensitiveSourceFinder :
		case None:
			return false;
		}
```
* **if use `Recursive`  or `ContextSensitive` or `ContextInsensitive`, then `reconstructPaths == --path / --nopath`**
* **But if using`ContextInsensitiveSourceFinder` or `None`, then it's always `nopath`**



**13.nopaths** (√)
opposite as **12.paths**
```
--nopaths
    config.setComputeResultPaths(false);
```


**14.aggressivetw** (√) Default value is _false_ ///BUG///
set in _Test.java_
```
--aggressivetw
	aggressiveTaintWrapper = false;
```


**15.pathalgo** (√)  --> `DS -limitContextForComplex, -limitContextForGUI -limitContextForStrings, -noClinitContext`-- combined with 12,13
Sets the algorithm to be used for reconstructing the paths between sources and sinks
@param builder The path reconstruction algorithm to be used

```
--pathalgo [CONTEXTSENSITIVE|CONTEXTINSENSITIVE|SOURCESONLY]
	CONTEXTSENSITIVE--> config.setPathBuilder(PathBuilder.ContextSensitive);
	CONTEXTINSENSITIVE--> config.setPathBuilder(PathBuilder.ContextInsensitive);
	SOURCESONLY--> config.setPathBuilder(PathBuilder.ContextInsensitiveSourceFinder); (DEFAULT)
	else --> "Invalid path reconstruction algorithm"
```
refer to *soot.jimple.infoflow.data.pathBuilders.DefaultPathBuilderFactory.PathBuilder;*
```
	public void setPathBuilder(PathBuilder builder) {
		this.pathBuilder = builder;
	}
```

**This is mainly about what `pathBuilder` to use**
here is code in `SetupApplication.java` and the method `runInfoflow`
```
		Infoflow info;
		if (cfgFactory == null)
			info = new Infoflow(androidJar, forceAndroidJar, null,
					new DefaultPathBuilderFactory(config.getPathBuilder(),
							config.getComputeResultPaths()));
		else
			info = new Infoflow(androidJar, forceAndroidJar, cfgFactory,
					new DefaultPathBuilderFactory(config.getPathBuilder(),
							config.getComputeResultPaths()));

```


**More details are in the class `DefaultPathBuilder`**



**16.summarypath** (√)  need to import [StubDroid](https://github.com/secure-software-engineering/soot-infoflow-summaries) --> used when choosing TaintWrapper 
details referring to *Test.java*
```
--summarypath arg 
    summaryPath = arg
```
[StubDroid](https://github.com/secure-software-engineering/soot-infoflow-summaries) --> an approach for automatically inferencing library summaries from the Android SDK or Java JDK



**17.saveresults**  (need more)
*Test.java*
```
--saveresults arg
    resultFilePath = arg
```
**Here are what we need to output?? called through `Test.java`**
```
		// Ouput Sources, sinks, flows --------------------------------------
		@Override
		public void onResultsAvailable(
				IInfoflowCFG cfg, InfoflowResults results) {
			// Dump the results
			if (results == null) {
				print("No results found.");
			}
			else {
				// Report the results
				for (ResultSinkInfo sink : results.getResults().keySet()) {
					print("Found a flow to sink " + sink + ", from the following sources:");
					for (ResultSourceInfo source : results.getResults().get(sink)) {
						print("\t- " + source.getSource() + " (in "
								+ cfg.getMethodOf(source.getSource()).getSignature()  + ")");
						if (source.getPath() != null)
							print("\t\ton Path " + Arrays.toString(source.getPath()));
					}
				}
				
				// Serialize the results if requested
				// Write the results into a file if requested
				if (resultFilePath != null && !resultFilePath.isEmpty()) {
					InfoflowResultsSerializer serializer = new InfoflowResultsSerializer(cfg);
					try {
						serializer.serialize(results, resultFilePath);
					} catch (FileNotFoundException ex) {
						System.err.println("Could not write data flow results to file: " + ex.getMessage());
						ex.printStackTrace();
						throw new RuntimeException(ex);
					} catch (XMLStreamException ex) {
						System.err.println("Could not write data flow results to file: " + ex.getMessage());
						ex.printStackTrace();
						throw new RuntimeException(ex);
					}
				}
			}
```



**18. sysflows**--> `DS: -native, list native methods, cut information flows (not related)` (need more), not sure yet
Sets whether flows starting or ending in system packages such as Android's support library shall be ignored.
@param ignoreFlowsInSystemPackages True if flows starting or ending in system packages shall be ignored, otherwise false.
```
--sysflows
	config.setIgnoreFlowsInSystemPackages(false);
```
```
	public void setIgnoreFlowsInSystemPackages(boolean ignoreFlowsInSystemPackages) {
		this.ignoreFlowsInSystemPackages = ignoreFlowsInSystemPackages;
	}
```


**19.notaintwrapper** (√)

in _Test.java_ , the method _runAnalysis()_.
If _noTaintWrapper_, then `taintWrapper=null` and passed to `app.setTaintWrapper(taintWrapper);`, further influencing future analysis

```
--notaintwrapper
	noTaintWrapper = true;
```



**20.repeatcount** (√)
How many times to run the same app, in _Test.java_
```
--repeatcount n
    repeatCount = n
```


**21.noarraysize** -->???? `DS: -noarrayindex(default:consider index, not tainedd the whole array)` (not sure yet)
```
--noarraysize
    config.setEnableArraySizeTainting(false);
```

```
	public void setEnableArraySizeTainting(boolean arrayLengthTainting) {
		this.enableArraySizeTainting = arrayLengthTainting;
	}
```
default value in *InfoflowConfiguration*:
```
	private boolean enableArraySizeTainting = true;
```


**22.arraysize** (not sure yet)
opposite as *21.noarraysize*
--arraysize
	config.setEnableArraySizeTainting(true);



**23.notypetightening** --> `not sure what this is` (not sure yet)
Sets whether runtime type information shall be tightened as much as possible when deriving new taints
@param useTypeTightening True if the runtime type information shall automatically be tightened when deriving new taints, otherwise false
```
--notypetightening
    InfoflowAndroidConfiguration.setUseTypeTightening(false);
```
actually, in *InfoflowConfiguration*:
```
	public static void setUseTypeTightening(boolean useTypeTightening) {
		InfoflowConfiguration.useTypeTightening = useTypeTightening;
	}

```


**24.safemode**  (not sure yet)
Sets whether access paths pointing to outer objects using this$n shall be reduced, e.g. whether we shall propagate a.data instead of a.this$0.a.data.
@param useThisChainReduction True if access paths including outer objects
shall be reduced, otherwise false
```
--safemode
    InfoflowAndroidConfiguration.setUseThisChainReduction(false);
```
actually, in *InfoflowConfiguration*:
```
	public static void setUseThisChainReduction(boolean useThisChainReduction) {
		InfoflowConfiguration.useThisChainReduction = useThisChainReduction;
	}
```



**25.logsourcesandsinks** (√)
Sets whether the discovered sources and sinks shall be logged
@param logSourcesAndSinks True if the discovered sources and sinks shall be logged, otherwise false
```
--logsourcesandsinks
	config.setLogSourcesAndSinks(true);
```

```
	public void setLogSourcesAndSinks(boolean logSourcesAndSinks) {
		this.logSourcesAndSinks = logSourcesAndSinks;
	}
```
Then, sources and sinks list will be printed in the last step of `unAnalysis(file, androidjar)`




**26.callbackanalyzer** (√) [calculateCallbackMethods.md](/calculateCallbackMethods.md)

in *package soot.jimple.infoflow.android.InfoflowAndroidConfiguration.java*
Sets the callback analyzer to be used in preparation for the taint analysis
@param callbackAnalyzer The callback analyzer to be used
```
--callbackanalyzer [DEFAULT|FAST]
	DEFAULT"-->	config.setCallbackAnalyzer(CallbackAnalyzer.Default);
	FAST-->	config.setCallbackAnalyzer(CallbackAnalyzer.Fast);
	else--> "Invalid callback analysis algorithm"
```	
```
	public void setCallbackAnalyzer(CallbackAnalyzer callbackAnalyzer) {
		this.callbackAnalyzer = callbackAnalyzer;
	}
```
Also, in`SetupApplication`
if `enabledCallBacks==True`, then match `CallbackAnalyzer`:
```
// in SetupApplication.java, _calculateSourcesSinksEntrypoints(ISourceSinkDefinitionProvider sourcesAndSinks)_ method
// getCallbackAnalyzer
				switch (config.getCallbackAnalyzer()) {
				case Fast:
					calculateCallbackMethodsFast(resParser, lfp);
					break;
				case Default:
					calculateCallbackMethods(resParser, lfp);
					break;
				default:
					throw new RuntimeException("Unknown callback analyzer");
				}
```



**27.maxthreadnum** (√)
Sets the maximum number of threads to be used by the solver. A value of -1 indicates an unlimited number of threads, i.e., there will be as many threads as there are CPU cores on the machine.
@param threadNum The maximum number of threads to be used by the solver, or -1 for an unlimited number of threads.

```
--maxthreadnum n
	config.setMaxThreadNum(Integer.valueOf(n);
```
```
	public void setMaxThreadNum(int threadNum) {
		this.maxThreadNum = threadNum;
	}
```
**Here are `infoflow.java`, when actually running analysis**
```
		// by deafult, it would use all availableProcessors
        int numThreads = Runtime.getRuntime().availableProcessors();
		CountingThreadPoolExecutor executor = createExecutor(numThreads);
```



**28.arraysizetainting** ((not sure yet))
same as 21,22

```
--arraysizetainting
    config.setEnableArraySizeTainting(true);
```

```
	public void setEnableArraySizeTainting(boolean arrayLengthTainting) {
		this.enableArraySizeTainting = arrayLengthTainting;
	}
```
default value in *InfoflowConfiguration*:
```
	private boolean enableArraySizeTainting = true;
```




#validating parameters
1. *--timeout* and *--systimeout* cannot *>0* at the same time
```
if (timeout > 0 && sysTimeout > 0) {
			return false;
}
```

2. "Flow-insensitive aliasing can only be configured for callgraph "
if *getFlowSensitiveAliasing=false* and *cgAlgo!=OnDemand*(?) and *cgAlgo!=AutomaticSelection* (*--cgalgo* *AUTO*)
```
if (!config.getFlowSensitiveAliasing()
		&& config.getCallgraphAlgorithm() != CallgraphAlgorithm.OnDemand
		&& config.getCallgraphAlgorithm() != CallgraphAlgorithm.AutomaticSelection) {
	System.err.println("Flow-insensitive aliasing can only be configured for callgraph "
			+ "algorithms that support this choice.");
	return false;
}
```
**`CallgraphAlgorithm.OnDemand` appears in `AbstractInfoflow`**
```
		// We explicitly select the packs we want to run for performance
        // reasons. Do not re-run the callgraph algorithm if the host
        // application already provides us with a CG.
		if (config.getCallgraphAlgorithm() != CallgraphAlgorithm.OnDemand
				&& !Scene.v().hasCallGraph()) {
	        PackManager.v().getPack("wjpp").apply();
	        PackManager.v().getPack("cg").apply();
		}
		
```


3. in `infoFlow.java` it also clarifies that: 
```
		// Some configuration options do not really make sense in combination
		if (config.getEnableStaticFieldTracking()
				&& InfoflowConfiguration.getAccessPathLength() == 0)
			throw new RuntimeException("Static field tracking must be disabled "
					+ "if the access path length is zero");
		if (InfoflowConfiguration.getAccessPathLength() < 0)
			throw new RuntimeException("The access path length may not be negative");

```


4. also, in `infoFlow.java`, it shows some `aliasingStrategy` is `flow-sensitive` and some not
```
		// Print our configuration
		config.printSummary();
		if (config.getFlowSensitiveAliasing() && !aliasingStrategy.isFlowSensitive())
			logger.warn("Trying to use a flow-sensitive aliasing with an "
					+ "aliasing strategy that does not support this feature");
```


