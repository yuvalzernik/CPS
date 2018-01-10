package CPS_Clients.Controllers;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MonitorAndControlCustomerController extends BaseController
{
    
    @FXML
    private Label Headline;
    
    @FXML 
    private TextArea textBox;
    
    @FXML 
    private TextField customerID;
    
    @FXML
    void OnSubmit(ActionEvent event)
    {
	if (!InputValidator.Id(customerID.getText()))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
	    
	    return;
	}
	
	ServerResponse<Customer> serverResponse = RequestsSender.GetCustomer(customerID.getText());
	
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
	}
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene, Consts.MonitorAndControlCustomer);
    }
}
