package CPS_Clients.Controllers.Employee;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.LoginIdentification;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Employee;
import entities.enums.EmployeeType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

public class EmployeesLoginController extends EmployeeBaseController {

	@FXML
    private PasswordField password;

    @FXML
    private TextField userName;

    @FXML
    void OnLogin(ActionEvent event) {
    	// todo check

    	if(password.getText().equals("") || userName.getText().equals("")) 
    	{
    		DialogBuilder.AlertDialog(AlertType.ERROR, null, ConstsEmployees.OneFieldOrMoreIsEmpty, null,false);
    	}
  
    	else 
    	{
    	  	LoginIdentification loginIdentification=new LoginIdentification(userName.getText(),password.getText());
    	  	ServerResponse<Employee> employeeRes=RequestsSender.GetEmployee(loginIdentification);
    	  	
    	  	if (employeeRes.GetRequestResult().equals(RequestResult.WrongCredentials))
    			DialogBuilder.AlertDialog(AlertType.ERROR, null, ConstsEmployees.IncorrectUsernameOrPassword, null, false);
    	  	//if (employeeRes.GetRequestResult().equals(RequestResult.Failed )|| employeeRes.GetRequestResult().equals(RequestResult.NotFound)) {
    	  		//DialogBuilder.AlertDialog(AlertType.ERROR, null, ConstsEmployees.ErrorPleaseTryAgainLater, null, false);
			//}
    	  	else 
    	  	{
    	  		MyEmployee=employeeRes.GetResponseObject();
    	  		
    	  		if(MyEmployee.getEmployeeType()==EmployeeType.CustomerService)
    	  			myControllersManager.SetScene(ConstsEmployees.CustomerServiceEntery, ConstsEmployees.EmployeesLogin);
    	  		else if(MyEmployee.getEmployeeType()==EmployeeType.CEO)
    	  			myControllersManager.SetScene(ConstsEmployees.CeoLogin, ConstsEmployees.EmployeesLogin);
    	  		else if(MyEmployee.getEmployeeType()==EmployeeType.Parkinglot)
    	  			myControllersManager.SetScene(ConstsEmployees.ParkingLotWorkerEntery, ConstsEmployees.EmployeesLogin);
    	  		else if(MyEmployee.getEmployeeType()==EmployeeType.ParkinglotManager)
    	  			myControllersManager.SetScene(ConstsEmployees.ManagerLogin, ConstsEmployees.EmployeesLogin);
    	  	}
		}
    	
    }

   
}
