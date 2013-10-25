#!/bin/bash
#Compile Server
javac remote/*.java
rmic remote.RemoteServer
jar cfe server.jar remote.StartRemoteObject remote/*.class

#Compile client
jar cfe client.jar remote.StartRemoteObject local/*.class remote/IfaceRemoteServer.class
