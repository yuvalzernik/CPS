package CPS_Clients;

import java.net.URL;
import java.util.LinkedList;

import CPS_Clients.Controllers.ControllersManager;
import CPS_Utilities.Consts;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Web extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
	try
	{
	    LinkedList<Pair<String, URL>> list = new LinkedList<>();
	    
	    Class<?> myClass = getClass();
	     
	    list.add(new Pair<String, URL>(ConstsWeb.Web, myClass.getResource(ConstsWeb.WebFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.MonitorAndControll, myClass.getResource(Consts.MonitorAndControllFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.FullMembershipRegister, myClass.getResource(Consts.FullMembershipRegisterFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.PartialMembershipRegister, myClass.getResource(Consts.PartialMembershipRegisterFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsWeb.Complaints, myClass.getResource(ConstsWeb.ComplaintsFxmlPath)));
	    list.add(new Pair<String, URL>(ConstsWeb.OrderInAdvance, myClass.getResource(ConstsWeb.OrderInAdvanceFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.Payment, myClass.getResource(Consts.PaymentFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.MonitorAndControllNotMember, myClass.getResource(Consts.MonitorAndControllNotMemberFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.MonitorAndControllMember, myClass.getResource(Consts.MonitorAndControllMemberFxmlPath)));


	    ControllersManager controllersManager = new ControllersManager(list, primaryStage,ConstsWeb.Web);
	    
	    controllersManager.GoToHomePage();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public static void main(String[] args)
    {
	launch(args);
    }
}
