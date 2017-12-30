package CPS_Clients.Controllers.Employee;


//import clientServerCPS.RequestsSender;
//import clientServerCPS.ServerResponse;
import entities.ChangeRatesRequest;
//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManageRequestRateChangeController extends EmployeeBaseController {

	@FXML
	private TableView<ChangeRatesRequest> myTable ;//= new TableView<>();
	private ObservableList<ChangeRatesRequest> ChangeRatesRequests;

	@FXML
	void initialize() {
		/*
		//// myTable.setEditable(true);
		ServerResponse<ArrayList<ChangeRatesRequest>> RequestsRes = RequestsSender.GetAllChangeRatesRequests();
		//ArrayList<ChangeRatesRequest> Requests = RequestsRes.GetResponseObject();
		
		ChangeRatesRequests = FXCollections.observableArrayList(RequestsRes.GetResponseObject());
		
		myTable.setItems(ChangeRatesRequests);
		*/

	}

	public ManageRequestRateChangeController() {
		super();

	}

	@FXML
	private TableColumn<?, ?> newInAdvancePrice;

	@FXML
	private TableColumn<?, ?> parkingLot;

	@FXML
	private TableColumn<?, ?> newGuestPrice;

	@FXML
	void OnMouseClicked(ActionEvent event) {

	}

}
