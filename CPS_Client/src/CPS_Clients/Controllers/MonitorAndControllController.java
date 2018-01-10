package CPS_Clients.Controllers;

import CPS_Utilities.Consts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MonitorAndControllController extends BaseController
{
    @FXML
    private Label Headline;
    
    @FXML
    void OnMember(ActionEvent event)
    {
    	myControllersManager.SetScene(Consts.MonitorAndControllMember, Consts.MonitorAndControll);
    }
    
    @FXML
    void OnNotAMember(ActionEvent event)
    {
    	myControllersManager.SetScene(Consts.MonitorAndControllNotMember, Consts.MonitorAndControll);
    }
    
    @FXML
    void OnCustomerInfo(ActionEvent event)
    {
	myControllersManager.SetScene(Consts.MonitorAndControlCustomer, Consts.MonitorAndControll);
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.MonitorAndControll);
    }
}
