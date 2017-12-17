package CPS_Clients;

import java.net.URL;
import java.util.LinkedList;

import CPS_Clients.Controllers.ControllersManager;
import CPS_Utilities.Consts;
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
