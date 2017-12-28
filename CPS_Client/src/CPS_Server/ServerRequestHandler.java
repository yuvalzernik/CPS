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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import CPS_Utilities.CPS_Tracer;
import CPS_Utilities.LoginIdentification;
import clientServerCPS.ClientRequest;
import clientServerCPS.ClientServerConsts;
import clientServerCPS.RequestResult;
import clientServerCPS.ServerResponse;
import entities.AddRealTimeParkingRequest;
import entities.ChangeParkingSpotStatusRequest;
import entities.ChangeParkinglotStatusRequest;
import entities.ChangeRatesRequest;
import entities.ChangeRatesResponse;
import entities.CloseComplaintRequest;
import entities.Complaint;
import entities.Customer;
import entities.Employee;
import entities.FullMembership;
import entities.ParkingSpot;
import entities.Parkinglot;
import entities.PartialMembership;
import entities.Reservation;
import entities.enums.ComplaintStatus;
import entities.enums.EmployeeType;
import entities.enums.ParkingSpotStatus;
import entities.enums.ParkinglotStatus;
import entities.enums.ReservationStatus;
import entities.enums.ReservationType;

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
	
	case ClientServerConsts.Reservation:
	    return Reservation((Reservation) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetReservation:
	    return GetReservation((String) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetEmployee:
	    return GetEmployee((LoginIdentification) clientRequest.GetSentObject());
	
	case ClientServerConsts.AddParkinglot:
	    return AddParkinglot((Parkinglot) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetAllParkinglots:
	    return GetAllParkinglots();
	
	case ClientServerConsts.AddComplaint:
	    return AddComplaint((Complaint) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetAllActiveComplaints:
	    return GetAllActiveComplaints();
	
	case ClientServerConsts.CloseComplaint:
	    return CloseComplaint((CloseComplaintRequest) clientRequest.GetSentObject());
	
	case ClientServerConsts.AddChangeRatesRequest:
	    return AddChangeRatesRequest((ChangeRatesRequest) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetAllChangeRatesRequests:
	    return GetAllChangeRatesRequests();
	
	case ClientServerConsts.CloseChangeRatesRequest:
	    return CloseChangeRatesRequest((ChangeRatesResponse) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetParkingLot:
	    return GetParkinglot((String) clientRequest.GetSentObject());
	
	case ClientServerConsts.ChangeParkinglotStatus:
	    return ChangeParkinglotStatus((ChangeParkinglotStatusRequest) clientRequest.GetSentObject());
	
	case ClientServerConsts.ChangeParkingSpotStatus:
	    return ChangeParkingSpotStatus((ChangeParkingSpotStatusRequest) clientRequest.GetSentObject());
	
	case ClientServerConsts.GetAllDisabledParkingSpots:
	    return GetAllDisabledParkingSpots();
	
	case ClientServerConsts.ChangeExpireFullMembership:
	    return ChangeExpireFullMembership((FullMembership) clientRequest.GetSentObject());
	
	case ClientServerConsts.ChangeExpirePartialMembership:
	    return ChangeExpirePartialMembership((PartialMembership) clientRequest.GetSentObject());
	
	case ClientServerConsts.TryInsertGuestCar:
	    return TryInsertGuestCar((AddRealTimeParkingRequest) clientRequest.GetSentObject());
	
	default:
	    CPS_Tracer.TraceError(
		    "Server recived unknown destination. \nDestination: " + clientRequest.getServerDestination());
	    
	    throw new IllegalArgumentException(clientRequest.getServerDestination());
	}
    }
    
    private boolean IsDateTimesOverLap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2,
	    LocalDateTime end2)
    {
	if (start1.isAfter(end2) || start2.isAfter(end1))
	{
	    return false;
	}
	
	return true;
	
    }
    
    private ArrayList<String> ConvertToCarList(String carListString)
    {
	return new ArrayList<>(Arrays.asList(carListString.split(" ,")));
    }
    
    private String GenerateUniqueId(String preparedStatementString, int offset) throws Exception
    {
	boolean uniqueIdFound = false;
	
	String uniqueId;
	
	try (PreparedStatement preparedStatement = mySqlConnection.prepareStatement(preparedStatementString))
	{
	    do
	    {
		uniqueId = Integer.toString(new Random().nextInt(1000000) + offset);
		
		preparedStatement.setString(1, uniqueId);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (!resultSet.isBeforeFirst())
		{
		    uniqueIdFound = true;
		}
	    }
	    while (!uniqueIdFound);
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed to generate subsciption id.", e);
	    throw e;
	}
	
	return uniqueId;
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
    
    private int GetResultSetSize(ResultSet resultSet) throws SQLException
    {
	int size = 0;
	while (resultSet.next())
	{
	    size++;
	}
	resultSet.beforeFirst();
	return size;
    }
    
    private boolean IsParkingSpotFree(String parkinglot, LocalDateTime fromDateTime, LocalDateTime toDateTime)
	    throws Exception
    {
	CPS_Tracer.TraceInformation(
		"Checking if parking lot: " + parkinglot + " is free from " + fromDateTime + " to " + toDateTime);
	try
	{
	    int tempCount = 0;
	    
	    // get parking lot:
	    
	    PreparedStatement preparedStatement = mySqlConnection.prepareStatement(
		    "SELECT * FROM ParkingLots WHERE parkinglotName = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);
	    
	    preparedStatement.setString(1, parkinglot);
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    resultSet.next();
	    
	    int parkingSpots = Integer.parseInt(resultSet.getString(6));
	    
	    CPS_Tracer.TraceInformation("starting reduce spots from: " + parkingSpots);
	    
	    // reduce cars that park now and have a collision:
	    CPS_Tracer.TraceInformation("reduce cars that park now and have a collision");
	    
	    preparedStatement = mySqlConnection.prepareStatement("SELECT * FROM RealTimeParking WHERE parkingLot = ?",
		    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	    
	    preparedStatement.setString(1, parkinglot);
	    
	    resultSet = preparedStatement.executeQuery();
	    
	    while (resultSet.next())
	    {
		LocalDateTime carExitDateTime = LocalDateTime.parse(resultSet.getString(4));
		
		if (carExitDateTime.isAfter(fromDateTime))
		{
		    tempCount++;
		}
	    }
	    
	    CPS_Tracer.TraceInformation("reduced " + tempCount);
	    
	    parkingSpots -= tempCount;
	    tempCount = 0;
	    
	    // reduce reservations:
	    CPS_Tracer.TraceInformation("reduce reservations");
	    
	    preparedStatement = mySqlConnection.prepareStatement(
		    "SELECT * FROM Reservations WHERE parkingLot = ? AND status = ? ", ResultSet.TYPE_SCROLL_SENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);
	    
	    preparedStatement.setString(1, parkinglot);
	    preparedStatement.setString(2, ReservationStatus.NotStarted.toString());
	    
	    resultSet = preparedStatement.executeQuery();
	    
	    while (resultSet.next())
	    {
		LocalDate carExitDate = LocalDate.parse(resultSet.getString(7));
		LocalTime carExitTime = LocalTime.parse(resultSet.getString(9));
		
		LocalDateTime carExitDateTime = LocalDateTime.of(carExitDate, carExitTime);
		
		LocalDate carStartingDate = LocalDate.parse(resultSet.getString(6));
		LocalTime carStartingTime = LocalTime.parse(resultSet.getString(8));
		
		LocalDateTime carStartingDateTime = LocalDateTime.of(carStartingDate, carStartingTime);
		
		if (IsDateTimesOverLap(fromDateTime, toDateTime, carStartingDateTime, carExitDateTime))
		{
		    tempCount++;
		}
	    }
	    
	    CPS_Tracer.TraceInformation("reduced " + tempCount);
	    
	    parkingSpots -= tempCount;
	    tempCount = 0;
	    
	    // reduce disable parking spots:
	    CPS_Tracer.TraceInformation("reduce disable parking spots");
	    
	    preparedStatement = mySqlConnection.prepareStatement(
		    "SELECT * FROM DisabledParkingSpots WHERE parkinglotName = ? AND status = ? ",
		    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	    
	    preparedStatement.setString(1, parkinglot);
	    preparedStatement.setString(2, ParkingSpotStatus.Disabled.toString());
	    
	    resultSet = preparedStatement.executeQuery();
	    
	    tempCount = GetResultSetSize(resultSet);
	    
	    CPS_Tracer.TraceInformation("reduced " + tempCount);
	    
	    parkingSpots -= tempCount;
	    tempCount = 0;
	    
	    // reduce full memberships:
	    
	    // no need.. they signed to all of the parking lots.. we will not
	    // have free spots..
	    
	    // reduce partial memberships:
	    CPS_Tracer.TraceInformation("reduce partial memberships");
	    
	    preparedStatement = mySqlConnection.prepareStatement(
		    "SELECT * FROM PartialMemberships WHERE Parkinglot = ? ", ResultSet.TYPE_SCROLL_SENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);
	    
	    preparedStatement.setString(1, parkinglot);
	    
	    resultSet = preparedStatement.executeQuery();
	    
	    while (resultSet.next())
	    {
		LocalDate carExitDate = LocalDate.parse(resultSet.getString(3));
		LocalTime carExitTime = LocalTime.parse(resultSet.getString(7));
		
		LocalDateTime carExitDateTime = LocalDateTime.of(carExitDate, carExitTime);
		
		LocalDate carStartingDate = LocalDate.parse(resultSet.getString(3));
		LocalTime carStartingTime = LocalTime.parse("00:00");
		
		LocalDateTime carStartingDateTime = LocalDateTime.of(carStartingDate, carStartingTime);
		
		LocalDate expiryDate = LocalDate.parse(resultSet.getString(4));
		
		while (!expiryDate.equals(carStartingDate))
		{
		    if (IsDateTimesOverLap(fromDateTime, toDateTime, carStartingDateTime, carExitDateTime))
		    {
			tempCount++;
			break;
		    }
		    carStartingDate = carStartingDate.plusDays(1);
		    
		    carStartingDateTime = carStartingDateTime.plusDays(1);
		    carExitDateTime = carExitDateTime.plusDays(1);
		}
	    }
	    
	    CPS_Tracer.TraceInformation("reduced " + tempCount);
	    
	    parkingSpots -= tempCount;
	    tempCount = 0;
	    
	    CPS_Tracer.TraceInformation("parking spots remaining: " + parkingSpots);
	    
	    if (parkingSpots > 0)
	    {
		return true;
	    }
	    else
	    {
		return false;
	    }
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in IsParkingSpotFree", e);
	    
	    throw e;
	}
    }
    
    private ServerResponse<CloseComplaintRequest> CloseComplaint(CloseComplaintRequest closeComplaintRequest)
    {
	CPS_Tracer.TraceInformation("Trying to close complaint: \n" + closeComplaintRequest.getComplaintId());
	
	try
	{
	    
	    String preparedStatementString = "SELECT * FROM Complaints WHERE complaintId = ?";
	    
	    PreparedStatement preparedStatement = mySqlConnection.prepareStatement(preparedStatementString,
		    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	    
	    preparedStatement.setString(1, closeComplaintRequest.getComplaintId());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		return new ServerResponse<>(RequestResult.NotFound, closeComplaintRequest,
			"Complaint id " + closeComplaintRequest.getComplaintId() + " not found");
	    }
	    
	    resultSet.next();
	    
	    if (closeComplaintRequest.getCompensation() > 0)
	    {
		PreparedStatement preparedStatement2 = mySqlConnection.prepareStatement(
			"SELECT * FROM Customers WHERE customerId = ? ", ResultSet.TYPE_SCROLL_SENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
		
		preparedStatement2.setString(1, resultSet.getString(2));
		
		ResultSet resultSet2 = preparedStatement2.executeQuery();
		
		resultSet2.next();
		
		resultSet2.updateString(3, Float
			.toString(Float.parseFloat(resultSet2.getString(3)) + closeComplaintRequest.getCompensation()));
		
		resultSet2.updateRow();
	    }
	    
	    resultSet.updateString(5, ComplaintStatus.Closed.toString());
	    resultSet.updateString(6, Float.toString(closeComplaintRequest.getCompensation()));
	    resultSet.updateRow();
	    
	    ServerResponse<CloseComplaintRequest> serverResponse = new ServerResponse<>(RequestResult.Succeed,
		    closeComplaintRequest, "Closed successfully");
	    
	    CPS_Tracer.TraceInformation("Server response to client after closing complaint: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<CloseComplaintRequest> serverResponse = new ServerResponse<>(RequestResult.Failed, null,
		    "Failed to close complaint " + closeComplaintRequest.getComplaintId());
	    
	    CPS_Tracer.TraceError("Failed to update row in Complaints", e);
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<Complaint> AddComplaint(Complaint complaint)
    {
	CPS_Tracer.TraceInformation("Trying to add complaint: \n" + complaint);
	
	try
	{
	    // Checking if customer exists first:
	    
	    PreparedStatement preparedStatement = mySqlConnection
		    .prepareStatement("SELECT * FROM Customers WHERE customerId = ?");
	    
	    preparedStatement.setString(1, complaint.getCustomerId());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		CPS_Tracer.TraceError("Failed to add row to Complaints, customer not found");
		
		return new ServerResponse<>(RequestResult.NotFound, null,
			"Customer: " + complaint.getCustomerId() + " not found.");
	    }
	    
	    String preparedStatementString = "INSERT INTO Complaints(complaintId, customerId, fillingDateTime, complaintDetails, status, compensation) VALUES(?, ?, ?, ?, ?, ?)";
	    
	    String complaintId = GenerateUniqueId("SELECT complaintId FROM Complaints WHERE complaintId = ?", 1000000);
	    
	    preparedStatement = mySqlConnection.prepareStatement(preparedStatementString);
	    
	    preparedStatement.setString(1, complaintId);
	    preparedStatement.setString(2, complaint.getCustomerId());
	    preparedStatement.setString(3, complaint.getFillingDateTime().toString());
	    preparedStatement.setString(4, complaint.getComplaintDetails());
	    preparedStatement.setString(5, complaint.getStatus().toString());
	    preparedStatement.setString(6, Float.toString(complaint.getCompensation()));
	    
	    preparedStatement.executeUpdate();
	    
	    complaint.setComplaintId(complaintId);
	    
	    ServerResponse<Complaint> serverResponse = new ServerResponse<>(RequestResult.Succeed, complaint, null);
	    
	    CPS_Tracer.TraceInformation("Server response to client after trying to add complaint: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<Complaint> serverResponse = new ServerResponse<>(RequestResult.Failed, complaint,
		    "Failed to add complaint to the table");
	    
	    CPS_Tracer.TraceError("Failed to add row to Complaints", e);
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<ChangeRatesResponse> CloseChangeRatesRequest(ChangeRatesResponse changeRatesResponse)
    {
	CPS_Tracer.TraceInformation("Trying to close ChangeRatesRequest: \n" + changeRatesResponse);
	
	try
	{
	    String preparedStatementString = "SELECT * FROM ChangeRatesRequests WHERE requestId = ?";
	    
	    PreparedStatement preparedStatement = mySqlConnection.prepareStatement(preparedStatementString,
		    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	    
	    preparedStatement.setString(1, changeRatesResponse.getRequestId());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		CPS_Tracer.TraceInformation("Change rate request not foud: " + changeRatesResponse.getRequestId());
		
		return new ServerResponse<>(RequestResult.NotFound, changeRatesResponse,
			"Request id " + changeRatesResponse.getRequestId() + " not found");
	    }
	    
	    resultSet.next();
	    
	    if (changeRatesResponse.getIsApproved())
	    {
		
		preparedStatementString = "SELECT * FROM ParkingLots WHERE parkinglotName = ?";
		
		PreparedStatement preparedStatement2 = mySqlConnection.prepareStatement(preparedStatementString,
			ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		preparedStatement2.setString(1, resultSet.getString(2));
		
		ResultSet resultSet2 = preparedStatement2.executeQuery();
		
		resultSet2.next();
		
		// updates the parkinglot's row
		resultSet2.updateString(4, resultSet.getString(3));
		resultSet2.updateString(5, resultSet.getString(4));
		
		resultSet2.updateRow();
	    }
	    resultSet.deleteRow();
	    
	    ServerResponse<ChangeRatesResponse> serverResponse = new ServerResponse<>(RequestResult.Succeed,
		    changeRatesResponse, null);
	    
	    CPS_Tracer.TraceInformation("Server response to client after changing rates: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<ChangeRatesResponse> serverResponse = new ServerResponse<>(RequestResult.Failed,
		    changeRatesResponse, "Failed to change rates.");
	    
	    CPS_Tracer.TraceError("Failed to change rates.", e);
	    
	    e.printStackTrace();
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<ChangeRatesRequest> AddChangeRatesRequest(ChangeRatesRequest changeRatesRequest)
    {
	CPS_Tracer.TraceInformation("Trying to add ChangeRatesRequest: \n" + changeRatesRequest);
	
	try
	{
	    // Checking if parkinglot exists first:
	    
	    PreparedStatement preparedStatement = mySqlConnection
		    .prepareStatement("SELECT * FROM ParkingLots WHERE parkinglotName = ?");
	    
	    preparedStatement.setString(1, changeRatesRequest.getParkinglotName());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		CPS_Tracer.TraceError("Failed to add ChangeRatesRequest to ChangeRatesRequests, parkinglot not found");
		
		return new ServerResponse<>(RequestResult.NotFound, null,
			"parkinglot: " + changeRatesRequest.getParkinglotName() + " not found.");
	    }
	    
	    String preparedStatementString = "INSERT INTO ChangeRatesRequests(requestId, parkinglotName, newGuestRate, newInAdvanceRate) VALUES(?, ?, ?, ?)";
	    
	    ArrayList<String> values = new ArrayList<>();
	    
	    String requestId = GenerateUniqueId("SELECT requestId FROM ChangeRatesRequests WHERE requestId = ?",
		    1000000);
	    
	    values.add(requestId);
	    values.add(changeRatesRequest.getParkinglotName());
	    values.add(Float.toString(changeRatesRequest.getNewGuestRate()));
	    values.add(Float.toString(changeRatesRequest.getNewInAdvanceRate()));
	    
	    AddRowToTable(preparedStatementString, values);
	    
	    changeRatesRequest.setRequestId(requestId);
	    
	    ServerResponse<ChangeRatesRequest> serverResponse = new ServerResponse<>(RequestResult.Succeed,
		    changeRatesRequest, null);
	    
	    CPS_Tracer
		    .TraceInformation("Server response to client after adding ChangeRatesRequest: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<ChangeRatesRequest> serverResponse = new ServerResponse<>(RequestResult.Failed,
		    changeRatesRequest, "Failed to add ChangeRatesRequest to the table");
	    
	    CPS_Tracer.TraceError("Failed to add row to ChangeRatesRequests", e);
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<ArrayList<ChangeRatesRequest>> GetAllChangeRatesRequests()
    {
	CPS_Tracer.TraceInformation("Trying to get all ChangeRatesRequests.");
	
	try (PreparedStatement preparedStatement = mySqlConnection
		.prepareStatement("SELECT * FROM ChangeRatesRequests"))
	{
	    ServerResponse<ArrayList<ChangeRatesRequest>> serverResponse;
	    
	    ArrayList<ChangeRatesRequest> changeRatesRequests = new ArrayList<>();
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while (resultSet.next())
	    {
		ChangeRatesRequest changeRatesRequest = new ChangeRatesRequest(resultSet.getString(2),
			Float.parseFloat(resultSet.getString(3)), Float.parseFloat(resultSet.getString(4)));
		
		changeRatesRequest.setRequestId(resultSet.getString(1));
		
		changeRatesRequests.add(changeRatesRequest);
	    }
	    
	    serverResponse = new ServerResponse<>(RequestResult.Succeed, changeRatesRequests,
		    "Found " + changeRatesRequests.size() + " parkinglots.");
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to get all ChangeRatesRequests: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting ChangeRatesRequests.", e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get ChangeRatesRequests");
	}
    }
    
    private ServerResponse<FullMembership> RegisterFullMembership(FullMembership fullMembership)
    {
	CPS_Tracer.TraceInformation("Registering full membership: \n" + fullMembership);
	
	try
	{
	    String preparedStatementString = "INSERT INTO FullMemberships(subscriptionId, customerId, startDate, expiryDate, carNumber) VALUES(?, ?, ?, ?, ?)";
	    
	    ArrayList<String> values = new ArrayList<>();
	    
	    String subscriptionId = GenerateUniqueId(
		    "SELECT subscriptionId FROM FullMemberships WHERE subscriptionId = ?", 1000000);
	    
	    values.add(subscriptionId);
	    values.add(fullMembership.GetCustomerId());
	    values.add(fullMembership.GetStartDate().toString());
	    values.add(fullMembership.getExpiryDate().toString());
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
    
    private ServerResponse<FullMembership> ChangeExpireFullMembership(FullMembership fullMembership)
    {
	
	CPS_Tracer.TraceInformation("Change expiry Date full membership: \n" + fullMembership);
	
	try (PreparedStatement preparedStatement = mySqlConnection.prepareStatement(
		"SELECT * FROM FullMemberships WHERE subscriptionId = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
		ResultSet.CONCUR_UPDATABLE))
	{
	    
	    ServerResponse<FullMembership> serverResponse;
	    
	    preparedStatement.setString(1, fullMembership.GetSubscriptionId());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.NotFound, null, "Full Membership Not Found");
	    }
	    else
	    {
		resultSet.next();
		
		resultSet.updateString(4, fullMembership.getExpiryDate().plusDays(28).toString());
		
		resultSet.updateRow();
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, fullMembership, "Updated");
	    }
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to change Full Membership expiry date : \n"
			    + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in changing  Full Membership expiry date: \n" + fullMembership, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed changing  Full Membership expiry date ");
	    
	}
    }
    
    private ServerResponse<PartialMembership> ChangeExpirePartialMembership(PartialMembership partialMembership)
    {
	CPS_Tracer.TraceInformation("Change expiry Date partial membership: \n" + partialMembership);
	
	try (PreparedStatement preparedStatement = mySqlConnection.prepareStatement(
		"SELECT * FROM PartialMemberships WHERE subscriptionId = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
		ResultSet.CONCUR_UPDATABLE))
	{
	    
	    ServerResponse<PartialMembership> serverResponse;
	    
	    preparedStatement.setString(1, partialMembership.GetSubscriptionId());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.NotFound, null, "Partial Membership Not Found");
	    }
	    else
	    {
		resultSet.next();
		
		resultSet.updateString(4, partialMembership.getExpiryDate().plusDays(28).toString());
		
		resultSet.updateRow();
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, partialMembership, "Updated");
	    }
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to change Partial Membership expiry date : \n"
			    + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in changing  Partial Membership expiry date: \n" + partialMembership, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed changing  Partial Membership expiry date ");
	    
	}
    }
    
    private ServerResponse<PartialMembership> RegisterPartialMembership(PartialMembership partialMembership)
    {
	CPS_Tracer.TraceInformation("Registering partial membership: \n" + partialMembership);
	
	try
	{
	    String preparedStatementString = "INSERT INTO PartialMemberships(subscriptionId, customerId, startDate, expiryDate, parkinglot, carList, exitTime) VALUES(?, ?, ?, ?, ?, ?, ?)";
	    
	    ArrayList<String> values = new ArrayList<>();
	    
	    String subscriptionId = GenerateUniqueId(
		    "SELECT subscriptionId FROM PartialMemberships WHERE subscriptionId = ?", 2000000);
	    
	    values.add(subscriptionId);
	    values.add(partialMembership.GetCustomerId());
	    values.add(partialMembership.GetStartDate().toString());
	    values.add(partialMembership.getExpiryDate().toString());
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
		    partialMembership, "Failed to add partialMembership to the table");
	    
	    CPS_Tracer.TraceError("Failed to add row to PartialMemberships", e);
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<Reservation> Reservation(Reservation reservation)
    {
	CPS_Tracer.TraceInformation("Adding Reservation: \n" + reservation);
	
	try
	{
	    String preparedStatementString = "INSERT INTO Reservations(orderId, type, customerId, parkingLot, carNumber, startingDate, endingDate, startHour, endHour, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    ArrayList<String> values = new ArrayList<>();
	    
	    String orderId = GenerateUniqueId("SELECT orderId FROM Reservations WHERE orderId = ?", 3000000);
	    
	    values.add(orderId);
	    values.add(reservation.getReservationType().toString());
	    values.add(reservation.getCustomerId());
	    values.add(reservation.getParkinglot());
	    values.add(reservation.getCarNumber());
	    values.add(reservation.getArrivalDate().toString());
	    values.add(reservation.getLeavingDate().toString());
	    values.add(reservation.getArrivalHour().toString());
	    values.add(reservation.getLeavingHour().toString());
	    values.add(reservation.getReservationStatus().toString());
	    
	    AddRowToTable(preparedStatementString, values);
	    
	    reservation.setOrderId(orderId);
	    
	    ServerResponse<Reservation> serverResponse = new ServerResponse<>(RequestResult.Succeed, reservation, null);
	    
	    CPS_Tracer.TraceInformation("Server response to client after adding order: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<Reservation> serverResponse = new ServerResponse<>(RequestResult.Failed, reservation,
		    "Failed to add Reservation to the table");
	    
	    CPS_Tracer.TraceError("Failed to add row to Reservations", e);
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<Parkinglot> AddParkinglot(Parkinglot parkinglot)
    {
	CPS_Tracer.TraceInformation("Adding Parkinglot: \n" + parkinglot);
	
	try
	{
	    String preparedStatementString = "INSERT INTO ParkingLots(parkinglotName, width, status, guestRate, inAdvanceRate, totalSpotsNumber) VALUES(?, ?, ?, ?, ?, ?)";
	    
	    ArrayList<String> values = new ArrayList<>();
	    
	    values.add(parkinglot.getParkinglotName());
	    values.add(Integer.toString(parkinglot.getWidth()));
	    values.add(parkinglot.getStatus().toString());
	    values.add(Float.toString(parkinglot.getGuestRate()));
	    values.add(Float.toString(parkinglot.getInAdvanceRate()));
	    values.add(Integer.toString(parkinglot.getTotalSpotsNumber()));
	    
	    AddRowToTable(preparedStatementString, values);
	    
	    ServerResponse<Parkinglot> serverResponse = new ServerResponse<>(RequestResult.Succeed, parkinglot, null);
	    
	    CPS_Tracer.TraceInformation("Server response to client after adding order: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<Parkinglot> serverResponse = new ServerResponse<>(RequestResult.Failed, parkinglot,
		    "Failed to add parkinglot to the table");
	    
	    CPS_Tracer.TraceError("Failed to add row to Parkinglots", e);
	    
	    return serverResponse;
	}
    }
    
    private ServerResponse<ArrayList<Parkinglot>> GetAllParkinglots()
    {
	CPS_Tracer.TraceInformation("Trying to get all parkinglots.");
	
	try (PreparedStatement preparedStatement = mySqlConnection.prepareStatement("SELECT * FROM ParkingLots"))
	{
	    ServerResponse<ArrayList<Parkinglot>> serverResponse;
	    
	    ArrayList<Parkinglot> parkinglots = new ArrayList<>();
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while (resultSet.next())
	    {
		Parkinglot parkinglot = new Parkinglot(resultSet.getString(1), Integer.parseInt(resultSet.getString(2)),
			ParkinglotStatus.valueOf(resultSet.getString(3)), Float.parseFloat(resultSet.getString(4)),
			Float.parseFloat(resultSet.getString(5)));
		
		parkinglots.add(parkinglot);
	    }
	    
	    serverResponse = new ServerResponse<>(RequestResult.Succeed, parkinglots,
		    "Found " + parkinglots.size() + " parkinglots.");
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to get all parkinglots: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting parkinglots.", e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get parkinglots");
	}
    }
    
    private ServerResponse<ArrayList<Complaint>> GetAllActiveComplaints()
    {
	CPS_Tracer.TraceInformation("Trying to get all active complaints.");
	
	try (PreparedStatement preparedStatement = mySqlConnection
		.prepareStatement("SELECT * FROM Complaints WHERE status = ?"))
	{
	    ServerResponse<ArrayList<Complaint>> serverResponse;
	    
	    ArrayList<Complaint> complaints = new ArrayList<>();
	    
	    preparedStatement.setString(1, ComplaintStatus.Active.toString());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while (resultSet.next())
	    {
		Complaint complaint = new Complaint(resultSet.getString(1), resultSet.getString(2),
			LocalDateTime.parse(resultSet.getString(3)), resultSet.getString(4),
			ComplaintStatus.valueOf(resultSet.getString(5)), Float.parseFloat(resultSet.getString(6)));
		
		complaints.add(complaint);
		
	    }
	    
	    serverResponse = new ServerResponse<>(RequestResult.Succeed, complaints,
		    "Found " + complaints.size() + " complaints.");
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to get all active complaints: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Throwable e)
	{
	    CPS_Tracer.TraceError("Failed in getting all active complaints.", e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get complaints");
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
	    
	    CPS_Tracer.TraceInformation("Server response to client after trying to get customer: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting customer.\nSubscription Id: " + customerId, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get customer");
	}
    }
    
    private ServerResponse<Reservation> GetReservation(String orderId)
    {
	CPS_Tracer.TraceInformation("Get Reservation with Id:" + orderId);
	
	try (PreparedStatement preparedStatement = mySqlConnection
		.prepareStatement("SELECT * FROM Reservations WHERE orderId = ?"))
	{
	    ServerResponse<Reservation> serverResponse;
	    
	    Reservation reservation;
	    
	    preparedStatement.setString(1, orderId);
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.NotFound, null, orderId + "Not Found");
	    }
	    else
	    {
		resultSet.next();
		
		reservation = new Reservation(ReservationType.valueOf(resultSet.getString(2)), resultSet.getString(3),
			resultSet.getString(4), resultSet.getString(5), LocalDate.parse(resultSet.getString(6)),
			LocalDate.parse(resultSet.getString(7)), LocalTime.parse(resultSet.getString(8)),
			LocalTime.parse(resultSet.getString(9)), ReservationStatus.valueOf(resultSet.getString(10)));
		
		reservation.setOrderId(resultSet.getString(1));
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, reservation, "Found");
	    }
	    
	    CPS_Tracer
		    .TraceInformation("Server response to client after trying to get reservation: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting reservation.\nReservation Id: " + orderId, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get Reservation ");
	}
    }
    
    private ServerResponse<Employee> GetEmployee(LoginIdentification loginIdentification)
    {
	CPS_Tracer.TraceInformation("Trying to get Employee with username:" + loginIdentification.getUsername());
	
	try (PreparedStatement preparedStatement = mySqlConnection
		.prepareStatement("SELECT * FROM Employees WHERE username = ?"))
	{
	    ServerResponse<Employee> serverResponse;
	    
	    preparedStatement.setString(1, loginIdentification.getUsername());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.WrongCredentials, null, "Not Found username");
	    }
	    else
	    {
		resultSet.next();
		
		if (!resultSet.getString(7).equals(loginIdentification.getPassword()))
		{
		    serverResponse = new ServerResponse<>(RequestResult.WrongCredentials, null, "Wrong password");
		}
		else
		{
		    Employee employee = new Employee(resultSet.getString(1), resultSet.getString(2),
			    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
			    resultSet.getString(6), resultSet.getString(7),
			    EmployeeType.valueOf(resultSet.getString(8)));
		    
		    serverResponse = new ServerResponse<>(RequestResult.Succeed, employee, "Found");
		}
	    }
	    
	    CPS_Tracer.TraceInformation("Server response to client after trying to get employee: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting emlpoyee.\nCredentials: " + loginIdentification, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get emlpoyee ");
	}
    }
    
    private ServerResponse<Parkinglot> GetParkinglot(String parkinglotName)
    {
	CPS_Tracer.TraceInformation("Trying to get Parkinglot: " + parkinglotName);
	
	try (PreparedStatement preparedStatement = mySqlConnection.prepareStatement(
		"SELECT * FROM ParkingLots WHERE parkinglotName = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
		ResultSet.CONCUR_UPDATABLE))
	{
	    ServerResponse<Parkinglot> serverResponse;
	    
	    preparedStatement.setString(1, parkinglotName);
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.NotFound, null, "parkinglot Not Found");
	    }
	    else
	    {
		resultSet.next();
		
		Parkinglot parkinglot = new Parkinglot(resultSet.getString(1), Integer.parseInt(resultSet.getString(2)),
			ParkinglotStatus.valueOf(resultSet.getString(3)), Float.parseFloat(resultSet.getString(4)),
			Float.parseFloat(resultSet.getString(5)));
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, parkinglot, "Found");
	    }
	    
	    CPS_Tracer
		    .TraceInformation("Server response to client after trying to get parkinglot: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting parkinglot: " + parkinglotName, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get parkinglot ");
	}
    }
    
    private ServerResponse<ChangeParkinglotStatusRequest> ChangeParkinglotStatus(
	    ChangeParkinglotStatusRequest changeParkinglotStatusRequest)
    {
	CPS_Tracer.TraceInformation("Trying to change parkinglot's status: \n" + changeParkinglotStatusRequest);
	
	try (PreparedStatement preparedStatement = mySqlConnection.prepareStatement(
		"SELECT * FROM ParkingLots WHERE parkinglotName = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
		ResultSet.CONCUR_UPDATABLE))
	{
	    ServerResponse<ChangeParkinglotStatusRequest> serverResponse;
	    
	    preparedStatement.setString(1, changeParkinglotStatusRequest.getParkinglotName());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (!resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.NotFound, null, "parkinglot Not Found");
	    }
	    else
	    {
		resultSet.next();
		
		resultSet.updateString(3, changeParkinglotStatusRequest.getParkinglotStatus().toString());
		
		resultSet.updateRow();
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, changeParkinglotStatusRequest, "Updated");
	    }
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to change parkinglot status: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in changing parkinglot status: \n" + changeParkinglotStatusRequest, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed changing parkinglot status ");
	}
    }
    
    private ServerResponse<ChangeParkingSpotStatusRequest> ChangeParkingSpotStatus(
	    ChangeParkingSpotStatusRequest changeParkingSpotStatusRequest)
    {
	CPS_Tracer.TraceInformation("Trying to change parking spot status: \n" + changeParkingSpotStatusRequest);
	
	try
	{
	    ServerResponse<ChangeParkingSpotStatusRequest> serverResponse;
	    
	    if (changeParkingSpotStatusRequest.getParkingSpotStatus().equals(ParkingSpotStatus.Active))
	    {
		PreparedStatement preparedStatement = mySqlConnection.prepareStatement(
			"SELECT * FROM DisabledParkingSpots WHERE parkinglotName = ? AND parkingSpot = ? AND status = ?",
			ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		preparedStatement.setString(1, changeParkingSpotStatusRequest.getParkinglotName());
		preparedStatement.setString(2, changeParkingSpotStatusRequest.getParkingSpot().toString());
		preparedStatement.setString(3, ParkingSpotStatus.Disabled.toString());
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (!resultSet.isBeforeFirst())
		{
		    serverResponse = new ServerResponse<>(RequestResult.NotFound, changeParkingSpotStatusRequest,
			    "disabled parking spot Not Found");
		}
		else
		{
		    resultSet.next();
		    
		    resultSet.updateString(6, ParkingSpotStatus.Active.toString());
		    resultSet.updateString(5, LocalDateTime.now().toString());
		    
		    resultSet.updateRow();
		    
		    serverResponse = new ServerResponse<>(RequestResult.Succeed, changeParkingSpotStatusRequest,
			    "parking spot Actived.");
		}
	    }
	    else // Disable spot
	    {
		PreparedStatement preparedStatement = mySqlConnection.prepareStatement(
			"SELECT * FROM DisabledParkingSpots WHERE parkinglotName = ? AND parkingSpot = ? AND status = ?",
			ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		preparedStatement.setString(1, changeParkingSpotStatusRequest.getParkinglotName());
		preparedStatement.setString(2, changeParkingSpotStatusRequest.getParkingSpot().toString());
		preparedStatement.setString(3, ParkingSpotStatus.Disabled.toString());
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.isBeforeFirst()) // case parking spot is already
					       // disabled
		{
		    serverResponse = new ServerResponse<>(RequestResult.NotFound, changeParkingSpotStatusRequest,
			    "parking spot already disabled");
		}
		else
		{
		    String preparedStatementString = "INSERT INTO DisabledParkingSpots(uniqueCol, parkinglotName, parkingSpot, startDateTime, endDateTime, status) VALUES(?, ?, ?, ?, ?, ?)";
		    
		    ArrayList<String> values = new ArrayList<>();
		    
		    String uniqueId = GenerateUniqueId("SELECT uniqueCol FROM DisabledParkingSpots WHERE uniqueCol = ?",
			    5000000);
		    
		    values.add(uniqueId);
		    values.add(changeParkingSpotStatusRequest.getParkinglotName());
		    values.add(changeParkingSpotStatusRequest.getParkingSpot().toString());
		    values.add(LocalDateTime.now().toString());
		    values.add(null);
		    values.add(ParkingSpotStatus.Disabled.toString());
		    
		    AddRowToTable(preparedStatementString, values);
		    
		    serverResponse = new ServerResponse<>(RequestResult.Succeed, changeParkingSpotStatusRequest,
			    "parking spot Disabled.");
		}
	    }
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to change parking spot status: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in changing parking spot status: \n" + changeParkingSpotStatusRequest, e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to change parking spot status ");
	}
    }
    
    private ServerResponse<ArrayList<ParkingSpot>> GetAllDisabledParkingSpots()
    {
	CPS_Tracer.TraceInformation("Trying to get all disabled parking spots.");
	
	try (PreparedStatement preparedStatement = mySqlConnection
		.prepareStatement("SELECT * FROM DisabledParkingSpots WHERE status = ?"))
	{
	    ServerResponse<ArrayList<ParkingSpot>> serverResponse;
	    
	    ArrayList<ParkingSpot> parkingSpots = new ArrayList<>();
	    
	    preparedStatement.setString(1, "Disabled");
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while (resultSet.next())
	    {
		ParkingSpot parkingSpot = ParkingSpot.Parse(resultSet.getString(3));
		
		parkingSpots.add(parkingSpot);
	    }
	    
	    serverResponse = new ServerResponse<>(RequestResult.Succeed, parkingSpots,
		    "Found " + parkingSpots.size() + " parking spots.");
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to get disabled parking spots: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    CPS_Tracer.TraceError("Failed in getting disabled parking spots.", e);
	    
	    return new ServerResponse<>(RequestResult.Failed, null, "Failed to get disabled parking spots");
	}
    }
    
    private ServerResponse<AddRealTimeParkingRequest> TryInsertGuestCar(AddRealTimeParkingRequest request)
    {
	CPS_Tracer.TraceInformation("Trying to insert guest car: \n" + request);
	
	try
	{
	    ServerResponse<AddRealTimeParkingRequest> serverResponse;
	    
	    // Check if the car is not in a parking lot already:
	    
	    String preparedStatementString = "SELECT * FROM RealTimeParking WHERE carNumber = ?";
	    
	    PreparedStatement preparedStatement = mySqlConnection.prepareStatement(preparedStatementString);
	    
	    preparedStatement.setString(1, request.getCarNumber());
	    
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (resultSet.isBeforeFirst())
	    {
		serverResponse = new ServerResponse<>(RequestResult.AlredyExist, request, null);
	    }
	    // Check if there is a free parking spot:
	    else if (!IsParkingSpotFree(request.getParkinglot(), request.getStartDateTime(), request.getExiDateTime()))
	    {
		serverResponse = new ServerResponse<>(RequestResult.ResourceNotAvaillable, request, "Parkinglot full");
	    }
	    else
	    {
		preparedStatementString = "INSERT INTO RealTimeParking(parkingLot, parkingSpot, startDateTime, exitDateTime, carNumber) VALUES(?, ?, ?, ?, ?)";
		
		ArrayList<String> values = new ArrayList<>();
		
		values.add(request.getParkinglot());
		values.add(new ParkingSpot(1, 1, 1).toString());
		values.add(request.getStartDateTime().toString());
		values.add(request.getExiDateTime().toString());
		values.add(request.getCarNumber());
		
		AddRowToTable(preparedStatementString, values);
		
		serverResponse = new ServerResponse<>(RequestResult.Succeed, request, null);
	    }
	    
	    CPS_Tracer.TraceInformation(
		    "Server response to client after trying to insert guest car: \n" + serverResponse);
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    ServerResponse<AddRealTimeParkingRequest> serverResponse = new ServerResponse<>(RequestResult.Failed,
		    request, "Failed to insert guest car");
	    
	    CPS_Tracer.TraceError("Failed to insert guest car", e);
	    
	    return serverResponse;
	}
    }
}
