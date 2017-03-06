# This doc is about how to run IccTA
github link of IccTA: https://github.com/lilicoding/soot-infoflow-android-iccta.git 
 
# install mysql
link : https://dev.mysql.com/downloads/mysql/
After installation, add $PATH

  ```
vi ~/.bash_profile
export PATH=/usr/local/mysql/bin/:${PATH}

. ~/.bash_profile
  ```
Then, you should be able to run mysql
  
note down the password for the user root@localhost. Login to mysql as root user
```
mysql -h localhost -u root -p
```
Then, wait for the prompt and input your password.
Until here, you are able to log in to mysql 
Then, reset password for root before log out
```
ALTER USER root@localhost IDENTIFIED BY 'MyNewPassword';
```
Then, you are good to log out.

# run IccTA
1. copy all dependences: (refer to https://github.com/lilicoding/soot-infoflow-android-iccta/wiki/Usage-of-IccTA) 
- Jasmin: https://github.com/Sable/jasmin
- Heros: https://github.com/Sable/heros
- Soot: https://github.com/Sable/soot
- soot-infoflow: https://github.com/lilicoding/soot-infoflow.git
- soot-infoflow-android: https://github.com/lilicoding/soot-infoflow-android.git
- soot-infoflow-android-iccta: https://github.com/lilicoding/soot-infoflow-android-iccta.git
- Epicc: included in the IccTA project under the directory of soot-infoflow-android-iccta/iccProvider/epicc

2. import all projects above into eclipse (refer to https://github.com/lilicoding/soot-infoflow-android-iccta/wiki/Usage-of-IccTA) 
3. explicitly build paths for IccTA (refer to https://github.com/lilicoding/soot-infoflow-android-iccta/wiki/Usage-of-IccTA) 
4. create database for IccTA  (refer to https://github.com/lilicoding/soot-infoflow-android-iccta/wiki/Usage-of-IccTA) 
```
mysql -u root -p -e 'create database db_name;'
mysql -u root -p db_name < res/schema;
```

5. After importing schemas, you can login to your mysql database to check all your tables;
```
> mysql -u root -p
mysql> use db_name;
mysql> show tables; # you will get the list of all tables in your database 'db_name'
```
# Customize configurations for Epicc
1. modify iccProvider/epicc/runEpicc.sh, customise the database's HOST, NAME, USERNAME and PASSWORD as well as the ANDROID_JARS;
```
cd $ICCTA_DIR
vi iccProvider/epicc/runEpicc.sh

# modify
HOST
NAME
USERNAME
PASSWORD
ANDROID_JARS
```

2.Create a directory called "output_iccta" in your working directory. Otherwise, you will get a exception: java.io.FileNotFoundException: output_iccta/[filename].csv (No such file or directory), however, this will not influence the analysis results of Epicc.

3. (mentioned by IccTA)"I strongly recommend you to use IC3 to replace Epicc in your experiments."

# Customize IccTA
1. in res/jdbc.xml, where you need to customise the information of your database.
```
cd $ICCTA_DIR
vi res/jdbc.xml

```
for example, edit it to:
```
<databases>
        <database>
                <name>IccTA</name>
                <driver>com.mysql.jdbc.Driver</driver>
                <url>jdbc:mysql://127.0.0.1:3306/cc</url>
                
                
                <!--<url>jdbc:mysql://localhost/IccTA</url>-->
                <!--<url>jdbc:mysql://localhost/IccTA</url>-->
                <!--<url>jdbc:mysql://localhost/IccTA</url> -->
                <username>root</username>
                <password>root</password>
                <charset>N/A</charset>   <!-- currently not used -->
        </database>
</databases>
```
* queries might be useful:
```
mysql> select user();
mysql> show variables;
mysql> show variables where variable_name = 'port' # or any other variables
```

# Launch IccTA
```
# First, launching Epicc to build ICC links for an Android application.
./runEpicc.sh $path_of_apk

# Then, running IccTA for the above application. 
# Note that IccTA is based on the ICC links extracted by Epicc to perform inter-component analysis.
# The entry-point (main method) of IccTA is implemented in class soot.jimple.infoflow.android.iccta.TestApps.Test
java -jar IccTA.jar ~/github/DroidBench/apk/InterCompCommunication_startActivity1/InterCompCommunication_startActivity1.apk  ~/github/android-platforms
```
(referring to https://github.com/lilicoding/soot-infoflow-android-iccta/wiki/Usage-of-IccTA)
The more details are listed below. Usage: java -jar IccTA.jar path_to_apk_file path_to_android_jar [all_params_of_FlowDroid]

-aliasflowins This option makes the alias search flow-insensitive and may generate more false positives, but on the other hand can greatly reduce runtime for large applications
-androidJars Spedivy the android jars, e.g., the dir of android-platforms usually used by Soot
-apkPath Specify the apk path that you want to analyze
-aplength Sets the maximum access path length to n. The default is 5. In general, larger values make the analysis more precise, but also more expensive
-enableDB Put the result to db
-help Print this message
-iccProvider Specify the icc provider, default is Epicc
-intentMatchLevel Specify the intent match level: 0 means only explicit Intents, 1 means 0+action/categories, 2 means 1+mimetype and the default 3 means everything;
-nocallbacks Disables the emulation of Android callbacks (button clicks, GPS location changes, etc.) This option reduces the runtime, but may miss some leaks
-nopaths Just shows which sources are connected to which sinks, but does not reconstruct exact propagation paths. Note that this option does not affect precision. It just disables the additional path processing
-nostatic Disables tracking static fields. Makes the analysis faster, but may also miss some leaks
-pathalgo Specifies the path reconstruction algorithm to be used. There are the following possibilities: 1) "sourcesonly" just shows which sources are connected to which sinks, but does not reconstruct exact propagation paths. This path algorithm is context-insensitive by construction, but also the fastest algorithm. 2) "contextinsensitive" shows the complete propagation path from source to sink and is context-insensitive. 3) "contextsensitive" shows the complete propagation path from source to sink and is fully context-sensitive. It is the most precise, but also the slowest and most memory-demanding algorithm.
