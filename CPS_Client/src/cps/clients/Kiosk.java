package cps.clients;

import java.net.URL;
import java.util.LinkedList;

import cps.clients.controllers.ControllersManager;
import cps.utilities.Consts;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Kiosk entry.
 */
public class Kiosk extends Application
{
    
    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
	try
	{
	    LinkedList<Pair<String, URL>> list = new LinkedList<>();
	    
	    Class<?> myClass = getClass();
	    
	    list.add(new Pair<String, URL>(Consts.Kiosk, myClass.getResource(Consts.KioskFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.KioskEntry, myClass.getResource(Consts.KioskEntryFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.MonitorAndControll, myClass.getResource(Consts.MonitorAndControllFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.KioskExit, myClass.getResource(Consts.KioskExitFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.FullMembershipRegister, myClass.getResource(Consts.FullMembershipRegisterFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.PartialMembershipRegister, myClass.getResource(Consts.PartialMembershipRegisterFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.GuestEntry, myClass.getResource(Consts.GuestEntryFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.Payment, myClass.getResource(Consts.PaymentFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.MonitorAndControllNotMember, myClass.getResource(Consts.MonitorAndControllNotMemberFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.MonitorAndControllMember, myClass.getResource(Consts.MonitorAndControllMemberFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.MonitorAndControlCustomer, myClass.getResource(Consts.MonitorAndControlCustomerFxmlPath)));

	    ControllersManager controllersManager = new ControllersManager(list, primaryStage,Consts.Kiosk);
	    
	    controllersManager.GoToHomePage(null);
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    throw e;
	}
    }
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
	launch(args);
    }
}
