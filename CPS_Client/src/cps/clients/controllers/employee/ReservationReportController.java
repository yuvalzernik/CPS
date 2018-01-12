package cps.clients.controllers.employee;

import java.util.ArrayList;

import cps.clientServer.RequestsSender;
import cps.clientServer.ServerResponse;
import cps.entities.Reservation;
import cps.entities.ReservationReport;
import cps.entities.enums.ReservationStatus;
import cps.utilities.ConstsEmployees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class ReservationReportController.
 */
public class ReservationReportController extends EmployeeBaseController {
	
	/**
	 * The Class MyRow.
	 */
	public class MyRow 
	{
		
		/** The Reservation id. */
		String ReservationId;
		
		/** The Parking lot name. */
		String ParkingLotName;
		
		/** The Status. */
		ReservationStatus Status;
		
		/**
		 * Instantiates a new my row.
		 *
		 * @param a the a
		 * @param b the b
		 * @param c the c
		 */
		public MyRow(String a, String b, ReservationStatus c) 
		{
			ReservationId=a;
			ParkingLotName=b;
			Status=c;
		}
		
		/**
		 * Gets the reservation id.
		 *
		 * @return the reservation id
		 */
		public String getReservationId() {return ReservationId;}
		
		/**
		 * Gets the status.
		 *
		 * @return the status
		 */
		public ReservationStatus getStatus() {return Status;}
		
		/**
		 * Gets the parking lot name.
		 *
		 * @return the parking lot name
		 */
		public String getParkingLotName() {return ParkingLotName;}
	}
	
	/** The In advancelist. */
	private ObservableList<MyRow> InAdvancelist = FXCollections.observableArrayList();
	
	/** The Guestlist. */
	private ObservableList<MyRow> Guestlist = FXCollections.observableArrayList();

	
	

	
    /** The excersized reservations. */
    @FXML
    private TextField excersizedReservations;

    /** The reservation amount. */
    @FXML
    private TextField reservationAmount;

    /** The guest reservation table. */
    @FXML
    private TableView<MyRow> guestReservationTable;

    /** The cancelled reservations. */
    @FXML
    private TextField cancelledReservations;

    /** The in advance reservation tablr. */
    @FXML
    private TableView<MyRow> inAdvanceReservationTablr;
    
    /**
     * Initialize.
     */
    @FXML
    void initialize() 
    {


         
    	guestReservationTable.setEditable(true);
    	inAdvanceReservationTablr.setEditable(true);
    	
    	guestReservationTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ReservationId"));
    	guestReservationTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ParkingLotName"));
    	guestReservationTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Status"));
    	
    	inAdvanceReservationTablr.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ReservationId"));
    	inAdvanceReservationTablr.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ParkingLotName"));
    	inAdvanceReservationTablr.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Status"));
    }
		

    /**
     * On show 2.
     *
     * @param event the event
     */
    @FXML
    void OnShow2(ActionEvent event) 
    {	
    	String temp=MyEmployee.getOrgAffiliation();
    	if(temp.equals("CEO"))
    		temp="all";
      	ServerResponse<ReservationReport>ReservationReportRes= RequestsSender.GetReservationReport(temp);
    	ReservationReport reservationReport=ReservationReportRes.GetResponseObject();
    	
    	String amount=Integer.toString(reservationReport.getReservationAmount());
    	reservationAmount.setText(amount); 
    	String ex=Integer.toString(reservationReport.getReservationExcersied());
    	excersizedReservations.setText(ex);
    	String canceled=Integer.toString(reservationReport.getReservationCancelled());
    	cancelledReservations.setText(canceled);
    	
    	ArrayList<Reservation>reservationsList=reservationReport.getGuestList();
    	for (Reservation reservation : reservationsList) 
    	{
    		Guestlist.add(new MyRow(reservation.getOrderId(), reservation.getParkingLot(), reservation.getReservationStatus()));
		}
    	guestReservationTable.setItems(Guestlist);
    	

    	ArrayList<Reservation>reservationsList2=reservationReport.getInAdvanceList();
    	for (Reservation reservation : reservationsList2) 
    	{
    		InAdvancelist.add(new MyRow(reservation.getOrderId(), reservation.getParkingLot(), reservation.getReservationStatus()));
		}
    	inAdvanceReservationTablr.setItems(InAdvancelist);
    }

    /**
     * On back.
     *
     * @param event the event
     */
    @FXML
    void OnBack(ActionEvent event) 
    {
    	guestReservationTable.getItems().clear();
    	inAdvanceReservationTablr.getItems().clear();
    	myControllersManager.Back(PreviousScene, ConstsEmployees.ReservationReport);
    }

}
