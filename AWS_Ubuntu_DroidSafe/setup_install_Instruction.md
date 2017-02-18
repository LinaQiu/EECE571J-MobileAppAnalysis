## How to set up and connect to Amazon EC2 Ubuntu server:
1. Launch instance from [Amazon Web Service](https://ca-central-1.console.aws.amazon.com/ec2/v2/home?region=ca-central-1#LaunchInstanceWizard).
  - Configure hardware (memory, cpu, etc.) according to your task’s requirements.
2. Modify the Security Group rules, to enable HTTP, HTTPS, SSH inbound connection
3. Connect to your EC2 Linux instance using SSH. ([Reference](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AccessingInstancesLinux.html))
```
- chmod 400 /path/my-key-pair.pem
- ssh -i /path/my-key-pair.pem ubuntu@Public DNS
```

## How to install Java, Python3, Ant in the AMS EC2 instance:
1. Update packages: `sudo apt-get update`
2. To check operating system architecture: `uname -i`
3. Install Java jdk: `sudo apt install openjdk-8-jre-headless` `sudo apt-get install openjdk-8-jdk` (openjdk is required to run ant)
4. Install Python3: `sudo apt install python3`
5. Install Ant: `sudo apt install ant`
6. How to copy file from local machine to remote server securely: `scp -i /path/my-key-pair.pem /path/fileName ubuntu@Public DNS:~`

## How to retrieve the Ubuntu GUI for an Amazon EC2 instance: ([Reference](http://stackoverflow.com/questions/25657596/how-to-set-up-gui-on-amazon-ec2-ubuntu-server))
1.Add rule to enable TCP connection to port 5901 to the Security Group

2.Create new user with password login:
```
sudo useradd -m awsgui
sudo passwd awsgui
sudo usermod -aG admin awsgui

sudo vim /etc/ssh/sshd_config # edit line "PasswordAuthentication" to yes

sudo /etc/init.d/ssh restart
```
3.Setting up UI based Ubuntu machine on AWS:
```
sudo apt-get update
sudo apt-get install ubuntu-desktop
sudo apt-get install vnc4server                                          
```   
4.Run the following commands and enter the login password for VNC connection:
```
su - awsgui
vncserver
vncserver -kill :1
vim /home/awsgui/.vnc/xstartup 
```                                          
5.Comment all original file content. Copy paste the following text to the file: (press “i” to insert)
```
#!/bin/sh

# Uncomment the following two lines for normal desktop:
# unset SESSION_MANAGER
# exec /etc/X11/xinit/xinitrc

[ -x /etc/vnc/xstartup ] && exec /etc/vnc/xstartup
[ -r $HOME/.Xresources ] && xrdb $HOME/.Xresources
xsetroot -solid grey 
vncconfig -iconic &
x-terminal-emulator -geometry 80x24+10+10 -ls -title "$VNCDESKTOP Desktop" &
x-window-manager &
        
gnome-panel &
gnome-settings-daemon &
metacity &
nautilus &
```
When you are done, press “esc” first, and then type “:wq” to save all changes.

6.Then start VNC server again. 
```
vncserver
```
7.Mac VNC client can be downloaded from [here](http://www.realvnc.com/download/get/1286/). 

8.Install VNC client on your MAC, launch it. Then search for address `Public DNS:#` (# refers to the number of the started vncserver)

## How to install Eclipse in Ubuntu:
1. Open terminal, type firefox to open the browser.
2. Search for `Eclipse`, and then download the zip file, uncompress.
3. Launch Eclipse, click `Help —> Install new software —> Add —> ADT Plugin+https://dl-ssl.google.com/android/eclipse/`
4. We recommend that you change the memory requirements in the Eclipse initialization file which is
  - `<Eclipse installation directory>/eclipse.ini` on Linux, to the following:
```
-Xms1g
-Xmx16g
```
## How to install Android SDK Tools 25 in Ubuntu:
1. Search for https://developer.android.com/studio/index.html, navigate to the bottom of the page, and find the sdk tools only download section. Download the latest Android SDK Tools 25, and save the zip file locally. 
2. Open terminal, navigate to the path where you stored the downloaded sdk tools zip file, unzip it by using command `unzip SOURCE DEST`.
3. Add path of `tools/android` by adding the following line to `~/.bashrc` file. (Edit `~/.bashrc` by following: `vi ~/.bashrc` -> press i -> copy paste the text -> press esc, type :wq)

## Download Android SDK Tools 19 using the `sdkmanager`:
1. Run in root: `sudo -s`
2. List all android sdk: `android list sdk --all`
3. Find the number for Android SDK Tools 19
4. Download the sdk tools using: `android update -u -a -t #` (# refers to the no. of items that we want to download)
5. Congratulations! You have downloaded the Android SDK Tools 19 successfully! 

## So far, we have set up all required software dependencies for running DroidSafe successfully.

## Install DroidSafe
### Install the DroidSafe Analyzer
1.Clone the Droidsafe repository from GitHut to local directory.
```
git clone <Droidsafe clone URL>
```

2.Set the environment variable
  - `DROIDSAFE_SRC_HOME` - the root directory of the local Droidsafe repository.
```
vim ~/.bashrc

export PATH=${PATH}:${DROIDSAFE_SRC_HOME}
```
3.Build the Droidsafe static analyzer by execute the following ant command from the Droidsafe installation directory.
```
ant compile
```

### Install the Droidsafe Eclipse Plugin
1.Eclipse should be closed at this point.
2.Edit the file **build.properties**. (It can be found under the Droidsafe installation directory/src/eclipse/build.properties) Change the value for target.eclipse.platform by following the instruction below:
```
target.eclipse.platform=<root directory of your eclipse installation>
```
3.Build and install the Droidsafe plugin using the shell commands: (you need to navigate to the Droidsafe installation directory first)
```
cd $DROIDSAFE_SRC_HOME
ant plugin-deploy
```

If ant succeeds, a new eclipse plugin has been installed in the **plugins** directory under the eclipse installation directory.
