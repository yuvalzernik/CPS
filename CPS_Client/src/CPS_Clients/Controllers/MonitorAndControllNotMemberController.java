/**
 * Sample Skeleton for 'MonitorAndControllNotMember.fxml' Controller Class
 */

package CPS_Clients.Controllers;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class MonitorAndControllNotMemberController extends BaseController {

    @FXML
    private Label Headline;
 
	@FXML // fx:id="Order_ID"
	private TextField Order_ID; // Value injected by FXMLLoader

	@FXML // fx:id="TextOrderDeatiles"
	private TextArea TextOrderDeatiles; // Value injected by FXMLLoader

	@FXML
	void OnSubmit(ActionEvent event) {
		if (!InputValidator.OrderId(Order_ID.getText())) {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);

			return;
		}
		ServerResponse<Reservation> MonitorAndControllGetOrder=RequestsSender.GetReservation(Order_ID.getText());
		if (MonitorAndControllGetOrder.GetRequestResult().equals(RequestResult.Failed)) {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
			return;
		}
		if (MonitorAndControllGetOrder.GetRequestResult().equals(RequestResult.NotFound)) {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, "Sorry, your reservation was not found", null, false);
			return;
		}
		TextOrderDeatiles.setText(MonitorAndControllGetOrder.GetResponseObject().toString());

	}

	@FXML
	void OnBack(ActionEvent event) {
		myControllersManager.Back(PreviousScene, Consts.MonitorAndControllNotMember);
	}

}
