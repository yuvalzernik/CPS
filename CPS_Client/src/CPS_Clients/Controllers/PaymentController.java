package CPS_Clients.Controllers;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;

public class PaymentController extends BaseController {
	@FXML
	private TextField threeSecretNumbers;

	@FXML
	private TextArea orderDetails;

	@FXML
	private TextField creditCard;

	@FXML
	private Label paymentAmount;

	@FXML
	private TextField expirationDate;

	private Consumer<Void> afterPaymentDetailsCheck;

	void setPaymentAmount(float paymentAmount) {
		this.paymentAmount.setText(Float.toString(paymentAmount) + "$");
	}

	void SetOnSubmit(Consumer<Void> afterPaymentDetailsCheck) {
		this.afterPaymentDetailsCheck = afterPaymentDetailsCheck;
	}

	void SetOrderDetails(String order) {
		orderDetails.setText(order);
	}

	@FXML
	void OnBack(ActionEvent event) {
		myControllersManager.Back(PreviousScene, Consts.Payment);
	}

	@FXML
	void OnSubmit(ActionEvent event) {
		if (IsPaymentDetailsAccepted()) {
			afterPaymentDetailsCheck.accept(null);
		} else {
			DialogBuilder.AlertDialog(AlertType.ERROR, null, Consts.InputsAreIncorrect, null, false);
		}
	}

	private boolean IsPaymentDetailsAccepted() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
			YearMonth ym = YearMonth.parse(expirationDate.getText(), formatter);

			if (InputValidator.CreditCardNumber(creditCard.getText())
					&& InputValidator.Ccv(threeSecretNumbers.getText()) && InputValidator.ExpirationDate(ym))
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
