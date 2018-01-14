/**
 * Sample Skeleton for 'MonitorAndControllNotMember.fxml' Controller Class
 */

package cps.clients.controllers;

import java.util.concurrent.CompletableFuture;

import cps.clientServer.RequestResult;
import cps.clientServer.RequestsSender;
import cps.clientServer.ServerResponse;
import cps.entities.Reservation;
import cps.utilities.Consts;
import cps.utilities.DialogBuilder;
import cps.utilities.InputValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

// TODO: Auto-generated Javadoc
/**
 * The Class MonitorAndControllNotMemberController.
 */
public class MonitorAndControllNotMemberController extends BaseController
{
    
    /** The Headline. */
    @FXML
    private Label Headline;
    
    /** The Order ID. */
    @FXML // fx:id="Order_ID"
    private TextField Order_ID; // Value injected by FXMLLoader
    
    /** The Text order deatiles. */
    @FXML // fx:id="TextOrderDeatiles"
    private TextArea TextOrderDeatiles; // Value injected by FXMLLoader
    
    /** The prg bar. */
    @FXML
    private ProgressBar prgBar;
    
    /**
     * Client clicks on back button.
     *
     * @param event the event
     */
    @FXML
    void OnSubmit(ActionEvent event)
    {
	if (!InputValidator.OrderId(Order_ID.getText()))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
	    
	    return;
	}
	
	prgBar.setVisible(true);
	
	CompletableFuture.runAsync(() ->
	{
	    ServerResponse<Reservation> MonitorAndControllGetOrder = RequestsSender.GetReservation(Order_ID.getText());
	    
	    Platform.runLater(() ->
	    {
		if (MonitorAndControllGetOrder.GetRequestResult().equals(RequestResult.Failed))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		    
		}
		else if (MonitorAndControllGetOrder.GetRequestResult().equals(RequestResult.NotFound))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, "Sorry, your reservation was not found", null,
			    false);
		    
		}
		else
		{
		    TextOrderDeatiles.setText(MonitorAndControllGetOrder.GetResponseObject().toString());		    
		}
		
		prgBar.setVisible(false);
	    });
	});
	
    }
    
    /**
     * Client clicks on back button.
     *
     * @param event the event
     */
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.MonitorAndControllNotMember);
    }
    
}
