
//A network server that listens to port 2009.

// Sean Warren - Talk Server 3/1/2014

import java.io.*;
import java.net.*;

public class TalkServer
{
	public static void main(String[] args) throws Exception 
	{
		int port = 2009;

		 NetworkServer_1 nwServer = new NetworkServer_1(port);

		nwServer.listen();
	}
}

class NetworkServer_1
{
	protected int port;

  /** Build a server on specified port. It will continue to accept connections
      until an explicit exit command is sent.
  */
	public NetworkServer_1(int port)
	{
		this.port = port;
	}

  /** Monitor a port for connections. Each time one is established, pass resulting Socket to
      handleConnection.
  */
	public void listen()
	{

		try
		{
			 ServerSocket listener = new ServerSocket(port); //resource link says it will never closes, but will close in the client thread
			 
			 Socket server;
             System.out.println(" TalkServer is up and running\n");
			 while(true)
			 {
				 server = listener.accept(); //accept connection request from a client
				 new Thread(new clientthread(server)).start(); //handleConnection(server); instead of handling the connection this function creates a newthread based on the handleconnection class but goes throught the client thread class
			 }

		}
		catch (IOException ioe){ System.out.println("IOException: " + ioe); }

	}
}
  /**
      This is the method that provides the behavior to the server
  */

    
