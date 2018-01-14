package cps.clients.controllers;

import java.util.ArrayList;

import cps.utilities.Consts;
import cps.utilities.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class KioskController.
 */
public class KioskController extends BaseController {
	
	/** The subscription types. */
	private ArrayList<String> subscriptionTypes = new ArrayList<>();

	/**
	 * Instantiates a new kiosk controller.
	 */
	public KioskController() {
		subscriptionTypes.add(Consts.FullMembership);
		subscriptionTypes.add(Consts.PartialMembership);
	}
	
    /** The Headline. */
    @FXML
    private Label Headline;
    
	/**
	 * Client clicks on enter button.
	 *
	 * @param event the event
	 */
	@FXML
	void OnEnter(ActionEvent event) {
		myControllersManager.SetScene(Consts.KioskEntry, Consts.Kiosk);
	}

	/**
	 *  Client clicks on exit button.
	 *
	 * @param event the event
	 */
	@FXML
	void OnExit(ActionEvent event) {
		myControllersManager.SetScene(Consts.KioskExit, Consts.Kiosk);
	}

	/**
	 * Client clicks on register button .
	 *
	 * @param event the event
	 */
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

	/**
	 * Client clicks on monitor and control button.
	 *
	 * @param event the event
	 * @throws InterruptedException the interrupted exception
	 */
	@FXML
	void OnMonitorAndControll(ActionEvent event) throws InterruptedException {

		myControllersManager.SetScene(Consts.MonitorAndControll, Consts.Kiosk);
	}
}
