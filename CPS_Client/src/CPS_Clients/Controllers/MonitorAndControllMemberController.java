package CPS_Clients.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.time.LocalDate;
import java.util.ArrayList;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;

public class MonitorAndControllMemberController extends BaseController{
	
	private ArrayList<String> subscriptionTypes = new ArrayList<>();
	
	public MonitorAndControllMemberController() {
		subscriptionTypes.add(Consts.Payment);
	}

    @FXML // fx:id="SubscriptionRenewal"
    private Button SubscriptionRenewal; // Value injected by FXMLLoader

    @FXML // fx:id="TextMemberDeatil"
    private TextArea TextMemberDeatil; // Value injected by FXMLLoader

    @FXML // fx:id="Subscription_ID"
    private TextField Subscription_ID; // Value injected by FXMLLoader

    @FXML
    void OnSubmit(ActionEvent event) {
    	//TODO check if member exists in DB and show is details and enable renewalSubscription
    	SubscriptionRenewal.setDisable(false);
    }

    @FXML
    void OnSubscriptionRenewal(ActionEvent event) {
    	String newDate = LocalDate.now().plusDays(28).toString();
    	newDate="The expiration of the new subscription is: "+newDate;
    	String buttonResult = DialogBuilder.AlertDialog(AlertType.NONE, "Renewal Subscription", newDate, subscriptionTypes, true);

    if(buttonResult==Consts.Payment){
    	SubscriptionRenewal.setDisable(true);
    	 myControllersManager.SetScene(Consts.Payment, Consts.MonitorAndControllMember);
    	}

    }

    @FXML
    void OnBack(ActionEvent event) {
    	SubscriptionRenewal.setDisable(true);
    	myControllersManager.Back(PreviousScene,Consts.MonitorAndControllMember);
    }

}
