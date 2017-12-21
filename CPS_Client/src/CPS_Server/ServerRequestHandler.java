package CPS_Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import clientServerCPS.ClientRequest;
import clientServerCPS.ClientServerConsts;
import clientServerCPS.RequestResult;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.FullMembership;
import entities.OrderInAdvance;
import entities.PartialMembership;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import CPS_Utilities.CPS_Tracer;

public class ServerRequestHandler // pLw9Zaqp{ey`2,Ve

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
		ClientRequest clientRequest = (ClientRequest) inputStream.readObject();
		
		outputStream.writeObject(ExtractAndApplyRequest(clientRequest));
	    }
	    catch (Exception e)
	    {
		CPS_Tracer.TraceError(e.toString());
	    }
	});
    }
    
    private Object ExtractAndApplyRequest(ClientRequest clientRequest)
    {
	switch (clientRequest.getServerDestination())
	{
	case ClientServerConsts.RegisterFullMembership:
	    return RegisterFullMembership((FullMembership) clientRequest.GetSentObject());
	
	case ClientServerConsts.RegisterPartialMembership:
	    return RegisterPartialMembership((PartialMembership) clientRequest.GetSentObject());
	
	case ClientServerConsts.AddCustomerIfNotExists:
	    return AddCustomerIfNotExists((Customer) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetFullMembership:
	    return GetFullMembership((String) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetPartialMembership:
	    return GetPartialMembership((String) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetCustomer:
	    return GetCustomer((String) clientRequest.GetSentObject());
	    
	case ClientServerConsts.OrderInAdvance:
		return OrderInAdvance((OrderInAdvance) clientRequest.GetSentObject());///////what????
	
	case ClientServerConsts.GetOrderInAdvance:
		return GetOrderInAdvance((String) clientRequest.GetSentObject());
	
	default:
	    CPS_Tracer.TraceError(
		    "Server recived unknown destination. \nDestination: " + clientRequest.getServerDestination());
	    
	    throw new IllegalArgumentException(clientRequest.getServerDestination());
	}
    }
    
    private ArrayList<String> ConvertToCarList(String carListString)
    {
	return new ArrayList<>(Arrays.asList(carListString.split(" ,")));
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
	CPS_Tracer.TraceInformation("Registering full membership: \n" + fullMembership);
	
	try
	{
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
    
    private ServerResponse<PartialMembership> RegisterPartialMembership(PartialMembership partialMembership)
    {
	CPS_Tracer.TraceInformation("Registering partial membership: \n" + partialMembership);
	
	try
	{
	    String preparedStatementString = "INSERT INTO PartialMemberships(subscriptionId, customerId, startingDate, endingDate, parkinglot, carList, exitTime) VALUES(?, ?, ?, ?, ?, ?, ?)";
	    
	    ArrayList<String> values = new ArrayList<>();
	    
	    String subscriptionId = GenerateSubscriptionId(
		    "SELECT subscriptionId FROM PartialMemberships WHERE subscriptionId = ?", 2000000);
	    
	    values.add(subscriptionId);
	    values.add(partialMembership.GetCustomerId());
	    values.add(partialMembership.GetStartingDate().toString());
	    values.add(partialMembership.GetEndingDate().toString());
	    values.add(partialMembership.GetParkinglot());
	    values.add(partialMembership.CarListString());
	    values.add(partialMembership.GetExitTime().toString());
	    
	    AddRowToTable(preparedStatementString, values);
	    
	    partialMembership.SetSubscriptionId(subscriptionId);
	    
	    ServerResponse<PartialMembership> serverResponse = new ServerResponse<>(RequestResult.Succeed,
		    partialMembership, null);
	    
	    CPS_Tracer.TraceInformation("Server response to client after register: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<PartialMembership> serverResponse = new ServerResponse<>(RequestResult.Failed,
		    partialMembership, "Failed to add fullMembership to the table");
	    
	    CPS_Tracer.TraceError("Failed to add row to FullMemberships", e);
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<OrderInAdvance> OrderInAdvance (OrderInAdvance orderInAdvance )
    {
	CPS_Tracer.TraceInformation("Adding Order In Advance: \n" + orderInAdvance);
	
	try
	{
	    String preparedStatementString = "INSERT INTO OrderInAdvance(orderId, customerId, parkingLot, carNumber, email, startingDate, endingDate, startHour, endHour) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    ArrayList<String> values = new ArrayList<>();
	    
	    String orderId = GenerateSubscriptionId(
		    "SELECT orderId FROM OrderInAdvance WHERE orderId = ?",3000000);
	    
	    values.add(orderId);
	    values.add(orderInAdvance.getCustomerId());
	    values.add(orderInAdvance.getParkinglot());
	    values.add(orderInAdvance.getCarNumber());
	    values.add(orderInAdvance.getEmail());
	    values.add(orderInAdvance.getArrivalDate().toString());
	    values.add(orderInAdvance.getLeavingDate().toString());
	    values.add(orderInAdvance.getArrivalHour().toString());
	    values.add(orderInAdvance.getArrivalHour().toString());
	    
	    
	    AddRowToTable(preparedStatementString, values);
	    
	    orderInAdvance.setOrderId(orderId);
	    
	    ServerResponse<OrderInAdvance> serverResponse = new ServerResponse<>(RequestResult.Succeed, orderInAdvance,
		    null);
	    
	    CPS_Tracer.TraceInformation("Server response to client after adding order: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<OrderInAdvance> serverResponse = new ServerResponse<>(RequestResult.Failed, orderInAdvance,
		    "Failed to add Order to the table");
	    
	    CPS_Tracer.TraceError("Failed to add row to OrderInAdvance", e);
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<Customer> AddCustomerIfNotExists(Customer customer)
    {
	CPS_Tracer.TraceInformation("Trying to add customer: \n" + customer);
	
	try
	{
	    ServerResponse<Customer> serverResponse;
	    
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
    
    private ServerResponse<FullMembership> GetFullMembership(String subscriptionId)
    {
	CPS_Tracer.TraceInformation("Get full membership: \n" + subscriptionId);
	
	try (PreparedStatement preparedStatement = mySqlConnection
		.prepareStatement("SELECT * FROM FullMemberships WHERE subscriptionId = ?"))
	{
	    ServerResponse<FullMembership> serverResponse;
	    
	    FullMembership fullMembership;
	    
	    preparedStatement.setString(1, subscriptionId);
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.NotFound, null, subscriptionId + "Not Found");
	    }
	    else
	    {
		resultSet.next();
		
		fullMembership = new FullMembership(resultSet.getString(2), LocalDate.parse(resultSet.getString(3)),
			LocalDate.parse(resultSet.getString(4)), resultSet.getString(5));
		
		fullMembership.SetSubscriptionId(resultSet.getString(1));
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, fullMembership, "Found");
	    }
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to get full membership: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting full membership.\nSubscription Id: " + subscriptionId, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get full membership");
	}
    }
    
    private ServerResponse<PartialMembership> GetPartialMembership(String subscriptionId)
    {
	CPS_Tracer.TraceInformation("Get partial membership: \n" + subscriptionId);
	
	try (PreparedStatement preparedStatement = mySqlConnection
		.prepareStatement("SELECT * FROM PartialMemberships WHERE subscriptionId = ?"))
	{
	    ServerResponse<PartialMembership> serverResponse;
	    
	    PartialMembership partialMembership;
	    
	    preparedStatement.setString(1, subscriptionId);
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.NotFound, null, subscriptionId + "Not Found");
	    }
	    else
	    {
		resultSet.next();
		
		partialMembership = new PartialMembership(resultSet.getString(2),
			LocalDate.parse(resultSet.getString(3)), LocalDate.parse(resultSet.getString(4)),
			resultSet.getString(5), ConvertToCarList(resultSet.getString(6)),
			LocalTime.parse(resultSet.getString(7)));
		
		partialMembership.SetSubscriptionId(resultSet.getString(1));
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, partialMembership, "Found");
	    }
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to get partial membership: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting partial membership.\nSubscription Id: " + subscriptionId, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get partial membership");
	}
    }
    
    private ServerResponse<Customer> GetCustomer(String customerId)
    {
	CPS_Tracer.TraceInformation("Get customer: \n" + customerId);
	
	try (PreparedStatement preparedStatement = mySqlConnection
		.prepareStatement("SELECT * FROM Customers WHERE customerId = ?"))
	{
	    ServerResponse<Customer> serverResponse;
	    
	    Customer customer;
	    
	    preparedStatement.setString(1, customerId);
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.NotFound, null, customerId + "Not Found");
	    }
	    else
	    {
		resultSet.next();
		
		customer = new Customer(resultSet.getString(1), resultSet.getString(2),
			Float.parseFloat(resultSet.getString(3)));
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, customer, "Found");
	    }
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to get customer: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting customer.\nSubscription Id: " + customerId, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get customer");
	}
    }
    
    private ServerResponse<OrderInAdvance> GetOrderInAdvance(String orderId)
    {
	CPS_Tracer.TraceInformation("Get Order Id: \n" + orderId);
	
	try (PreparedStatement preparedStatement = mySqlConnection
		.prepareStatement("SELECT * FROM OrderInAdvance WHERE orderId = ?"))
	{
	    ServerResponse<OrderInAdvance> serverResponse;
	    
	    OrderInAdvance orderInAdvance;
	    
	    preparedStatement.setString(1, orderId);
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.NotFound, null, orderId + "Not Found");
	    }
	    else
	    {
		resultSet.next();
		
		orderInAdvance = new OrderInAdvance(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), LocalDate.parse(resultSet.getString(6)),
			LocalDate.parse(resultSet.getString(7)),LocalTime.parse(resultSet.getString(8)),LocalTime.parse(resultSet.getString(9)));
		
		orderInAdvance.setOrderId(resultSet.getString(1));
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, orderInAdvance, "Found");
	    }
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to get order in advance: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting order.\nOrder Id: " + orderId, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get order ");
	}
    }
}
