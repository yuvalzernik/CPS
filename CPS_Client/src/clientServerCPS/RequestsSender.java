package clientServerCPS;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import CPS_Utilities.Consts;
import application.Models.FullMembership;

public class RequestsSender
{
    @SuppressWarnings("unchecked")
    private static <T> ServerResponse<T> SendRequest(T sentObject, String serverDestination)
    {
	try (Socket socket = new Socket("localHost", Consts.PORT);
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()))
	{
	    
	    ClientRequest<T> clientRequest = new ClientRequest<>(sentObject, serverDestination);
	    
	    outputStream.writeObject(clientRequest);
	    
	    ServerResponse<T> serverResponse = (ServerResponse<T>) inputStream.readObject();
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    
	    return new ServerResponse<T>(RequestResult.Failed, null, "Internal server error");
	}
    }
    
    public static ServerResponse<FullMembership> RegisterFullMembership(FullMembership fullMembership)
    {
	return SendRequest(fullMembership, ClientServerConsts.RegisterFullMembership);
    }
}
