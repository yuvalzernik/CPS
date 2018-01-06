package CPS_Clients.Controllers;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.InputValidator;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
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
			
		    Image image = new Image("file:///C:/Users/yuval/git/CPS/CPS_Client/src/CPS_Clients/Controllers/200w_d.gif");  //pass in the image path
		    
		    myControllersManager.getScene(Consts.Payment).setCursor(new ImageCursor(image));
			
			afterPaymentDetailsCheck.accept(null);
			
		    myControllersManager.getScene(Consts.Payment).setCursor(Cursor.DEFAULT);

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
