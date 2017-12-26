package CPS_Clients.Controllers.Employee;

import CPS_Clients.ConstsEmployees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CustomerServiceEnteryController  extends EmployeeBaseController{

    @FXML
    void OnManageComplaints(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ManageRequestRateChange, ConstsEmployees.CustomerServiceEntery);
    }

    @FXML
    void OnSaveParkingSpot(ActionEvent event) 
    {
    	
    }
    @FXML
    void OnBack(ActionEvent event) 
    {
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ParkingLotWorkerEntery );
    }
}
