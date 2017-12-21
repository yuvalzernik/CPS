package CPS_Clients.Controllers;

import java.time.LocalDate;
import java.util.function.Consumer;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
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
    //TODO take from DB update list of parkinglot.
    
    @FXML
    private TextField exitHour;
    
    @FXML
    private ListView<String> carListView;
    
    private ObservableList<String> cars = FXCollections.observableArrayList();
    
    @FXML
    private TextField id;
    
    @FXML
    private DatePicker startingDatePicker;
    
    @FXML
    private TextField email;
    
    @FXML
    void initialize() 
    {
	carListView.setEditable(true);
	startingDatePicker.setEditable(true); 
	startingDatePicker.setValue(LocalDate.now());
    }
    
    @FXML
    void OnBack(ActionEvent event)
    {
	myControllersManager.Back(PreviousScene,Consts.PartialMembershipRegister);
	
	carListView.setItems(null);
	cars.clear();
    }
    
    @FXML
    void OnSubmitAndPay(ActionEvent event)
    {
	// Todo : calc amount to pay.
	
	float paymentAmount = 100;
	
	Consumer<Void> afterPayment = Void ->
	{
	    DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.ThankYouForRegistering, null,
		    false);
	    
	    myControllersManager.GoToHomePage(Consts.Payment);
	};
	
	myControllersManager.Payment("test", paymentAmount, afterPayment, Consts.PartialMembershipRegister);
	
	carListView.setItems(null);
	cars.clear();
    }
    
    @FXML
    void OnAddCar(ActionEvent event)
    {
	cars.add(carNumber.getText());
	
	carNumber.clear();
	
	carListView.setItems(cars);
    }
}
