package CPS_Clients.Controllers.Employee;

import java.util.concurrent.CompletableFuture;

import CPS_Clients.Controllers.BaseController;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import entities.Employee;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;

public class EmployeeBaseController extends BaseController
{
    protected static Employee MyEmployee;
    
    public static Employee getMyEmployee()
    {
	return MyEmployee;
    }
    
    protected void LogOut()
    {
	try
	{
	    CompletableFuture.runAsync(() ->
	    
	    {
		if (!RequestsSender.LogoutUser(MyEmployee.getUsername()).GetRequestResult()
			.equals(RequestResult.Succeed))
		{
		    Platform.runLater(() ->
		    {
			DialogBuilder.AlertDialog(AlertType.ERROR, null,
				"Failed to log out your user, please contact IT", null, false);
		    });
		}
	    });
	}
	catch (Exception e)
	{
	    DialogBuilder.AlertDialog(AlertType.ERROR, null, "Failed to log out your user, please contact IT", null,
		    false);
	}
    }
}
