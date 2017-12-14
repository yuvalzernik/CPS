package application.Controllers;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import javafx.event.ActionEvent;

//package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class GuestEntryController extends BaseController
{
    
    @FXML
    private TextField carNumber;
    
    @FXML
    private TextField departureTime;
    
    @FXML
    private TextField id;
    
    @FXML
    private TextField email;
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene);
    }
    
    @FXML
    void OnSubmit(ActionEvent event)
    {
	DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.LeaveTheCarMessage, null, false);
	
	myControllersManager.SetScene(Consts.Kiosk, null);
    }
    
}
