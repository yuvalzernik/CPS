package CPS_Clients.Controllers;
import java.util.ArrayList;

import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class WebController extends BaseController {
	
	private ArrayList<String> subscriptionTypes = new ArrayList<>();
	public WebController()
	{
		subscriptionTypes.add(Consts.FullMembership);
		subscriptionTypes.add(Consts.PartialMembership);
	}

    @FXML
    private Label Headline;
    
    @FXML
    void OnComplaines(ActionEvent event) {
    	 myControllersManager.SetScene(ConstsWeb.Complaints, ConstsWeb.Web);
    }

    @FXML
    void OnRegister(ActionEvent event) {
    	String buttonResult = DialogBuilder.AlertDialog(AlertType.NONE, "Register", "Please choose a subscription type", subscriptionTypes, true);

    	switch (buttonResult)
    	{
    	case Consts.FullMembership:
    	     myControllersManager.SetScene(Consts.FullMembershipRegister, ConstsWeb.Web);
    	    break;
    	    
    	case Consts.PartialMembership:
    	    myControllersManager.SetScene(Consts.PartialMembershipRegister, ConstsWeb.Web);
    	    break;
    	    
    	default:
    	    break;
    	}

    }

    @FXML
    void OnMonitorAndControll(ActionEvent event) {
    	myControllersManager.SetScene(Consts.MonitorAndControll, ConstsWeb.Web);
    }

    @FXML
    void OnOrderInAdvance(ActionEvent event) {
    	myControllersManager.SetScene(ConstsWeb.OrderInAdvance, ConstsWeb.Web);

    }

}
