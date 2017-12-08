package application.Controllers;

import java.util.ArrayList;

import application.Consts;
import application.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;

public class KioskController extends BaseController
{   
    private ArrayList<String> subscriptionTypes = new ArrayList<>();
    
    public KioskController()
    {
	subscriptionTypes.add(Consts.FullMembership);
	subscriptionTypes.add(Consts.PartialMembership);
    }
    
    @FXML
    void OnEnter(ActionEvent event)
    {
	myControllersManager.SetScene(Consts.KioskEntry, Consts.Kiosk);
    }
    
    @FXML
    void OnExit(ActionEvent event)
    {
	myControllersManager.SetScene(Consts.KioskExit, Consts.Kiosk);
    }
    
    @FXML
    void OnRegister(ActionEvent event)
    {
	String buttonResult = DialogBuilder.AlertDialog(AlertType.NONE, "Register", "Please choose subscription type", subscriptionTypes, true);

	switch (buttonResult)
	{
	case Consts.FullMembership:
	     myControllersManager.SetScene(Consts.FullMembershipRegister, Consts.Kiosk);
	    break;
	    
	case Consts.PartialMembership:
	    myControllersManager.SetScene(Consts.PartialMembershipRegister, Consts.Kiosk);
	    break;
	    
	default:
	    break;
	}
    }
    
    @FXML
    void OnMonitorAndControll(ActionEvent event)
    {
	myControllersManager.SetScene(Consts.MonitorAndControll, Consts.Kiosk);
    }
}
