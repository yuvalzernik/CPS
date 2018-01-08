package CPS_Clients.Controllers.Employee;

import CPS_Clients.ConstsEmployees;
import CPS_Clients.ConstsMor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CustomerServiceEnteryController  extends EmployeeBaseController{

    @FXML
    void OnManageComplaints(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsMor.ManageComplaints, ConstsEmployees.CustomerServiceEntery);
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
