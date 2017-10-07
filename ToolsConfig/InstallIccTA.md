# This doc is about how to install IccTA

## Here are several related manuals:
* [IccTA](https://github.com/lilicoding/soot-infoflow-android-iccta)
* [IC3](https://github.com/siis/ic3)
* [ApkCombiner](https://github.com/lilicoding/ApkCombiner)

## Download IccTA
```
git clone https://github.com/lilicoding/soot-infoflow-android-iccta.git
```
and [dependencies](https://github.com/lilicoding/soot-infoflow-android-iccta/wiki/Usage-of-IccTA) (if you want to build yourself)

## Install Database
* [install mysql and config user](/IccTAManual.md)
* More,[mysql installation tutorial](https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-14-04)

## Config Database
* create database 
```shell
mysql -u root -p root -e 'create database cc';
```
* import schemas
```
cd $IccTA_PATH
mysql -u root -p root cc < res/schema;
```

* check database status
```
mysql> use cc;
mysql> show tables;
```

## Use IC3
```
cd $IccTA_PATH/release
vi runIC3.sh  # modify db information etc. 

# edit all shell script here for correct info

# then, run IC3
./runIC3.sh $android_jar $path_of_apk
```

## Config Properties

* all scripts
```
dir: $IccTA_PATH/release &  $IccTA_PATH/release/res
```

