package application.ClientServerCPS;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import application.Consts;
import application.Models.FullMembership;

public class RequestsSender
{
    @SuppressWarnings("unchecked")
    public static ServerResponse<FullMembership> RegisterFullMembership(FullMembership fullMembership)
    {
	try (Socket socket = new Socket("localHost", Consts.PORT);
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()))
	{
	    
	    ClientRequest<FullMembership> clientRequest = new ClientRequest<>(fullMembership, "FullMembershipRegister");
	    
	    outputStream.writeObject(clientRequest);
	    
	    ServerResponse<FullMembership> serverResponse = (ServerResponse<FullMembership>) inputStream.readObject();
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    
	    return new ServerResponse<FullMembership>(RequestResult.Failed, null, "Internal server error");
	}
    }
}
