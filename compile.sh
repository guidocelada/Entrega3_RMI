#!/bin/bash
#Compile Server
rmic remote.RemoteServer
javac remote/*.java
jar cfe server.jar remote.StartRemoteObject remote/*.class

#Compile client
javac local/*.java
jar cfe client.jar local.AskRemote local/*.class remote/IfaceRemoteServer.class
