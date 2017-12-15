package CPS_Clients.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MonitorAndControllController extends BaseController
{
    @FXML
    void OnMember(ActionEvent event)
    {
	System.out.println("test");
    }
    
    @FXML
    void OnNotAMember(ActionEvent event)
    {
	System.out.println("test");
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene);
    }
}
