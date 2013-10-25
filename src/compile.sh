#!/bin/bash

#Compile Server
rmic remote.RemoteServer
javac remote/*.java
jar cfe server.jar remote.StartRemoteObject remote/*.class

#Compile client
javac local/*.java
jar cfe client.jar local.AskRemote local/*.class remote/IfaceRemoteServer.class remote/RemoteServer_Stub.class

#Compile MultiThreadTest
javac thread_test/MultiThreadClientTest.java
jar cfe thread_test.jar thread_test.MultiThreadClientTest thread_test/*.class remote/IfaceRemoteServer.class remote/RemoteServer_Stub.class
