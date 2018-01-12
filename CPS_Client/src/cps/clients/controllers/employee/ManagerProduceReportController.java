package cps.clients.controllers.employee;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import cps.clientServer.RequestsSender;
import cps.clientServer.ServerResponse;
import cps.entities.ComplaintsReport;
import cps.entities.DisabledReport;
import cps.utilities.ConstsEmployees;
import cps.utilities.DialogBuilder;
import javafx.event.ActionEvent;

public class ManagerProduceReportController  extends EmployeeBaseController {

    @FXML
    void OnBack(ActionEvent event) 
    {
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ManagerProduceReport );
    }

    @FXML
    void OnReservationReport(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ReservationReport,ConstsEmployees.ManagerProduceReport);

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
    	String temp=MyEmployee.getOrgAffiliation();
      	ServerResponse<DisabledReport>Res= RequestsSender.GetDisabledReport(temp);
    	DisabledReport Disabledreport=Res.GetResponseObject();
    	int actice= Disabledreport.getActiveList().size();
    	String out="Number of disabled parking spots during the quarterly: " + Disabledreport.getDisabledAmount()+"\n"+ "Number of parking spots that have already been activated: "+ actice;
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", out, null,false);
    }

}
