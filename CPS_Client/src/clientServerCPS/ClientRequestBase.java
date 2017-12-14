package application.ClientServerCPS;

import java.io.Serializable;

public abstract class ClientRequestBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String serverDestination;
    
    public ClientRequestBase(String serverDestination)
    {
	this.serverDestination = serverDestination;
    }
    
    public String getServerDestination()
    {
	return serverDestination;
    }
}
