package CPS_Clients.Controllers.Employee;

import java.util.List;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class RateChangeController extends EmployeeBaseController {
	List<String> buttonNames;
    @FXML
    private TableColumn<?, ?> newInAdvancePrice;

    @FXML
    private TableView<?> MyTable;

    @FXML
    private TableColumn<?, ?> newGuestPrice;

    @FXML
    private TableColumn<?, ?> parkingLotName;
    RateChangeController()
    {
    	buttonNames.add("compensate and close");
    	buttonNames.add("close without compensating");

    }
    @FXML
    void OnSelectRow(ActionEvent event) 
    {
		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.RequestSent, buttonNames,true);	
		
    }

}
