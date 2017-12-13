package CPS_Server;

import java.net.ServerSocket;
import java.net.Socket;

import application.Consts;

public class ServerController
{
    private ServerRequestHandler myRequestHandler;
    
    public ServerController()
    {
	myRequestHandler = new ServerRequestHandler();
    }
    
    private void ListenAndResponse()
    {
	try (ServerSocket serverSocket = new ServerSocket(Consts.PORT))
	{
	    while (true)
	    {
		try
		{
		    Socket socket = serverSocket.accept();
		    
		    myRequestHandler.HandleRequestAsync(socket);
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		    // Socked will be closed and the client will return
		    // ServerResult with Failed result
		}
	    }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    // Server will shut down.
	}
    }
    
    public static void main(String[] args)
    {
	new ServerController().ListenAndResponse();
    }
}