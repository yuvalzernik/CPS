package CPS_Clients.Controllers;

import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import entities.Complaint;

public class ComplaintsController extends BaseController {

	@FXML // fx:id="complaintDeatils"
	private TextArea complaintDeatils; // Value injected by FXMLLoader
	
    @FXML
    private Label Headline;
    
	@FXML // fx:id="memberID"
	private TextField memberID; // Value injected by FXMLLoader

	Complaint complaint;

	@FXML
	void OnSubmit(ActionEvent event) {

		String result = DialogBuilder.AlertDialog(AlertType.CONFIRMATION, Consts.Approved, ConstsWeb.SumbitComplaint,
				null, false);

		if (result.equals("OK")) {
			complaint = new Complaint(memberID.getText(), complaintDeatils.getText());

			if (!InputValidator.Id(memberID.getText())) {
				DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);

				return;
			}
			if(!InputValidator.TextIsEmpty(complaintDeatils.getText())) {
				DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);

				return;
				
			}
			ServerResponse<Complaint> ComplaintResponse = RequestsSender.AddComplaint(complaint);

			if (ComplaintResponse.GetRequestResult().equals(RequestResult.Failed)) {
				DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
				return;
			}
			if (ComplaintResponse.GetRequestResult().equals(RequestResult.NotFound)) {
				DialogBuilder.AlertDialog(AlertType.ERROR, null, "Sorry, your ID was not found\n", null, false);
				return;
			}
			DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.ComplaintRegistered, null, false);

			myControllersManager.SetScene(ConstsWeb.Web, null);
		}
	}

	@FXML
	void OnBack(ActionEvent event) {
		myControllersManager.Back(PreviousScene, ConstsWeb.Complaints);
	}

}
