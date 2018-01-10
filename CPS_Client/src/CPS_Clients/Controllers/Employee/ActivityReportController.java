package CPS_Clients.Controllers.Employee;

import java.time.LocalDate;
import java.util.ArrayList;

import CPS_Clients.ConstsEmployees;
import CPS_Utilities.DialogBuilder;
import clientServerCPS.RequestsSender;
import clientServerCPS.ServerResponse;
import entities.ActivityReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class ActivityReportController extends EmployeeBaseController{
	public class MyRow
	{
		String daynum;
		String numofex;
		String numofcanc;
		String numofdis;
		
		public MyRow(String a, String b, String c, String d) 
		{
			daynum=a;
			numofex=b;
			numofcanc=c;
			numofdis=d;
		}
		public String getDaynum() {
			return daynum;
		}
		public String getNumofcanc() {
			return numofcanc;
		}
		public String getNumofdis() {
			return numofdis;
		}
		public String getNumofex() {
			return numofex;
		}
	}
	
	private ObservableList<MyRow> MyTable = FXCollections.observableArrayList();
	
    @FXML
    private MenuButton month;
    
    @FXML
    private MenuButton year;
    
    @FXML
    private TextField deviationExercised;

    @FXML
    private TableView<MyRow> DataTable;

    @FXML
    private TextField deviationCancelled;

    @FXML
    private TextField medianDisabled;

    @FXML
    private TextField deviationDisabled;

    @FXML
    private TextField medianExercised;

    @FXML
    private TextField medianCancelled;
    
    @FXML
    private Label Test;
    
    String mon;
    
    String yr;
    
    @FXML
    void initialize() 
    {
	    DataTable.setEditable(true);
	    DataTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("daynum"));
	    DataTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("numofex"));
	    DataTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("numofcanc"));
	    DataTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("numofdis"));
	    
	    MenuItem item = new MenuItem("January");
		item.setOnAction(a -> {
			mon = (item.getText());
			month.setText(mon);
		});
		month.getItems().add(item);
		
	    MenuItem item2 = new MenuItem("February");
		item2.setOnAction(a -> {
			mon = (item2.getText());
			month.setText(mon);
		});
		month.getItems().add(item2);
		
	    MenuItem item3 = new MenuItem("March");
		item3.setOnAction(a -> {
			mon = (item3.getText());
			month.setText(mon);
		});
		month.getItems().add(item3);
		
	    MenuItem item4 = new MenuItem("April");
		item4.setOnAction(a -> {
			mon = (item4.getText());
			month.setText(mon);
		});
		month.getItems().add(item4);
		
	    MenuItem item5 = new MenuItem("May");
		item5.setOnAction(a -> {
			mon = (item5.getText());
			month.setText(mon);
		});
		month.getItems().add(item5);
		
	    MenuItem item6 = new MenuItem("June");
		item6.setOnAction(a -> {
			mon = (item6.getText());
			month.setText(mon);
		});
		month.getItems().add(item6);
		
	    MenuItem item7 = new MenuItem("July");
		item7.setOnAction(a -> {
			mon = (item7.getText());
			month.setText(mon);
		});
		month.getItems().add(item7);
		
	    MenuItem item8 = new MenuItem("August");
		item8.setOnAction(a -> {
			mon = (item8.getText());
			month.setText(mon);
		});
		month.getItems().add(item8);
		
	    MenuItem item9 = new MenuItem("September");
		item9.setOnAction(a -> {
			mon = (item9.getText());
			month.setText(mon);
		});
		month.getItems().add(item9);
		
	    MenuItem item10 = new MenuItem("October");
		item10.setOnAction(a -> {
			mon = (item10.getText());
			month.setText(mon);
		});
		month.getItems().add(item10);
		
	    MenuItem item11 = new MenuItem("November");
		item11.setOnAction(a -> {
			mon = (item11.getText());
			month.setText(mon);
		});
		month.getItems().add(item11);
		
	    MenuItem item12 = new MenuItem("December");
		item12.setOnAction(a -> {
			mon = (item12.getText());
			month.setText(mon);
		});
		month.getItems().add(item12);
	    
		for(int i = -3 ; i<= 0; i++)
		{
			MenuItem year1 = new MenuItem(Integer.toString(LocalDate.now().plusYears(i).getYear()));
		    year1.setOnAction(a -> {
				yr = (year1.getText());
				year.setText(yr);
			});
		    year.getItems().add(year1);
		}	
	}

    @FXML
    void OnBack(ActionEvent event)
    {
    	DataTable.getItems().clear();
    	myControllersManager.Back(PreviousScene, ConstsEmployees.ActivityReport);
    }

    @FXML
    void OnShow(ActionEvent event)
    {
    	DataTable.getItems().clear();
    	mon=month.getText();
    	yr=year.getText();
    	if(yr.equals("Year")||mon.equals("Month"))
    	{
    		DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.SelectYearAndMonth, null,false);
    	}
    	else 
    	{
	    	LocalDate date;
	    	int SelectedYear;
	    	int SelectedMonth = 0;
	    	SelectedYear=Integer.parseInt(yr);
	    			
	    	if(mon.equals("January"))
	       	 SelectedMonth=1;
	    	else if(mon.equals("February"))
	    		SelectedMonth=2;
	    	else if(mon.equals("March"))
	    		SelectedMonth=3;
	    	else if(mon.equals("April"))
	    		SelectedMonth=4;
	    	else if(mon.equals("May"))
	    		SelectedMonth=5;
	    	else if(mon.equals("June"))
	    		SelectedMonth=6;
	    	else if(mon.equals("July"))
	    		SelectedMonth=7;
	    	else if(mon.equals("August"))
	    		SelectedMonth=8;
	    	else if(mon.equals("September"))
	    		SelectedMonth=9;
	    	else if(mon.equals("October"))
	    		SelectedMonth=10;
	    	else if(mon.equals("November"))
	    		SelectedMonth=11;
	    	else if(mon.equals("December"))
	    		SelectedMonth=12;
	    	
	    	date = LocalDate.of(SelectedYear, SelectedMonth, 1);
	    	if(date.isAfter(LocalDate.now()))
	    	{
	        	DialogBuilder.AlertDialog(AlertType.ERROR, "", ConstsEmployees.DateisToLate, null,false);
	
	    	}
	    	else 
	    	{
		      	ServerResponse<ActivityReport>Res= RequestsSender.GetActivityReport(date);
		    	ActivityReport activityReport=Res.GetResponseObject();
		    	
		    	String DE=Float.toString(activityReport.getDeviationExercised());
		    	deviationExercised.setText(DE); 
		    	String DC=Float.toString(activityReport.getDeviationCancelled());
		    	deviationCancelled.setText(DC);
		    	String DD=Float.toString(activityReport.getDeviationDisabled());
		    	deviationDisabled.setText(DD);
		    	String ME=Float.toString(activityReport.getMedianExercised());
		    	medianExercised.setText(ME);
		    	String MC=Float.toString(activityReport.getMedianCancelled());
		    	medianCancelled.setText(MC);
		    	String MD=Float.toString(activityReport.getMedianDisabled());
		    	medianDisabled.setText(MD);
		    	
		    	ArrayList<Integer>Arrex=activityReport.getArrExercised();
		    	ArrayList<Integer>Arrcanc=activityReport.getArrCancelled();
		    	ArrayList<Float>Arrdis=activityReport.getArrDisabled();
		    	MyRow row;
		    	for (int i=1;i<=Arrex.size();i++) 
		    	{
		    		row = new MyRow(Integer.toString(i), Integer.toString(Arrex.get(i-1)), Integer.toString(Arrcanc.get(i-1)), Float.toString(Arrdis.get(i-1)));
					MyTable.add(row);
				}
		    	DataTable.setItems(MyTable);
	    	}
	    }
    }
}
