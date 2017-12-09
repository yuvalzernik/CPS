package application.Controllers;

import java.util.function.Consumer;

import application.Consts;
import application.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class FullMembershipRegisterController extends BaseController
{
    @FXML
    private DatePicker startingDate;
    
    @FXML
    private TextField carNumber;
    
    @FXML
    private TextField id;
    
    @FXML
    private TextField email;
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene);
    }
    
    @FXML
    void OnSubmitAndPay(ActionEvent event)
    {
	//Todo : calc amount to pay.
	
	float paymentAmount = 100;
	
	Consumer<Void> afterPayment = Void ->
	{    
	    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.ThankYouForRegistering,
			null, false);		    
	    
	    myControllersManager.SetScene(Consts.Kiosk, null);
	};
	
	myControllersManager.Payment(paymentAmount, afterPayment, Consts.FullMembershipRegister);
    }
}
