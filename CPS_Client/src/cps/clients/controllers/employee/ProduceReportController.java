package cps.clients.controllers.employee;

import cps.clientServer.RequestsSender;
import cps.clientServer.ServerResponse;
import cps.entities.ComplaintsReport;
import cps.entities.DisabledReport;
import cps.entities.PerformanceReport;
import cps.utilities.ConstsEmployees;
import cps.utilities.DialogBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;

// TODO: Auto-generated Javadoc
/**
 * The Class ProduceReportController.
 */
public class ProduceReportController extends EmployeeBaseController 
{

    /**
     * On reservation report.
     *
     * @param event the event
     */
    @FXML
    void OnReservationReport(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ReservationReport,ConstsEmployees.ProduceReport);

    }

    /**
     * On comaplaints report.
     *
     * @param event the event
     */
    @FXML
    void OnComaplaintsReport(ActionEvent event) 
    {
      	ServerResponse<ComplaintsReport>ComplaintsRes= RequestsSender.GetComplaintsReport();
    	ComplaintsReport complaintReport=ComplaintsRes.GetResponseObject();
    	String out="Complaints Amount: " + complaintReport.getComplaintAmount()+"\n"+"Handled Complaints: " + complaintReport.getHandledComplaints();
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", out, null,false);

    }

    /**
     * On disabled parking spot report.
     *
     * @param event the event
     */
    @FXML
    void OnDisabledParkingSpotReport(ActionEvent event) 
    {
      	ServerResponse<DisabledReport>Res= RequestsSender.GetDisabledReport("all");
    	DisabledReport Disabledreport=Res.GetResponseObject();
    	int actice= Disabledreport.getActiveList().size();
    	String out="Number of disabled parking spots during the quarterly: " + Disabledreport.getDisabledAmount()+"\n"+ "Number of parking spots that have already been activized: "+ actice;
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", out, null,false);
    }

    /**
     * On performance report.
     *
     * @param event the event
     */
    @FXML
    void OnPerformanceReport(ActionEvent event) 
    {
      	ServerResponse<PerformanceReport>Res= RequestsSender.GetPerformanceReport();
    	PerformanceReport PrefReport=Res.GetResponseObject();
    	String out="Memberships amount: " + PrefReport.getMembershipAmount()+"\n"+"Number of members with multiple cars: " + PrefReport.getMembersMultipleCars();
    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", out, null,false);
    }

    /**
     * On activity report.
     *
     * @param event the event
     */
    @FXML
    void OnActivityReport(ActionEvent event) 
    {
    	myControllersManager.SetScene(ConstsEmployees.ActivityReport,ConstsEmployees.ProduceReport);

    }

    /**
     * On back.
     *
     * @param event the event
     */
    @FXML
    void OnBack(ActionEvent event) 
    {
    	
    	myControllersManager.Back(PreviousScene,ConstsEmployees.ProduceReport );

    }

    /**
     * On status report.
     *
     * @param event the event
     */
    @FXML
    void OnStatusReport(ActionEvent event) 
    {	
    	myControllersManager.SetScene(ConstsEmployees.StatusReport,ConstsEmployees.ProduceReport);
    }

}
