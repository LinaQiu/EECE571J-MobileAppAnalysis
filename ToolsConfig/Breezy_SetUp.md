# 1. BASIC INFORMATION
#### 1. Breezy server basic information
* Server Name: `breezy.westgrid.ca`
* [Main Page](https://www.westgrid.ca/support/systems/Breezy/system_status)
* [QuickStart Guide](https://www.westgrid.ca/support/quickstart/new_users#software)

#### 2. Connect
```
ssh username@breezy.westgrid.ca

# for example, 
ssh yingying@breezy.westgrid.ca
```

#### 3. Modules
* Some software exists on the server in the format of `Module`, which needs to be loaded before use
* [Module Manual](https://docs.computecanada.ca/wiki/Utiliser_des_modules/en)
* Check the list of all available modules: 
  `module avail`
* Load module 
  `module load $module_name`
* __*Need to load modules for every ssh session*__


#### 4. Running jobs 
* remember to load modules in .pbs script before running tasks we need 
necessary module is `java`
```
module load java
```
* [Running Jobs](https://www.westgrid.ca/support/running_jobs)
* [Breezy Quickstart Guide](https://www.westgrid.ca/support/quickstart/breezy) 
* command like: 
```
qsub -l nodes=1:ppn=8,mem=64gb,walltime=72:00:00 mpi_diffuse.pbs

# or specifying resources we need in the .pbs script 

#PBS -l nodes=4:ppn=8,mem=64gb,walltime=72:00:00 

# then, run the script
qsub mpi_diffuse.pbs
```
* Breezy Specific requirements:
> The maximum walltime limit for Breezy jobs is 3 days.

> For a single user, the maximum number of jobs in the system at a time is 1000.

> Since Breezy is intended for applications requiring large amounts of memory, one will often be expected to specify a TORQUE mem parameter on the qsub command line (or in #PBS directives in the batch job script). Although the memory per node is nominally 256 GB, there is not quite that much available. Do not specify more than 250 GB for the mem or pmem resource requests or your job will get stuck in input queue waiting for memory that will never be available.

> Another intended use for Breezy is for multi-threaded single-node applications. For such cases, use a resource request of the form -l nodes=1:ppn=24,mem=250gb, where ppn, the processors per node, is the number of cores required and the memory needed is specified with the mem parameter. Since Breezy compute nodes have 24 cores, that is maximum number you can specify for ppn. You can use smaller values for ppn and mem as appropriate for your calculation. However, if you are using less than 24 cores, it is important to limit the number of threads used by your application to the number of cores requested, so as not to interfere with other users' jobs, which may be assigned to the same node. Often this can be accomplished by setting the OMP_NUM_THREADS variable. See the example script in the OpenMP section of Running Jobs page for an example.

> Please do not use the ncpus or procs parameters when requesting processors on Breezy. In the rare cases in which multiple nodes are used for a single job, use the -l nodes=n:ppn=24 format to request multiple nodes, where n is the number required.

* sample scripts: 
  [pbs_sample.pbs](https://github.com/LinaQiu/EECE571J-MobileAppAnalysis/blob/master/pbs_sample.pbs)

# 2. DROIDSAFE INSTALLATION [Manual](https://github.com/MIT-PAC/droidsafe-src/wiki/Getting-Started)
### 1. Dependencies
#### 1.1 Git
* built in, could be accessed directly
#### 1.2 Ant [ref](https://howtoprogram.xyz/2016/10/14/install-apache-ant-ubuntu-16-04-lts-xenial-xerus/)
* download `1.10.1 .tar.gz`: [link](http://ant.apache.org/bindownload.cgi)
* create a folder `tools` on Breezy for all dependencies
  ```
  mkdir -p ~/tools
  ```
* transfer to server 
  ```
  scp ~/Downloads/apache-ant-1.10.1-bin.tar yingying@breezy.westgrid.ca:~/tools
  ```
* extract the file
  ```
  tar -xf apache-ant-1.9.7-bin.tar.gz  -C ~/tools/
  ```
* create symbolic link for ant folder
  ```
  ln -s /home/yingying/tools/apache-ant-1.10.1 /home/yingying/tools/ant
  ```
* edit environemnt variables
  ```
  vi ~/.bash_profile
  
  # add environemnt variables
  export ANT_HOME=/home/yingying/tools/ant
  export PATH=${ANT_HOME}/bin:${PATH}
  
  # finish editing, source the file
  source ~/.bash_profile
  ```
* verfiy ant
  ```
  ant -version
  ```
#### 1.3 Python 2.6+
* built in, no need to install

#### 1.4 Java 1.7
* can be loaded as module
* check all available versions of java
  ```
  module avail
  ```
* install java 1.8
  ```
  module load java/1.8.0_45
  ```
* verify java version
  ```
  java -version
  ```
#### 1.5 Android SDK 19 
* scp from existing file
  ```
  scp ~/Downloads/android-sdks.zip yingying@breezy.westgrid.ca:~/.
  ```
* unzip the sdk
  ```
  unzip ~/android-sdks.zip
  ```
### 2. Install DroidSafe Analyzer
#### 2.1 config environemnt variables
```
vim ~/.bash_profile

export ANDROID_SDK_HOME=/home/yingying/android-sdks #upper level of /sources/ folder
export DROIDSAFE_SRC_HOME=/home/yingying/DroidSafe/droidsafe-src  #the root directory of the local Droidsafe repository
export DROIDSAFE_MEMORY=64


# source that file
source ~/.bash_profile
```
#### 2.2 Install DroidSafe
```
cd $DROIDSAFE_SRC_HOME
ant compile
# if met error, try:
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
# then ant compile again
```


# 3. FLOWDROID SETTING UP
#### 1 Make a folder on your local for all files
Here we use FlowDroid1.5ReleaseVersion, so name it like:
`mkdir FlowDroid1.5ReleaseVersion`

#### 2 Download all needed files from FlowDroid Github [Link](https://github.com/secure-software-engineering/soot-infoflow-android/wiki)
Here we use the FlowDroid1.5ReleaseVersion.
##### List of needed files:
*6 Jar Files:*
* axml-2.0.jar
* slf4j-api-1.7.5.jar
* slf4j-simple-1.7.5.jar
* soot-infoflow-android.jar
* soot-infoflow.jar
* soot-trunk.jar
*3 txt files:*
* AndroidCallbacks.txt
* EasyTaintWrapperSource.txt
* SourcesAndSinks.txt

#### 3 Scp all above files to Breezy Server
```
# first, make a folder on Breezy for FlowDroid
mkdir ~/FlowDroid

# then, scp your Flowdroid 1.5 release version folder from local to Breezy
scp -r ~/Downloads/FlowDroid1.5ReleaseVersion yingying@breezy.westgrid.ca:~/FlowDroid/
```
Done.



# 4. IccTA SETTING UP
### 1. Setting Up Dependencies
#### 1.1 Mysql
##### Mysql dependences: [ref](https://dev.mysql.com/doc/refman/5.7/en/source-installation.html)
* cmake: [download](https://cmake.org/download/)
```
module load cmake
```
* boost
```
module load boost
```
##### install Mysql from source: [REF](https://dev.mysql.com/doc/refman/5.7/en/installing-development-tree.html)
* git clone 
```
git clone https://github.com/mysql/mysql-server.git

```
* create a folder for mysql installation
```
mkdir -p /home/yingying/tools/mysql-5.7-personalBuilt
```
* configure via cmake
```
cd mysql-server
cmake -DCMAKE_INSTALL_PREFIX=/home/yingying/tools/mysql-5.7-personalBuilt -DDOWNLOAD_BOOST=1 -DWITH_BOOST=/home/yingying/tools/

```
* make and make install
```
make 
make install DESTDIR="/home/yingying/tools/mysql-5.7-personalBuilt"
# met error here, need to solve later
```


#### 1.2 install IccTA
```
mkdir ~/IccTA
cd ~/IccTA
git clone https://github.com/lilicoding/soot-infoflow-android-iccta
```

#### 1.3 config IC3/ EPIC



#### 1.4 config IccTA release





# 5. AmanDroid SETTING UP
```
mkdir ~/AmanDroid
cd ~/AmanDroid
git clone https://github.com/arguslab/Argus-SAF
```
Done 
