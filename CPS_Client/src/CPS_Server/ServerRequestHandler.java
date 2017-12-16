package CPS_Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clientServerCPS.ClientRequest;
import clientServerCPS.ClientRequestBase;
import clientServerCPS.ClientServerConsts;
import clientServerCPS.RequestResult;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.FullMembership;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import CPS_Utilities.CPS_Tracer;

public class ServerRequestHandler
{
    Connection mySqlConnection;
    
    public ServerRequestHandler() throws Exception
    {
	try
	{
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    
	    mySqlConnection = DriverManager.getConnection(
		    "jdbc:mysql://softengproject.cspvcqknb3vj.eu-central-1.rds.amazonaws.com/tuatara_schema",
		    "tuatara_admin", "pLw9Zaqp{ey`2,Ve");
	}
	catch (SQLException e)
	{
	    CPS_Tracer.TraceError("Failed to connect to DB.", e);
	    throw e;
	}
    }
    
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
		CPS_Tracer.TraceError(e.toString());
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
	
	case ClientServerConsts.AddCustomerIfNotExists:
	    return AddCustomerIfNotExists(((ClientRequest<Customer>) clientRequestBase).GetSentObject());
	
	default:
	    CPS_Tracer.TraceError(
		    "Server recived unknown destination. \nDestination: " + clientRequestBase.getServerDestination());
	    return null; // Todo - return server response somehow
	}
    }
    
    private String GenerateSubscriptionId(String preparedStatementString, int offset) throws Exception
    {
	boolean subscriptionIdFound = false;
	
	String subscriptionId;
	
	try (PreparedStatement preparedStatement = mySqlConnection.prepareStatement(preparedStatementString))
	{
	    do
	    {
		subscriptionId = Integer.toString(new Random().nextInt(1000000) + offset);
		
		preparedStatement.setString(1, subscriptionId);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (!resultSet.isBeforeFirst())
		{
		    subscriptionIdFound = true;
		}
	    }
	    while (!subscriptionIdFound);
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed to generate subsciption id.", e);
	    throw e;
	}
	
	return subscriptionId;
    }
    
    private void AddRowToTable(String preparedStatementString, ArrayList<String> values) throws Exception
    {
	try (PreparedStatement preparedStatement = mySqlConnection.prepareStatement(preparedStatementString))
	{
	    for (int i = 0; i < values.size(); i++)
	    {
		preparedStatement.setString(i + 1, values.get(i));
	    }
	    
	    preparedStatement.executeUpdate();
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed to add row.\nStatement string: " + preparedStatementString, e);
	    
	    throw e;
	}
    }
    
    private ServerResponse<FullMembership> RegisterFullMembership(FullMembership fullMembership)
    {
	try
	{
	    CPS_Tracer.TraceInformation("Registering full membership: \n" + fullMembership);
	    
	    String preparedStatementString = "INSERT INTO FullMemberships(subscriptionId, customerId, startingDate, endingDate, carNumber) VALUES(?, ?, ?, ?, ?)";
	    
	    ArrayList<String> values = new ArrayList<>();
	    
	    String subscriptionId = GenerateSubscriptionId(
		    "SELECT subscriptionId FROM FullMemberships WHERE subscriptionId = ?", 1000000);
	    
	    values.add(subscriptionId);
	    values.add(fullMembership.GetCustomerId());
	    values.add(fullMembership.GetStartingDate().toString());
	    values.add(fullMembership.GetEndingDate().toString());
	    values.add(fullMembership.GetCarNumber());
	    
	    AddRowToTable(preparedStatementString, values);
	    
	    fullMembership.SetSubscriptionId(subscriptionId);
	    
	    ServerResponse<FullMembership> serverResponse = new ServerResponse<>(RequestResult.Succeed, fullMembership,
		    null);
	    
	    CPS_Tracer.TraceInformation("Server response to client after register: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<FullMembership> serverResponse = new ServerResponse<>(RequestResult.Failed, fullMembership,
		    "Failed to add fullMembership to the table");
	    
	    CPS_Tracer.TraceError("Failed to add row to FullMemberships", e);
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<Customer> AddCustomerIfNotExists(Customer customer)
    {	
	try
	{
	    ServerResponse<Customer> serverResponse;
	    
	    CPS_Tracer.TraceInformation("Trying to add customer: \n" + customer);
	    
	    // Check if customer doesn't exists:
	    
	    String preparedStatementString = "SELECT customerId FROM Customers WHERE customerId = ?";
	    
	    PreparedStatement preparedStatement = mySqlConnection.prepareStatement(preparedStatementString);
	    
	    preparedStatement.setString(1, customer.GetId());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    	    
	    if (resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.AlredyExist, customer, null);
	    }
	    else // New customer:
	    {
		preparedStatementString = "INSERT INTO Customers(customerId, email, balance) VALUES(?, ?, ?)";
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, customer, null);
		
		ArrayList<String> values = new ArrayList<>();
		
		values.add(customer.GetId());
		values.add(customer.GetEmail());
		values.add(Float.toString(customer.GetBalance()));
		
		AddRowToTable(preparedStatementString, values);
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, customer, null);
	    }
	    
	    preparedStatement.close();
	    
	    CPS_Tracer.TraceInformation("Server response to client after trying to add customer: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<Customer> serverResponse = new ServerResponse<>(RequestResult.Failed, customer,
		    "Failed to add customer to the table");
	    
	    CPS_Tracer.TraceError("Failed to add row to Customers", e);
	    	    
	    return serverResponse;
	}
    }
}
