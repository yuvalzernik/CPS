package CPS_Clients.Controllers.Employee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.LoginIdentification;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Employee;
import entities.enums.EmployeeType;
import entities.enums.LogedStatus;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class EmployeesLoginController extends EmployeeBaseController
{
    
    @FXML
    private PasswordField password;
    
    @FXML
    private TextField userName;
    
    @FXML
    private Button login;
    
    @FXML
    private ProgressIndicator prgBar;
    
    @FXML
    void OnLogin(ActionEvent event) throws InterruptedException, ExecutionException
    {
	if (password.getText().equals("") || userName.getText().equals(""))
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, ConstsEmployees.OneFieldOrMoreIsEmpty, null, false);
	    return;
	}
	
	Platform.runLater(() -> prgBar.setVisible(true));
	
	CompletableFuture.runAsync(() ->
	{
	    LoginIdentification loginIdentification = new LoginIdentification(userName.getText(), password.getText());
	    ServerResponse<Employee> employeeRes = RequestsSender.GetEmployee(loginIdentification);
	    
	    Platform.runLater(() ->
	    {
		Platform.runLater(() -> prgBar.setVisible(false));
		
		if (employeeRes.GetRequestResult().equals(RequestResult.WrongCredentials))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, ConstsEmployees.IncorrectUsernameOrPassword, null,
			    false);
		    return;
		}
		
		if (employeeRes.GetRequestResult().equals(RequestResult.Failed))
		{
		    DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
		    return;
		}
		
		if (employeeRes.GetRequestResult().equals(RequestResult.Succeed))
		{
		    if (employeeRes.GetResponseObject().getLogedStatus().equals(LogedStatus.LogedIn))
		    {
			DialogBuilder.AlertDialog(AlertType.ERROR, null,
				"You are already logged in.\nPlease log out first.", null, false);
			return;
		    }
		    
		    MyEmployee = employeeRes.GetResponseObject();
		    
		    if (!RequestsSender.LoginUser(MyEmployee.getUsername()).GetRequestResult()
			    .equals(RequestResult.Succeed))
		    {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
			return;
		    }
		    
		    if (MyEmployee.getEmployeeType().equals(EmployeeType.CustomerService))
			myControllersManager.SetScene(ConstsEmployees.CustomerServiceEntery,
				ConstsEmployees.EmployeesLogin);
		    else if (MyEmployee.getEmployeeType().equals(EmployeeType.CEO))
			myControllersManager.SetScene(ConstsEmployees.CeoLogin, ConstsEmployees.EmployeesLogin);
		    else if (MyEmployee.getEmployeeType().equals(EmployeeType.Parkinglot))
			myControllersManager.SetScene(ConstsEmployees.ParkingLotWorkerEntery,
				ConstsEmployees.EmployeesLogin);
		    else if (MyEmployee.getEmployeeType().equals(EmployeeType.ParkinglotManager))
			myControllersManager.SetScene(ConstsEmployees.ManagerLogin, ConstsEmployees.EmployeesLogin);
		    
		}
	    });
	});
	
    }
    
}
