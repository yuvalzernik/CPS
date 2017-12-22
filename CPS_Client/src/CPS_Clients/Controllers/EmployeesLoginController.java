package CPS_Clients.Controllers;

import CPS_Clients.ConstsEmployees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EmployeesLoginController extends BaseController {
   
    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    @FXML
    void OnLogin(ActionEvent event) {
    	// todo check
    	
    	myControllersManager.SetScene(ConstsEmployees.CustomerServiceEntery, ConstsEmployees.EmployeesLogin);
    }

   
}
