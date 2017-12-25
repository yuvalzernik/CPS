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
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.FullMembership;
import entities.PartialMembership;

public class MonitorAndControllMemberController extends BaseController {

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
		if (!InputValidator.OrderId(Subscription_ID.getText())) {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);

			return;
		}
		ServerResponse<FullMembership> monitorAndControllfullmembership = RequestsSender
				.GetFullMembership(Subscription_ID.getText());
		ServerResponse<PartialMembership> monitorAndControllPartialMember = null;
		if (monitorAndControllfullmembership.GetRequestResult().equals(RequestResult.Failed)) {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
			return;
		}
		if (monitorAndControllfullmembership.GetRequestResult().equals(RequestResult.NotFound)) {
			monitorAndControllPartialMember = RequestsSender.GetPartialMembership(Subscription_ID.getText());

			if (monitorAndControllPartialMember.GetRequestResult().equals(RequestResult.Failed)) {
				DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
				return;
			}
			if (monitorAndControllPartialMember.GetRequestResult().equals(RequestResult.NotFound)) {

				DialogBuilder.AlertDialog(AlertType.ERROR, null, "Sorry, your reservation was not found", null, false);
				return;
			}
		}
		if (monitorAndControllfullmembership.GetRequestResult().equals(RequestResult.Succeed))
			TextMemberDeatil.setText(monitorAndControllfullmembership.GetResponseObject().toString());
		else if (monitorAndControllPartialMember.GetRequestResult().equals(RequestResult.Succeed))
			TextMemberDeatil.setText(monitorAndControllPartialMember.GetResponseObject().toString());
		SubscriptionRenewal.setDisable(false);
	}

	@FXML
	void OnSubscriptionRenewal(ActionEvent event) {
		String newDate = LocalDate.now().plusDays(28).toString();
		newDate = "The expiration of the new subscription is: " + newDate;
		String buttonResult = DialogBuilder.AlertDialog(AlertType.NONE, "Renewal Subscription", newDate,
				subscriptionTypes, true);

		if (buttonResult == Consts.Payment) {
			SubscriptionRenewal.setDisable(true);
			myControllersManager.SetScene(Consts.Payment, Consts.MonitorAndControllMember);
		}

	}

	@FXML
	void OnBack(ActionEvent event) {
		SubscriptionRenewal.setDisable(true);
		myControllersManager.Back(PreviousScene, Consts.MonitorAndControllMember);
	}

}
