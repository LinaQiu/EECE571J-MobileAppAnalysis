## How to set up Amazon EC2 Ubuntu server, with Java, Python3, Any installed:
1. Launch instance from [Amazon Web Service](https://ca-central-1.console.aws.amazon.com/ec2/v2/home?region=ca-central-1#LaunchInstanceWizard).
  - Configure hardware (memory, cpu, etc.) according to your task’s requirements.
2. Modify the Security Group rules, to enable HTTP, HTTPS, SSH inbound connection
3. Connect to your EC2 Linux instance using SSH. ([Reference](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AccessingInstancesLinux.html))
```
- chmod 400 /path/my-key-pair.pem
- ssh -i /path/my-key-pair.pem ubuntu@Public DNS
```
4. Update packages: `sudo apt-get update`
5. To check operating system architecture: `uname -i`
6. Install Java jdk: `sudo apt install openjdk-8-jre-headless`
7. Install Python3: `sudo apt install python3`
8. Install Ant: `sudo apt install ant`
9. How to copy file from local machine to remote server securely: `scp -i /path/my-key-pair.pem /path/fileName ubuntu@Public DNS:~`

## How to retrieve the Ubuntu GUI for an Amazon EC2 instance: ([Reference](http://stackoverflow.com/questions/25657596/how-to-set-up-gui-on-amazon-ec2-ubuntu-server))
1. Add rule to enable TCP connection to port 5901 to the Security Group
2. Create new user with password login:
```
sudo useradd -m awsgui
sudo passwd awsgui
sudo usermod -aG admin awsgui

sudo vim /etc/ssh/sshd_config # edit line "PasswordAuthentication" to yes

sudo /etc/init.d/ssh restart
```
3. Setting up UI based Ubuntu machine on AWS:
```
sudo apt-get update
sudo apt-get install ubuntu-desktop
sudo apt-get install vnc4server                                          
```   
4. Run the following commands and enter the login password for VNC connection:
```
su - awsgui
vncserver
vncserver -kill :1
vim /home/awsgui/.vnc/xstartup 
```                                          
5. Comment all original file content. Copy paste the following text to the file: (press “i” to insert)
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
6. Then start VNC server again. 
```
vncserver
```
7. Mac VNC client can be downloaded from [here](http://www.realvnc.com/download/get/1286/). 
8. Install VNC client on your MAC, launch it. Then search for address `Public DNS:#` (# refers to the number of the started vncserver)

## How to install Eclipse in Ubuntu:
1. Open terminal, type firefox to open the browser.
2. Search for `Eclipse`, and then download the zip file, uncompress…
3. Launch Eclipse, click `Help —> Install new software —> Add —> ADT Plugin+https://dl-ssl.google.com/android/eclipse/`
