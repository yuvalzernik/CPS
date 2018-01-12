package cps.clientServer;

import java.io.Serializable;

public class ClientRequest implements Serializable
{    
    private static final long serialVersionUID = 1L;
    
    private String serverDestination;
    
    private Object sentObject;
            
    public ClientRequest(Object sentObject, String serverDestination)
    {
	this.serverDestination = serverDestination;
	
	this.sentObject = sentObject;
    }
    
    public Object GetSentObject()
    {
	return sentObject;
    }
    
    public String getServerDestination()
    {
	return serverDestination;
    }
}
