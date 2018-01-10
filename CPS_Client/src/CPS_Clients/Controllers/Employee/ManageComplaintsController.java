package CPS_Clients.Controllers.Employee;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.CloseComplaintRequest;
import entities.Complaint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;

public class ManageComplaintsController extends EmployeeBaseController{

	ArrayList<Complaint> myComplaints;
	ArrayList<String> myButtons = new ArrayList<String>();
	private ArrayList<String> Compensate = new ArrayList<>();
	private String currentComplaintId = null;

	
    @FXML
    private ListView<String> complaintsList;
    
    @FXML
    void OnBack(ActionEvent event) {
    	myControllersManager.Back(PreviousScene, ConstsEmployees.ManageComplaints);
    }
    
    @FXML
    void OnHandleComplaint(ActionEvent event)
    {
    	Complaint currentComplaint=null;
    	
    	if(currentComplaintId == null)
    	{
    		DialogBuilder.AlertDialog(AlertType.WARNING, "", ConstsEmployees.ChooseComplaint,null,false);
    		return ;
    	}
    	
    	for (Complaint complaint : myComplaints) 
    	{
    		if(complaint.getComplaintId().equals(currentComplaintId))
    		{
    			currentComplaint=complaint;
    		}
    		
		}
    	if(currentComplaint!=null)
    	{
    		String Details="Complaint ID: " + currentComplaint.getComplaintId() + "\n"+ "Complaint Details: " + currentComplaint.getComplaintDetails() +"\n";
    		String result=DialogBuilder.AlertDialog(AlertType.NONE, ConstsEmployees.SelectChoice, Details,myButtons,true);
    		
    		if(result.equals("Compensate Customer")) 
    		{
    			Dialog<List<String>> dialog = DialogBuilder.InputsDialog(Consts.FillRequest, Compensate, "Compensate");
    	    	Optional<List<String>> myresult = dialog.showAndWait();
    	    	myresult.ifPresent(inputs->
    	    	{
    	    		Float compensationAmount= Float.parseFloat(inputs.get(0));
    	    		CloseComplaintRequest myClosedComplaint=new CloseComplaintRequest(currentComplaintId, compensationAmount);
    	    		RequestsSender.CloseComplaint(myClosedComplaint);
    	    		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ComplaintClosed,null,false);
    	    	});
    		}
    		
    		else if(result.equals("Close without compensation")) 
    		{
    			
    	    		CloseComplaintRequest myClosedComplaint=new CloseComplaintRequest(currentComplaintId,0);
    	    		RequestsSender.CloseComplaint(myClosedComplaint);
    	    		DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.ComplaintClosed,null,false);
    	    	
    		}
    	}
    	
    	OnLoad(null);
    }

    @FXML
    void OnLoad(ActionEvent event) 
    {
    	currentComplaintId = null;
    	
    	ServerResponse<ArrayList<Complaint>> serverResponse = RequestsSender.GetAllActiveComplaints();
    	
    	if(serverResponse.GetRequestResult().equals(RequestResult.Failed))
    	{
    		DialogBuilder.AlertDialog(AlertType.ERROR, "",ConstsEmployees.FailToGetActive, null,false);
    		SetPreviousScene(PreviousScene);
    	}
    	
    	
    	ArrayList<Complaint> complaints = serverResponse.GetResponseObject();
    	myComplaints=complaints;
    	ObservableList<String> complaintsObservable = FXCollections.observableArrayList();
    	
    	for(Complaint complaint : complaints)
    	{
    		complaintsObservable.add(complaint.getComplaintId());
    	}
    	
    	complaintsList.setItems(complaintsObservable);
    	
    	if(complaints.size()==0)
    	{
    		DialogBuilder.AlertDialog(AlertType.INFORMATION, null, ConstsEmployees.NoComplaints, null, false);
    	}
    	
    }

    @FXML
    void initialize() 
    {
       	myButtons.add("Compensate Customer");
    	myButtons.add("Close without compensation");
    	Compensate.add("Compensation Amount:");
    	
    	complaintsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
    	{

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				
				currentComplaintId = arg2;
				
				
			}
    	});

    }
}
