package application.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class KioskExitController extends BaseController
{
    @FXML
    void OnMemberExit(ActionEvent event)
    {
	System.out.println("test");
    }
    
    @FXML
    void OnGuestExit(ActionEvent event)
    {
	System.out.println("test");
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene);
    }
}
