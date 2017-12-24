package CPS_Server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import CPS_Utilities.CPS_Tracer;
import clientServerCPS.ClientServerConsts;

public class ServerController
{
    private ServerRequestHandler myRequestHandler;
    
    public ServerController() throws Exception
    {
	myRequestHandler = new ServerRequestHandler();
    }
    
    private void ListenAndResponse() throws IOException
    {	
	try (ServerSocket serverSocket = new ServerSocket(ClientServerConsts.PORT))
	{
	    CPS_Tracer.TraceInformation("Server has started to listen on IP "
		    + InetAddress.getLocalHost().getHostAddress() + " Port " + ClientServerConsts.PORT);
	    
	    while (true)
	    {
		try
		{
		    Socket socket = serverSocket.accept();
		    
		    myRequestHandler.HandleRequestAsync(socket);
		}
		catch (Exception e)
		{		    
		    CPS_Tracer.TraceError(e.toString());
		    // Socked will be closed and the client will get
		    // ServerResponse with Failed result
		}
	    }
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError(e.toString());
	}
	
	CPS_Tracer.TraceInformation("Server is shutting down.");
    }
    
    public static void main(String[] args) throws Exception
    {
	new ServerController().ListenAndResponse();
    }
}