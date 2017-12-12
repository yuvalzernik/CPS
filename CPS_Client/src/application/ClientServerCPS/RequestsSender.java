package application.ClientServerCPS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;


public class RequestsSender
{
    public void Register()
    {
	try (Socket socket = new Socket("localHost", 5555))
	{
	    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
	    	    
	    ClientRequest test = new ClientRequest();
	    
	    test.serverDestination = "testtest";
	    
	    outputStream.writeObject(test);
	    
	    outputStream.close();
	}
	catch (SocketException se)
	{
	    se.printStackTrace();
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
    
    public static void main(String[] args)
    {
	RequestsSender requestsSender = new RequestsSender();
	requestsSender.Register();
    }
    
}
