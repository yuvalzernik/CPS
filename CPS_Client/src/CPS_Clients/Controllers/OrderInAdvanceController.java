package CPS_Clients.Controllers;

import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class OrderInAdvanceController extends BaseController {

    @FXML
    void OnPayment(ActionEvent event) {
    	myControllersManager.SetScene(Consts.Payment, ConstsWeb.OrderInAdvance);
    	
    }

    @FXML
    void OnBack(ActionEvent event) {
    	myControllersManager.Back(PreviousScene,ConstsWeb.OrderInAdvance);
    }

}
