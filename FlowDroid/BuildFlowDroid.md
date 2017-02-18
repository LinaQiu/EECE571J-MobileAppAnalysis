Here are two ways to build FlowDroid:
1. Build from source
2. Using the nightly build version

Each way corresponds to one folder. Here are instructions for each way:

1. Build from source
dependencies you need:
Jasmin: https://github.com/Sable/jasmin
Heros: https://github.com/Sable/heros
Soot: https://github.com/Sable/soot
soot-infoflow: https://github.com/secure-software-engineering/soot-infoflow
soot-infoflow-android: https://github.com/secure-software-engineering/soot-infoflow-android

import all five projects(Jasmin, Heros, Soot, soot-infoflow, soot-infoflow-android) into Eclipse. 
If meet errors, go to Preferences->Java->Compiler->Complier compliance level, change it to the highest one (for example, 1.8)

After importing all projects, you need to:
1. copy the text "EasyTaintWrapperSource" to the main folder of the project "soot-infoflow-android"
2. set the environment variable ANDROID_JARS (your android sdk platform location); if you cannot set the environment variable, then go to "soot-infoflow-android" --> test--> soot.jimple.infoflow.android.test.otherAPKs-->JUnitTests.java ;
modify line 56 from "String androidJars = System.getenv("ANDROID_JARS");" to "String androidJars = "[your_sdk_platform_location];"
comment out the following two if conditionals
3. run APK files with JUnit method in "soot-infoflow-android" --> test--> soot.jimple.infoflow.android.test.otherAPKs-->OtherAPKTests.java 

TIP: the main method lies in  "soot-infoflow-android" --> src --> soot.jimple.infoflow.android.TestApps --> Test.java


2. Use the nightly build version
Put all jar files together in the same folder, done.

