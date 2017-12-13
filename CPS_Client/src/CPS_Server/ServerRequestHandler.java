package CPS_Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import application.ClientServerCPS.ClientRequest;
import application.ClientServerCPS.ClientRequestBase;
import application.ClientServerCPS.ClientServerConsts;
import application.ClientServerCPS.ServerResponse;
import application.Models.FullMembership;
import java.util.concurrent.CompletableFuture;


public class ServerRequestHandler
{
    public void HandleRequestAsync(Socket socket)
    {
	CompletableFuture.runAsync(() ->
	{
	    try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
		    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream()))
	    {
		
		ClientRequestBase clientRequest = (ClientRequestBase) inputStream.readObject();
		
		outputStream.writeObject(ExtractAndApplyRequest(clientRequest));
	    }
	    catch (Exception e)
	    {
		e.printStackTrace();
	    }
	    finally
	    {
		try
		{
		    socket.close();
		}
		catch (IOException e)
		{
		    // Probably socket was broke, the client will get exception.
		    e.printStackTrace();
		}
	    }
	});
    }
    
    @SuppressWarnings("unchecked")
    private Object ExtractAndApplyRequest(ClientRequestBase clientRequestBase)
    {
	switch (clientRequestBase.getServerDestination())
	{
	case ClientServerConsts.RegisterFullMembership:
	    return RegisterFullMembership(((ClientRequest<FullMembership>) clientRequestBase).GetSentObject());
	
	default:
	    return null;  // Todo - return server response
	}
    }
    
    private ServerResponse<FullMembership> RegisterFullMembership(FullMembership fullMembership)
    {
	return null; // Todo - send to DB
    }
}
