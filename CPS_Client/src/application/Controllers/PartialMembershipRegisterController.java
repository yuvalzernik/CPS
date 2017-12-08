package application.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class PartialMembershipRegisterController extends BaseController
{   
    @FXML
    private TextField carNumber;
    
    @FXML
    private MenuButton parkingLot;
    
    @FXML
    private TextField exitHour;
    
    @FXML
    private ListView<?> carListView;
    
    @FXML
    private TextField id;
    
    @FXML
    private DatePicker startingDate;
    
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
    
    @FXML
    void OnAddCar(ActionEvent event)
    {
	
    }
}
