package application.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
	
    }
}
