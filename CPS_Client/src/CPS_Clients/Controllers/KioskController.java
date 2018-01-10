package CPS_Clients.Controllers;

import java.util.ArrayList;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class KioskController extends BaseController {
	private ArrayList<String> subscriptionTypes = new ArrayList<>();

	public KioskController() {
		subscriptionTypes.add(Consts.FullMembership);
		subscriptionTypes.add(Consts.PartialMembership);
	}
	
    @FXML
    private Label Headline;
    
	@FXML
	void OnEnter(ActionEvent event) {
		myControllersManager.SetScene(Consts.KioskEntry, Consts.Kiosk);
	}

	@FXML
	void OnExit(ActionEvent event) {
		myControllersManager.SetScene(Consts.KioskExit, Consts.Kiosk);
	}

	@FXML
	void OnRegister(ActionEvent event) {
		String buttonResult = DialogBuilder.AlertDialog(AlertType.NONE, "Register", "Please choose a subscription type",
				subscriptionTypes, true);

		switch (buttonResult) {
		case Consts.FullMembership:
			myControllersManager.SetScene(Consts.FullMembershipRegister, Consts.Kiosk);
			break;

		case Consts.PartialMembership:
			myControllersManager.SetScene(Consts.PartialMembershipRegister, Consts.Kiosk);
			break;

		default:
			break;
		}
	}

	@FXML
	void OnMonitorAndControll(ActionEvent event) throws InterruptedException {

		myControllersManager.SetScene(Consts.MonitorAndControll, Consts.Kiosk);
	}
}
