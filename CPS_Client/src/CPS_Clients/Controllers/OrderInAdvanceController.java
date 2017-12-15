package CPS_Clients.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class OrderInAdvanceController extends BaseController {

    @FXML
    void OnPayment(ActionEvent event) {
    	
    }

    @FXML
    void OnBack(ActionEvent event) {
    	myControllersManager.Back(PreviousScene);
    }

}
