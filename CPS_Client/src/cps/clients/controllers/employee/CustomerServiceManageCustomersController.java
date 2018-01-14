package cps.clients.controllers.employee;

import java.util.concurrent.CompletableFuture;

import cps.clientServer.RequestResult;
import cps.clientServer.RequestsSender;
import cps.clientServer.ServerResponse;
import cps.entities.CreditCustomerRequest;
import cps.entities.Customer;
import cps.utilities.Consts;
import cps.utilities.ConstsEmployees;
import cps.utilities.DialogBuilder;
import cps.utilities.InputValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerServiceManageCustomersController.
 */
public class CustomerServiceManageCustomersController extends EmployeeBaseController
{
    
    /** The Headline. */
    @FXML
    private Label Headline;
    
    /** The text box. */
    @FXML
    private TextArea textBox;
    
    /** The customer ID. */
    @FXML
    private TextField customerID;
    
    /** The refund. */
    @FXML
    private Button refund;
    
    /** The prg bar. */
    @FXML
    private ProgressBar prgBar;
    
    /** The customer. */
    private Customer customer;
    
    /**
     * On submit.
     *
     * @param event the event
     */
    @FXML
    void OnSubmit(ActionEvent event)
    {
	if (!InputValidator.Id(customerID.getText()))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
	    
	    return;
	}
	
	prgBar.setVisible(true);
	
	CompletableFuture.runAsync(() ->
	{
	    ServerResponse<Customer> serverResponse = RequestsSender.GetCustomer(customerID.getText());
	    
	    Platform.runLater(() ->
	    {
		prgBar.setVisible(false);
		
		if (serverResponse.GetRequestResult().equals(RequestResult.Failed))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		    return;
		}
		
		if (serverResponse.GetRequestResult().equals(RequestResult.NotFound))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, "Customer ID was not found.", null, false);
		    return;
		}
		
		if (serverResponse.GetRequestResult().equals(RequestResult.Succeed))
		{
		    textBox.setText(serverResponse.GetResponseObject().toString());
		    customer = serverResponse.GetResponseObject();
		    if (customer.GetBalance() > 0) refund.setDisable(false);
		}
	    });
	    
	});
	
    }
    
    /**
     * On back.
     *
     * @param event the event
     */
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, ConstsEmployees.ManageCustomer);
    }
    
    /**
     * On refund.
     *
     * @param event the event
     */
    @FXML
    void OnRefund(ActionEvent event)
    {
	CreditCustomerRequest request = new CreditCustomerRequest(customer.GetId(), -customer.GetBalance());
	
	prgBar.setVisible(true);
	
	CompletableFuture.runAsync(() ->
	{
	    ServerResponse<CreditCustomerRequest> serverResponse = RequestsSender.CreditCustomer(request);
	    
	    Platform.runLater(() ->
	    {
		prgBar.setVisible(false);
		
		if (serverResponse.GetRequestResult().equals(RequestResult.Succeed))
		{
		    DialogBuilder.AlertDialog(AlertType.INFORMATION, null, "Refund was successful.", null, false);
		    refund.setDisable(true);
		}
		else
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		    refund.setDisable(true);
		}
	    });
	    
	});
	
    }
}
