package CPS_Clients.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;

public class MonitorAndControllMemberController extends BaseController{

    @FXML // fx:id="TextMemberDeatil"
    private TextArea TextMemberDeatil; // Value injected by FXMLLoader

    @FXML // fx:id="Subscription_ID"
    private TextField Subscription_ID; // Value injected by FXMLLoader

    @FXML
    void OnSubmit(ActionEvent event) {
    	
    }

    @FXML
    void OnSubscriptionRenewal(ActionEvent event) {

    }

    @FXML
    void OnBack(ActionEvent event) {
    	myControllersManager.Back(PreviousScene);
    }

}
