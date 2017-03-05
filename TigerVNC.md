 ## This doc is about how to run TigerVNC on Mac
 
# Preparation on Amazon AWS
https://www.digitalocean.com/community/tutorials/how-to-install-and-configure-vnc-on-ubuntu-16-04

# Run TigerVNC on Mac OS
1. download TigerVNC Java Viewer JAR 
 http://www.tightvnc.com/download.php
 
 2. Make sure you have java installed. It requires Java SE version 1.6 or later.
 
 3. upzip your jar file, then you are able to use tigervnc java viewer
 
 4. statr vncserver on Amazon AWS
  ```
  su - DroidsafeAWSGUI(or other username)
  vncserver
  ```
  remember the number of X, then your port=5900+X
  
 5. on your local machine
 ```
 cd $JAR_LOC
 java -jar tightvnc-jviewer.jar -port=590X ec2-52-36-247-225.us-west-2.compute.amazonaws.com
 ```
 Then connect.
