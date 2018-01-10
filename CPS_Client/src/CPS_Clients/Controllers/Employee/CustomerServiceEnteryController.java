package CPS_Clients.Controllers.Employee;

import CPS_Clients.ConstsEmployees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CustomerServiceEnteryController  extends EmployeeBaseController{

	@FXML
    private Label Headline;
	
    @FXML
    void OnManageComplaints(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ManageComplaints, ConstsEmployees.CustomerServiceEntery);
    }

    @FXML
    void OnSaveParkingSpot(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ReserveParkingSpot, ConstsEmployees.CustomerServiceEntery);

    }
    @FXML
    void OnBack(ActionEvent event) 
    {
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ParkingLotWorkerEntery );
    }
}
