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
