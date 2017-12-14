/**
 * Sample Skeleton for 'Complaints.fxml' Controller Class
 */

package application.Controllers;

import application.Consts;
import application.ConstsWeb;
import application.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ComplaintsController extends BaseController {

	@FXML // fx:id="memberNumber"
	private TextField memberNumber; // Value injected by FXMLLoader

	@FXML // fx:id="complaintDeatils"
	private TextArea complaintDeatils; // Value injected by FXMLLoader

	@FXML // fx:id="memberID"
	private TextField memberID; // Value injected by FXMLLoader

	@FXML
	void OnSubmit(ActionEvent event) {

		String result = DialogBuilder.AlertDialog(AlertType.CONFIRMATION, Consts.Approved, ConstsWeb.SumbitComplaint,
				null, false);

		if (result.equals("OK")) {
			myControllersManager.SetScene(ConstsWeb.Web, null);
		}
	}

	@FXML
	void OnBack(ActionEvent event) {
		myControllersManager.Back(PreviousScene);
	}

}
