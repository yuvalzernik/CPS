package CPS_Clients.Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.function.Consumer;
import CPS_Clients.ConstsWeb;
import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import clientServerCPS.RequestResult;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.Parkinglot;
import entities.Reservation;
import entities.enums.ReservationStatus;
import entities.enums.ReservationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuButton;

public class OrderInAdvanceController extends BaseController {
	@FXML // fx:id="carNumber"
	private TextField carNumber; // Value injected by FXMLLoader

	@FXML // fx:id="parkingLot"
	private MenuButton parkingLot; // Value injected by FXMLLoader

	@FXML // fx:id="arrivalHour"
	private TextField arrivalHour; // Value injected by FXMLLoader

	@FXML // fx:id="leavingHour"
	private TextField leavingHour; // Value injected by FXMLLoader

	@FXML // fx:id="customerId"
	private TextField customerId; // Value injected by FXMLLoader

	@FXML // fx:id="email"
	private TextField email; // Value injected by FXMLLoader

	@FXML // fx:id="arrivalDate"
	private DatePicker arrivalDate; // Value injected by FXMLLoader

	@FXML // fx:id="leavingDate"
	private DatePicker leavingDate; // Value injected by FXMLLoader
	ArrayList<Parkinglot> parkinglist  = new ArrayList<Parkinglot>();
	String parking_Lot;

	Reservation reservation;

	Customer customer;

	@FXML
	void initialize() {
		arrivalDate.setEditable(true);
		arrivalDate.setValue(LocalDate.now());
		leavingDate.setEditable(true);
		leavingDate.setValue(LocalDate.now());
		ServerResponse<ArrayList<Parkinglot>> initListParkinglot = RequestsSender.GetAllParkinglots();
		int length = initListParkinglot.GetResponseObject().size();
		for (int i = 0; i < length; i++) {
			MenuItem item = new MenuItem(initListParkinglot.GetResponseObject().get(i).getParkinglotName());
			item.setOnAction(a -> {
				parking_Lot = (item.getText());
				parkingLot.setText(parking_Lot);
			});
			parkingLot.getItems().add(item);
			parkinglist.add(initListParkinglot.GetResponseObject().get(i));
		}
	}

	@FXML
	void OnPayment(ActionEvent event) {

		float paymentAmount = AmountToPay();
		if (!TryConstructOrderInAdvance()) {
			return;
		}

		Consumer<Void> afterPayment = Void -> {
			// + what if register succeed but add customer failed ?

			ServerResponse<Reservation> OrderInAdvanceResponse = RequestsSender.Reservation(reservation);

			ServerResponse<Customer> AddCustomerIfNotExist = RequestsSender.AddCustomerIfNotExists(customer);

			if (OrderInAdvanceResponse.GetRequestResult().equals(RequestResult.Failed)
					|| AddCustomerIfNotExist.GetRequestResult().equals(RequestResult.Failed)) {
				DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.ServerProblemMessage, null, false);

				return;
			}

			DialogBuilder.AlertDialog(AlertType.INFORMATION, Consts.Approved, Consts.ThankYouForOrderInAdvance, null,
					false);

			myControllersManager.GoToHomePage(Consts.Payment);
		};

		myControllersManager.Payment(reservation, paymentAmount, afterPayment, ConstsWeb.OrderInAdvance);
		arrivalDate.setValue(LocalDate.now());
		leavingDate.setValue(LocalDate.now());

	}

	@FXML
	void OnBack(ActionEvent event) {
		arrivalDate.setValue(LocalDate.now());
		leavingDate.setValue(LocalDate.now());
		myControllersManager.Back(PreviousScene, ConstsWeb.OrderInAdvance);
	}

	private boolean TryConstructOrderInAdvance() {
		customer = new Customer(customerId.getText(), email.getText(), 0);

		if (!InputValidator.OrderInAdvance(carNumber.getText(), arrivalDate.getValue(), leavingDate.getValue(),
				arrivalHour.getText(), leavingHour.getText()) || !InputValidator.Customer(customer)) {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);

			return false;
		}

		reservation = new Reservation(ReservationType.Web, customerId.getText(), parking_Lot, carNumber.getText(),
				arrivalDate.getValue(), leavingDate.getValue(), LocalTime.parse(arrivalHour.getText()),
				LocalTime.parse(leavingHour.getText()), ReservationStatus.NotStarted);

		customer = new Customer(customerId.getText(), email.getText(), 0);

		return true;
	}

	private float AmountToPay() {
		float rate = 0;
		float days = (float) (Period.between(arrivalDate.getValue(), leavingDate.getValue()).getDays());
		float hours = (LocalTime.parse(leavingHour.getText()).getHour()
				- LocalTime.parse(arrivalHour.getText()).getHour());
		for (Parkinglot parkinglot : parkinglist) {
			if (parkinglot.getParkinglotName().equals(parking_Lot))
				rate = parkinglot.getInAdvanceRate();
		}
		return ((hours + days * 24) * rate);
	}

}
