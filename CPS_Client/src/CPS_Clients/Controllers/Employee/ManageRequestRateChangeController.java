package CPS_Clients.Controllers.Employee;

import java.util.ArrayList;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.ChangeRatesRequest;
import entities.ChangeRatesResponse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;

public class ManageRequestRateChangeController extends EmployeeBaseController {

	ArrayList<String> myButtons = new ArrayList<String>();
	ArrayList<ChangeRatesRequest> myRequests;
	
	@FXML
    private ListView<String> RequestsList;
	private String currentRequestId = null;

    @FXML
    void OnLoadRequests(ActionEvent event) 
    {
    	currentRequestId = null;
    	
    	ServerResponse<ArrayList<ChangeRatesRequest>> serverResponse = RequestsSender.GetAllChangeRatesRequests();
    	
    	if(serverResponse.GetRequestResult().equals(RequestResult.Failed))
    	{
    		DialogBuilder.AlertDialog(AlertType.ERROR, "",ConstsEmployees.FailToGetRequest, null,false);
    		SetPreviousScene(PreviousScene);
    	}
    	
    	
    	ArrayList<ChangeRatesRequest> requests = serverResponse.GetResponseObject();
    	myRequests=requests;
    	ObservableList<String> requestObservable = FXCollections.observableArrayList();
    	
    	for(ChangeRatesRequest request : requests)
    	{
    		requestObservable.add(request.getRequestId());
    	}
    	
    	RequestsList.setItems(requestObservable);
    	
    	if(requests.size()==0)
    	{
    		DialogBuilder.AlertDialog(AlertType.INFORMATION, null, ConstsEmployees.NoRateChangeRequests, null, false);
    	}
    }

    
    @FXML
    void OnHandleRequests(ActionEvent event)
    {
    	ChangeRatesRequest currentRequest = null;
    	
    	if(currentRequestId == null)
    	{
    		DialogBuilder.AlertDialog(AlertType.WARNING, "", ConstsEmployees.ChooseRequest,null,false);
    		return ;
    	}
    	
    	for (ChangeRatesRequest request : myRequests) 
    	{
    		if(request.getRequestId().equals(currentRequestId))
    		{
    			currentRequest=request;
    		}
    		
		}
    	if(currentRequest!=null)
    	{
    		String Details = "Request ID: " + currentRequest.getRequestId() + "\n"+ 
    						 "New guest rate rquest: " + currentRequest.getNewGuestRate() +"\n" +
    						 "New in advance rate request: " + currentRequest.getNewInAdvanceRate() + "\n";
    		
    		String result=DialogBuilder.AlertDialog(AlertType.NONE, ConstsEmployees.SelectChoice, Details,myButtons,true);
    		
    		if(result.equals("Approve")) 
    		{
      			ChangeRatesResponse myResponse = new ChangeRatesResponse(currentRequestId, true);
       			RequestsSender.CloseChangeRatesRequest(myResponse);
       			DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.RequestApproved ,null,false);
    		}
    		
    		else if(result.equals("Decline")) 
    		{	
    			ChangeRatesResponse myResponse = new ChangeRatesResponse(currentRequestId, false);
       			RequestsSender.CloseChangeRatesRequest(myResponse);
    	    	DialogBuilder.AlertDialog(AlertType.INFORMATION, "", ConstsEmployees.RequestDeclined,null,false); 	
    		}
    	}
    	
    	OnLoadRequests(null);
    	
    }


    @FXML
    void OnBack(ActionEvent event) 
    {
    	myControllersManager.Back(PreviousScene, ConstsEmployees.ManageRequestRateChange);
    }

    @FXML
    void initialize() 
    {
       	myButtons.add("Approve");
    	myButtons.add("Decline");
    	   	
    	RequestsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
    	{

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				currentRequestId = arg2;				
			}
    	});
    	  
    }
}
