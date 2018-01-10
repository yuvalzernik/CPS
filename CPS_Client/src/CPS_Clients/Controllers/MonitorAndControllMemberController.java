package CPS_Clients.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Ellipse;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.imageio.ImageTypeSpecifier;

import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.FullMembership;
import entities.PartialMembership;
import entities.Reservation;

public class MonitorAndControllMemberController extends BaseController {

	private ArrayList<String> subscriptionTypes = new ArrayList<>();
	String fullOrPartialMembership;

	public MonitorAndControllMemberController() {
		subscriptionTypes.add(Consts.Payment);
	}

    @FXML
    private Label Headline;
    
    
	@FXML // fx:id="SubscriptionRenewal"
	private Button SubscriptionRenewal; // Value injected by FXMLLoader

	@FXML // fx:id="TextMemberDeatil"
	private TextArea TextMemberDeatil; // Value injected by FXMLLoader

	@FXML // fx:id="Subscription_ID"
	private TextField Subscription_ID; // Value injected by FXMLLoader
	FullMembership fullMebershipChanged = null;

	PartialMembership parialMebershipChanged = null;

	@FXML
	void OnSubmit(ActionEvent event) {
		if (!InputValidator.OrderId(Subscription_ID.getText())) {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);

			return;
		}
		SetfullOrPartialMembership();

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

				DialogBuilder.AlertDialog(AlertType.ERROR, null, "Sorry, your membership was not found", null, false);
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
		float paymentAmount = 100;
		String newDate = LocalDate.now().plusDays(28).toString();
		newDate = "The expiration of the new subscription is: " + newDate;
		String buttonResult = DialogBuilder.AlertDialog(AlertType.NONE, "Renewal Subscription", newDate,
				subscriptionTypes, true);

		if (buttonResult == Consts.Payment) {
			SubscriptionRenewal.setDisable(true);
			
			if (fullOrPartialMembership.equals("fullMembership")) {
				ServerResponse<FullMembership> getFullMembership = RequestsSender.GetFullMembership(Subscription_ID.getText());
				if (getFullMembership.GetRequestResult().equals(RequestResult.Failed)) {
					DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
					return;
				}
				fullMebershipChanged=getFullMembership.GetResponseObject();
			}
			else if (fullOrPartialMembership.equals("partialMembership")) {
				ServerResponse<PartialMembership> getpartialMembership = RequestsSender.GetPartialMembership(Subscription_ID.getText());
				if (getpartialMembership.GetRequestResult().equals(RequestResult.Failed)) {
					DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
					return;
				}
				parialMebershipChanged=getpartialMembership.GetResponseObject();
			}
			Consumer<Void> afterPayment = Void -> {
				if (fullOrPartialMembership.equals("fullMembership")) {
					ServerResponse<FullMembership> ChangeExpiryDateFull = RequestsSender.ChangeExpireFullMembership(fullMebershipChanged);
					if (ChangeExpiryDateFull.GetRequestResult().equals(RequestResult.Failed)) {
						DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
						return;
					}
				} else if (fullOrPartialMembership.equals("partialMembership")) {
					ServerResponse<PartialMembership> ChangeExpiryDatePartial = RequestsSender.ChangeExpirePartialMembership(parialMebershipChanged);
					if (ChangeExpiryDatePartial.GetRequestResult().equals(RequestResult.Failed)) {
						DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
						return;
					}
				}

				DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.SubscriptionRenewal, null,false);

				myControllersManager.GoToHomePage(Consts.Payment);
			};
			
			
			if (fullOrPartialMembership.equals("fullMembership")) {	
			
				myControllersManager.Payment(fullMebershipChanged, paymentAmount, afterPayment, Consts.MonitorAndControllMember);
			}
			else if (fullOrPartialMembership.equals("partialMembership")) {	
				
				myControllersManager.Payment(parialMebershipChanged, paymentAmount, afterPayment, Consts.MonitorAndControllMember);
			}
		}

	}

	@FXML
	void OnBack(ActionEvent event) {
		SubscriptionRenewal.setDisable(true);
		myControllersManager.Back(PreviousScene, Consts.MonitorAndControllMember);
	}

	void SetfullOrPartialMembership() {
		int savedValue = Integer.parseInt(Subscription_ID.getText());
		if (savedValue >= 1000000 && savedValue < 2000000) {
			this.fullOrPartialMembership = "fullMembership";
			return;
		} else if (savedValue > 2000000 && savedValue < 3000000) {
			this.fullOrPartialMembership = "partialMembership";
			return;

		}
	}

}
