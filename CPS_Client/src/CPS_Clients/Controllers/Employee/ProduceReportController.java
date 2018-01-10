package CPS_Clients.Controllers.Employee;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.ComplaintsReport;
import entities.DisabledReport;
import entities.PerformanceReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;

public class ProduceReportController extends EmployeeBaseController 
{

    @FXML
    void OnReservationReport(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ReservationReport,ConstsEmployees.ProduceReport);

    }

    @FXML
    void OnComaplaintsReport(ActionEvent event) 
    {
      	ServerResponse<ComplaintsReport>ComplaintsRes= RequestsSender.GetComplaintsReport();
    	ComplaintsReport complaintReport=ComplaintsRes.GetResponseObject();
    	String out="Complaints Amount: " + complaintReport.getComplaintAmount()+"\n"+"Handled Complaints: " + complaintReport.getHandledComplaints();
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", out, null,false);

    }

    @FXML
    void OnDisabledParkingSpotReport(ActionEvent event) 
    {
      	ServerResponse<DisabledReport>Res= RequestsSender.GetDisabledReport("all");
    	DisabledReport Disabledreport=Res.GetResponseObject();
    	int actice= Disabledreport.getActiveList().size();
    	String out="Number of disabled parking spots during the quarterly: " + Disabledreport.getDisabledAmount()+"\n"+ "Number of parking spots that have already been activized: "+ actice;
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", out, null,false);
    }

    @FXML
    void OnPerformanceReport(ActionEvent event) 
    {
      	ServerResponse<PerformanceReport>Res= RequestsSender.GetPerformanceReport();
    	PerformanceReport PrefReport=Res.GetResponseObject();
    	String out="Memberships amount: " + PrefReport.getMembershipAmount()+"\n"+"Number of members with multiple cars: " + PrefReport.getMembersMultipleCars();
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", out, null,false);
    }

    @FXML
    void OnActivityReport(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ActivityReport,ConstsEmployees.ProduceReport);

    }

    @FXML
    void OnBack(ActionEvent event) 
    {
    	
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ProduceReport );

    }

    @FXML
    void OnStatusReport(ActionEvent event) 
    {	
    	myControllersManager.SetScene(ConstsEmployees.StatusReport,ConstsEmployees.ProduceReport);
    }

}
