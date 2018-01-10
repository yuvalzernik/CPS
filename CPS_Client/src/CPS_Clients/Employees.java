package CPS_Clients;

import java.net.URL;
import java.util.LinkedList;

import CPS_Clients.Controllers.ControllersManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Employees extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
	try
	{
	    LinkedList<Pair<String, URL>> list = new LinkedList<>();
	    
	    Class<?> myClass = getClass();
	    
	    list.add(new Pair<String, URL>(ConstsEmployees.EmployeesLogin, myClass.getResource(ConstsEmployees.EmployeesLoginFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ParkingLotWorkerEntery, myClass.getResource(ConstsEmployees.ParkingLotWorkerEnteryFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ManagerLogin, myClass.getResource(ConstsEmployees.ManagerLoginFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.CustomerServiceEntery, myClass.getResource(ConstsEmployees.CustomerServiceEnteryFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.CeoLogin, myClass.getResource(ConstsEmployees.CeoLoginFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ManageRequestRateChange, myClass.getResource(ConstsEmployees.ManageRequestRateChangeFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ReserveParkingSpot, myClass.getResource(ConstsEmployees.ReserveParkingSpotFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ReserveParkingSpotInLocalParkingLot, myClass.getResource(ConstsEmployees.ReserveParkingSpotInLocalParkingLotFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ManageComplaints, myClass.getResource(ConstsEmployees.ManageComplaintsFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ProduceReport, myClass.getResource(ConstsEmployees.ProduceReportFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ReservationReport, myClass.getResource(ConstsEmployees.ReservationReportFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ActivityReport, myClass.getResource(ConstsEmployees.ActivityReportFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.ManagerProduceReport, myClass.getResource(ConstsEmployees.ManagerProduceReportFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsEmployees.StatusReport, myClass.getResource(ConstsEmployees.StatusReportControllerFxmlPath)));

	    
	    ControllersManager controllersManager = new ControllersManager(list, primaryStage,ConstsEmployees.EmployeesLogin);
	    
	    controllersManager.GoToHomePage(null);
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    throw e;
	}
    }
    
    public static void main(String[] args)
    {
	launch(args);
    }
}
