# usage: droidsafe
-analysisonlyrun                  Only run core analysis, and do not create reports or Eclipse output

-analyzestrings_unfiltered        Run string analysis with no application class filtering. (If true, string analysis is done for all soot application classes. Otherwise, it is only done for the source classes of the project.)

-apicalldepth <n>                 API Call Depth. (The depth in which to follow call graph edges from app code when building the call graph, -1 is follow all edges, 0 is don't traverse into api, 1 is traverse one edge only, ...)

-apk                              Run analysis on apk. (Specify that the analysis will be done for apk file only, no source code is provided.)

-apkfile <file>                   APK file to run the analysis on. (Provide the apk file path here.)

-appname <<str>>                  Name of application (used for reporting)

-approot <dir>                    The Android application root directory

-callgraph                        Output .dot callgraph file (droidsafe.analyses.cg.collapsedcg.CollaspedCallGraph; default: false)

-checkinfoflow                    Check infoflow result against expected-info-flows.txt (for debug-mode only? For example, DroidBench & Related file: check-info-flow.txt; default: false)

-configstr <<str>>                Additional Configuration String (used for reporting) (some additional information about configuration; default: “”; android.droidsafe.stats.PTAPaper.java)

-debug                            Enable debugging: log at ./droidsafe/droidsafe.log

-debuguser                        Enable debugging with log config file user-logback-debug.xml

-filetransforms                   Attempt to run the file precision / accuracy transformations.(Should we run the file precision transformations & Related to Soot, Jimple, to check FIS and FOS mapping to increase the precision of ICC modeling; default: false)

-help                             print this message

-ignoreexceptionflows             Ignore flows through throwable objects and their fields. (Ignore flows through throwable objects & Info. flow via exception, leakages; default: false)

-ignorenocontextflows             Ignore flows that occur in method with no context. (if true, then in the info flow analysis ignore methods with NoContext & Objective sensitive PTA; default: false)

-implicitflow                     Turn on implicit information flow analysis. (If true, analyze implicit information flows & Implicit flows have no direct flow from the source to the sink & Related file: info-iflow-results.txt; default: false)

-imprecisestrings                 turn off precision for all strings, FAST and IMPRECISE (should we not add any precision for strings and clump them all together; default: false)

-injectflows <file>               Inject inter app flows from file (if not empty, then read the file given as possible inter-application flows from other applications to be considered & need to provide a pre-generated resolved Intent values and reachable source flows)

-jimple                           Dump readable jimple files for all app classes in /droidsafe. (if true, write readable jimple output for all app classes & intermediate code; default: false)

-jsatimeout <value>               Timeout value for the string analysis in mins (default 120).(timeout value in minutes for running the string analysis & JSA String Analysis, IMPROVING THE PRECISION OF ICC MODELING. 0 means no timeout. Default is 120 (2 hours).)

-kobjsens <k>                     Depth for Object Sensitivity for PTA (depth of objective sensitive when running pta for precision (with context) & Objective sensitive PTA; default: 3)

-libpkgfile <file>                file containing library package prefixes (default: config-files/library_package_prefixes)

-limitcontextforcomplex           Limit context depth for classes that might blow up PTA (Limit context for complex classes & Related to Objective sensitive PTA analysis & PTA analysis need to be run successfully, in order to run the callfallbackmodeling, VA(value analysis), JSA injection; default: false) (/* if option limitcontextforcomplex then limit the context for this percent of the classes. Higher number will limit more context, be more scaleable but less precise.*/ private static final double PERCENTAGE_TO_LIMIT_COMPLEXITY = 0.5;)

-limitcontextforgui               Limit context depth for some GUI objects PTA (in spark limit heap context for some GUI elements & objective sensitive PTA analysis, to limit the context depth calculated for GUI elements; default: fullContextForGUI=true)

-limitcontextforstrings           Limit context depth for Strings in PTA (in spark limit heap context for strings if we are object sensitive & objective sensitive PTA analysis, to limit the context depth calculated for Strings; default: fullContextForStrings=true)

-manualmod                        Use small set of manual Android API modeling for fast run.(if true, use the small manual set of android classes for the api model, for a fast run & decide to use droidsafe-manual-api-model.jar or droidsafe-api-model.jar; default: false)

-multipassfb                      Enable multiple passes of fallback modeling. (should we run multiple passes of fallback modeling create unmodeled objects from API; default: false)

-native                           List native methods that cut information flows. (If true, list native methods that cut information flows & Droidsafe has a blanket flow policy for native methods of an Android application, but an application could inject a sensitive flow in a native method, and Droidsafe would not report it. Hence, it could be useful if Droidsafe can list all native methods that cut info. flows, and then human analysts can inspect relevant native methods if they suspect that there is any info. leakage; default: false)

-noarrayindex                     Do not transfer taints that flow into array indices. (If true, do not transfer taints that flow into array indices; default: false)

-noclinitcontext                  PTA will not add special context for static inits. (should a context sensitive pta add context to static inits? default: true)

-noclonestatics                   Do not clone static methods to add call site sensitivity. (should we clone static methods to add call site sensitivity for them? default: true)

-nofallback                       Disable Fallback Modeling (should we call unreachable callbacks, and insert dummy objects for unmodeled api calls? default: true)

-noinfoflow                       Turn off information flow analysis (if has option, then set infoFlow=false, checkInfoFlow=false; option -infoFlow: If true, analyze information flows.)

-nojsa                            Do not use JSA. (If has nojsa, then set runStringAnalysis=false. If runStringAnalysis=true, run string analysis on app classes. runStringAnalysis is true by default)

-noptaresult                      Do not translate PTA result for eclipse plugin. (If has noptaresult, then set ptaresult=false. If ptaresults=true, then generate pta result for eclipse plugin; default(ptaresult): true)

-norcfg                           Do not organize results by triggering event.  Does not produce eclipse files. (spec.html) (If has no rcfg, then set createRCFG=false. By default, createRCFG=true.)

-noreports                        Do not generate indicator reports. (If has foreparts, then set produceReports=false. If produceReports=true, then Produce indicator reports (the CollaspedCallGraph, AllEntryPointCallTree, IPCEntryPointCallTree, ICCEntryPointCallTree, ICCMap, UnresolvedICC, SensitiveSources, SourceCallTree, ObjectMethodOverrideContracts, CatchBlocks); default (produceReports): true)

-noscalaropts                     Do not run scalar opts like copy / constant prop and folding (If has noscalaropts, then set scalarOpts=false; by default, scalarOpts=true, if true, then run scalar opts like copy / constant prop and folding. Will not run for “errorhandling” mode.)

-nosourceinfo                     Do not print source information in spec (Don't include source location information when outputting spec; default: false; Main.java does not check this configuration. Need to figure out where is this config checked.)

-nova                             Do not run value analysis.(If has nova, then set runValueAnalysis=false. If runValueAnalysis=true, then run value analysis; default (runValueAnalysis): true; Main.java does not check this configuration. VA: a flow and context insensitive analysis responsible for conservatively keeping track of field values for select object types (currently URIs & Intents);va-results.log&va-errors.log)

-p,--precision <INT>              Run with precision level: 0 - 5, increasing precision.

-preciseinfoflow                  For info flow reporting use memory access analysis for args to sinks. (use precise reporting for info flow results of args to sinks; default: false; not very clear which report it generates.)

-pta <package>                    Run with pta package: spark, paddle, geo (default: POINTS_TO_ANALYSIS_PACKAGE=spark)

-ptadump                          Dump pta to ./droidsafe/pta.txt (If has ptadump, then set dumpPta=true. if dumpPta=true, then dump pta to a file; default: dumpPta=false)

-reportonlyargsflows              For infoflow reporting, report only flows through args and receiver of sinks. (If has reportonlyargsflows, then set reportOnlyArgFlows=true. If true, when querying the info flow through Method.java, only report flows through args and receiver; default (reportOnlyArgFlows): false)

-reportunmodeledflows             Report on flows from objects introduced by fallback modeling. (report on unmodeled taint & fallback, create fake methods for unmodeled methods & android.droidsafe.transforms.CallBackModeling.java —> findDeadCallbackAndCreateFallbackMethod(); default: false)

-s <ssl_file>                     Security Specification for application (used with confcheck)(Droidsafe currently does not implement the mode confcheck. Pass.)

-stats                            Perform extra work to generate stats.(If has stats, then set statsRun=true. If statsRun=true, for various passes do extra work to print stats, tied to --stats; default: false)

-strict                           Strict mode: die on errors and assertions.

-t,--target <target>              Target pass to run (spec dump, errorhandling)

-trackallflows                    Track all methods (excluding those in java.lang) during information flow analysis. (If has trackallflows, then set infoFlowTrackAll=true. If true, track all methods (excluding those in java.lang) regardless of APIInfoKindMapping.hasSourceInfoKind(); default (infoFlowTrackAll): false; android.droidsafe.android.system.AutomatedSourceTagging.java)

-transfertaintfield               Always transfer y.taint into x.taint (or x) for x = y.m(). (If has transfertaintfield, then set infoFlowTransferTaintField=true; default: false)

-typesforcontext                  use types (instead of alloc sites: Cxt,Loc(line no. maybe)) for object sensitive context elements > 1 (default: false)

-vastats                          Dump VA stats. (If has vastats, then set dumpVAStats=true. If true, compute value analysis result stats; default(dumpVAStats): false)

-writeinterappflows               Produce json file for possible inter app sinks in this app. (If has writeinterappflows, then set produceInterAppFlowsFile=true. If true, then then produce a json file that describes the possible inter-applications sinks; default(produceInterAppFlowsFile): false)

-x,--infoflow-value <INFOVALUE>   Print contexts and local variables that have INFOVALUE. Use "\$$r1 = ..." if line has $. (Provide some InfoValues that we are concerned with.)