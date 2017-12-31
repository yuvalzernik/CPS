package CPS_Clients.Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.function.Consumer;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.FullMembership;
import entities.Parkinglot;
import entities.PartialMembership;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class PartialMembershipRegisterController extends BaseController {
	@FXML
	private TextField carNumber;

	@FXML
	private MenuButton parkingLot;

	@FXML
	private TextField exitHour;

	@FXML
	private ListView<String> carListView;

	@FXML
	private TextField id;

	@FXML
	private DatePicker startingDatePicker;

	@FXML
	private TextField email;
	
	PartialMembership partialMembership;

	Customer customer;

	String parking_Lot;
	
	private ObservableList<String> cars = FXCollections.observableArrayList();
	ArrayList<Parkinglot> parkinglist  = new ArrayList<Parkinglot>();


	
	
	ArrayList<String> carList=new ArrayList<String>();

	@FXML
	void initialize() {
		carListView.setEditable(true);
		startingDatePicker.setEditable(true);
		startingDatePicker.setValue(LocalDate.now());
		ServerResponse<ArrayList<Parkinglot>> initListParkinglot = RequestsSender.GetAllParkinglots();
		if (initListParkinglot.GetRequestResult().equals(RequestResult.Failed)) {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);
			return;
		}
		int length = initListParkinglot.GetResponseObject().size();
		for (int i = 0; i < length; i++) {
			MenuItem item=new MenuItem(initListParkinglot.GetResponseObject().get(i).getParkinglotName());
			item.setOnAction(a->{
				parking_Lot=(item.getText());
				parkingLot.setText(parking_Lot);
			});
			parkingLot.getItems().add(item);
			parkinglist.add(initListParkinglot.GetResponseObject().get(i));

		}
	}

	@FXML
	void OnBack(ActionEvent event) {
		myControllersManager.Back(PreviousScene, Consts.PartialMembershipRegister);

		carListView.setItems(null);
		cars.clear();
		exitHour.clear();
	}

	@FXML
	void OnSubmitAndPay(ActionEvent event) {
		float paymentAmount = AmountToPay();
		if (!TryConstructPartialMembership()) {
			return;
		}
		Consumer<Void> afterPayment = Void -> {
			ServerResponse<PartialMembership> registerPartialMembershipResponse = RequestsSender
					.RegisterPartialMembership(partialMembership);
			ServerResponse<Customer> AddCustomerIfNotExist = RequestsSender.AddCustomerIfNotExists(customer);

			if (registerPartialMembershipResponse.GetRequestResult().equals(RequestResult.Failed)
					|| AddCustomerIfNotExist.GetRequestResult().equals(RequestResult.Failed)) {
				DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);

				return;
			}
			DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.ThankYouForRegistering, null,
					false);

			myControllersManager.GoToHomePage(Consts.Payment);
		};

		myControllersManager.Payment(partialMembership, paymentAmount, afterPayment, Consts.PartialMembershipRegister);

		carListView.setItems(null);
		cars.clear();
	}

	private boolean TryConstructPartialMembership() {

		customer = new Customer(id.getText(), email.getText(), 0);
		if(exitHour.getText().equals(""))
			exitHour.setText("00:00");
		else {
			if(!InputValidator.CheckHourFormat(exitHour.getText())) {
				DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
				return false;
			}
		}

		if (!InputValidator.PartialMembership( carList ,email.getText(), startingDatePicker.getValue()) || !InputValidator.Customer(customer)) {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);

			return false;
		}

		partialMembership = new PartialMembership(id.getText(), startingDatePicker.getValue(),
				startingDatePicker.getValue().plusDays(28), parking_Lot, carList, LocalTime.parse(exitHour.getText()));

		customer = new Customer(id.getText(), email.getText(), 0);

		return true;
	}

	@FXML
	void OnAddCar(ActionEvent event) {
		
		cars.add(carNumber.getText());
		carList.add(carNumber.getText());

		carNumber.clear();

		carListView.setItems(cars);
	}
	
	private float AmountToPay() {
		float rate = 0;
		float cars=carList.size();
		float hours=0;
		hours=(cars>1?54:60);
		for (Parkinglot parkinglot : parkinglist) {
			if (parkinglot.getParkinglotName().equals(parking_Lot))
				rate = parkinglot.getInAdvanceRate();
		}
		return ( cars * hours * rate);
	}

}
