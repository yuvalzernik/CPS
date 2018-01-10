package CPS_Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DialogBuilder
{
    PasswordField password = new PasswordField();
    
    private static List<String> TextExtractor(List<TextField> textList)
    {
	List<String> extractedList = new ArrayList<>();
	
	for (TextField textField : textList)
	{
	    extractedList.add(textField.getText());
	}
	
	return extractedList;
    }
    
    static public Dialog<List<String>> InputsDialog(String dialogTitle, List<String> inputLabels, String buttonLabel)
    {
	Dialog<List<String>> dialog = new Dialog<>();
	dialog.setTitle(dialogTitle);
	
	dialog.getDialogPane().getStylesheets().add(DialogBuilder.class.getResource("style.css").toExternalForm());
	
	// Set the button types
	ButtonType submit = new ButtonType(buttonLabel, ButtonData.OK_DONE);
	dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);
	
	// Create the labels
	GridPane grid = new GridPane();
	grid.setHgap(20);
	grid.setVgap(20);
	grid.setPadding(new Insets(20, 150, 10, 10));
	
	List<TextField> inputs = new ArrayList<>();
	
	for (int i = 0; i < inputLabels.size(); i++)
	{
	    inputs.add(new TextField());
	    
	    grid.add(new Label(inputLabels.get(i)), 0, i);
	    
	    grid.add(inputs.get(i), 1, i);
	}
	
	dialog.getDialogPane().setContent(grid);
	
	dialog.setResultConverter(dialogButton ->
	{
	    if (dialogButton == submit)
	    {
		return TextExtractor(inputs);
	    }
	    return null;
	});
	
	return dialog;
    }
    
    static public String AlertDialog(AlertType alertType, String title, String header, List<String> buttonNames,
	    boolean withCancel)
    {
	Alert alert = new Alert(alertType);
	
	alert.setTitle(title);
	alert.setHeaderText(header);
	
	alert.getDialogPane().getStylesheets().add(DialogBuilder.class.getResource("style.css").toExternalForm());
	
	if (buttonNames != null)
	{
	    for (String buttonName : buttonNames)
	    {
		alert.getButtonTypes().add(new ButtonType(buttonName));
	    }
	} 
	if (withCancel)
	{
	    alert.getButtonTypes().add(new ButtonType("Cancel", ButtonData.CANCEL_CLOSE));
	}
	
	Optional<ButtonType> result = alert.showAndWait();
	
	if (result.isPresent())
	{
	    return result.get().getText();
	}
	else
	{
	    return null;
	}
    }
    
}
