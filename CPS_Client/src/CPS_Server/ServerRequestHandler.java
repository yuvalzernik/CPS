package CPS_Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import application.Models.FullMembership;
import clientServerCPS.ClientRequest;
import clientServerCPS.ClientRequestBase;
import clientServerCPS.ClientServerConsts;
import clientServerCPS.RequestResult;
import clientServerCPS.ServerResponse;

import java.util.concurrent.CompletableFuture;


public class ServerRequestHandler
{
    public void HandleRequestAsync(Socket socket)
    {
	CompletableFuture.runAsync(() ->
	{
	    try (Socket mySocket = socket;
		    ObjectInputStream inputStream = new ObjectInputStream(mySocket.getInputStream());
		    ObjectOutputStream outputStream = new ObjectOutputStream(mySocket.getOutputStream()))
	    {
		ClientRequestBase clientRequest = (ClientRequestBase) inputStream.readObject();
		
		outputStream.writeObject(ExtractAndApplyRequest(clientRequest));
	    }
	    catch (Exception e)
	    {
		e.printStackTrace();
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
	    return null;  // Todo - return server response somehow
	}
    }
    
    private ServerResponse<FullMembership> RegisterFullMembership(FullMembership fullMembership)
    {
	// Todo - send to DB
	
	return new ServerResponse<FullMembership>(RequestResult.Succeed, null, null);
    }
}
