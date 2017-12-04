package application;

import java.net.URL;
import java.util.LinkedList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Kiosk extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
	try
	{
	    LinkedList<Pair<String, URL>> list = new LinkedList<>();
	    
	    Class<?> myClass = getClass();
	    
	    list.add(new Pair<String, URL>(Consts.KioskEntry, myClass.getResource(Consts.KioskEntryFxmlPath)));
	    list.add(new Pair<String, URL>(Consts.TestWindow, myClass.getResource(Consts.TestWindowFxmlPath)));

	    ControllersManager controllersManager = new ControllersManager(list, primaryStage);
	    
	    controllersManager.SetScene(Consts.KioskEntry);
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
