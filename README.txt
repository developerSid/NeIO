NeIO (pronounced Neo like the guy from 'The Matrix') is a library for doing IO in Java

It is for the most part just a thin wrapper around Java's already expansive IO library, but
done in a way that makes the code you create with it more testable.

This library really grew out of my need for some simple project to work on that I could call my own.
Originally the IO code began life as a simple NIO Socket implementation for doing network communication
between a server and multiple clients with lifetime connections to the server.  While I knew of JBoss
Netty and Apache Mina I could never get them to do what I wanted which was allow me to send arbitrary
bytes across a wire in a simple fashion.  It probably comes down to my utter stupidity, but I never
liked their implementation so with much ridicule from friends and colleagues I created my own that in
my mine presented the interface in a much cleaner fashion, or at least not abstracting details out as
far in my opinion.

The rest of the library grew out of the need to do message staging as messages are sent and recieved
between server and client.

There is an IO staging system that I tried to make as simple as possible.  I don't know how useful something
like this would be in your average project that is doing IO, but it exists so you should know about it.

The IO staging spawned a new abstraction of the lower level java.io.File system that is interface based
instead of class based.

The File system abstraction spawned a new Stream system since the java.io.* streams would no longer
work easily with the File system abstraction.