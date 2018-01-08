package CPS_Clients.Controllers.Employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableController extends EmployeeBaseController{

	public class MyRow {
		String colA;
		String colB;
		String colC;
		
		public MyRow(String a, String b, String c) {
			this.colA = a;
			this.colB = b;
			this.colC = c;
		}

		public String getColA() {
			return colA;
		}

		public String getColB() {
			return colB;
		}

		public String getColC() {
			return colC;
		}
	}
	
	private ObservableList<MyRow> list = FXCollections.observableArrayList();

	@FXML
	private TableView<MyRow> table;

	@FXML
	void OnShow(ActionEvent event) {
		
		list.add(new MyRow("1", "1", "1"));
		list.add(new MyRow("2", "3", "4"));
		
		table.setItems(list);
		
	}

	@FXML
	void initialize() {
		
		table.setEditable(true);
		
		table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("colA"));
		table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("colB"));
		table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("colC"));
		
	}
}
