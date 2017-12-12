package CPS_Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import application.ClientServerCPS.ClientRequest;

public class ServerController
{
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inStream = null;
    
    public void communicate()
    {
	try
	{
	    serverSocket = new ServerSocket(5555);
	    socket = serverSocket.accept();
	    System.out.println("Connected");
	    inStream = new ObjectInputStream(socket.getInputStream());
	    
	    ClientRequest clientRequest = (ClientRequest) inStream.readObject();
	    System.out.println("Object received = " + clientRequest.serverDestination);
	    socket.close();
	    
	    inStream.close();
	    
	}
	catch (SocketException se)
	{
	    System.exit(0);
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
	catch (ClassNotFoundException cn)
	{
	    cn.printStackTrace();
	}
    }
    
    public static void main(String[] args)
    {
	ServerController server = new ServerController();
	server.communicate();
    }
}