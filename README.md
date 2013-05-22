TCP Client-Server Application in Java
=====================================

This repository consists of two small Java applications, a server and a client. I built these applications quite a long time ago but have decided to rebuild them with better coding practices.

Server
------

The server is a TCP-based application that binds to port 14500 by default. It loops indefinitely and accepts connection requests from clients and displays the details to the console. It also sends the current timestamp to the client.

Client
------

The client is also a TCP-based application that attempts to connect to a TCP server on port 14500 by default on a user-specified host. To start the application, enter the following on the command line from the root directory of the downloaded repository:
```
java bin/Client localhost
```