package cps.clientServer;

// TODO: Auto-generated Javadoc
// These consts are used in the server side to determine what method should be called.

// For each const there should be one method in the ServerRequestHandler with the exact name.

/**
 * The Class ClientServerConsts.
 */
public class ClientServerConsts
{
    
    /** The Constant PORT. */
    public static final int PORT = 5555;
    
    /** The Constant RegisterFullMembership. */
    public static final String RegisterFullMembership = "RegisterFullMembership";
    
    /** The Constant AddCustomerIfNotExists. */
    public static final String AddCustomerIfNotExists = "AddCustomerIfNotExists";
    
    /** The Constant RegisterPartialMembership. */
    public static final String RegisterPartialMembership = "RegisterPartialMembership";
    
    /** The Constant GetFullMembership. */
    public static final String GetFullMembership = "GetFullMembership";
    
    /** The Constant GetCustomer. */
    public static final String GetCustomer = "GetCustomer";
    
    /** The Constant GetPartialMembership. */
    public static final String GetPartialMembership = "GetPartialMembership";
    
    /** The Constant GetReservation. */
    public static final String GetReservation = "GetReservation";
    
    /** The Constant Reservation. */
    public static final String Reservation = "Reservation";
    
    /** The Constant GetEmployee. */
    public static final String GetEmployee = "GetEmployee";
    
    /** The Constant AddParkinglot. */
    public static final String AddParkinglot = "AddParkinglot";
    
    /** The Constant GetParkingLot. */
    public static final String GetParkingLot = "GetParkingLot";
    
    /** The Constant ChangeParkinglotStatus. */
    public static final String ChangeParkinglotStatus = "ChangeParkinglotStatus";
    
    /** The Constant GetAllParkinglots. */
    public static final String GetAllParkinglots = "GetAllParkinglots";
    
    /** The Constant AddComplaint. */
    public static final String AddComplaint = "AddComplaint";
    
    /** The Constant GetAllActiveComplaints. */
    public static final String GetAllActiveComplaints = "GetAllActiveComplaints";
    
    /** The Constant CloseComplaint. */
    public static final String CloseComplaint = "CloseComplaint";
    
    /** The Constant AddChangeRatesRequest. */
    public static final String AddChangeRatesRequest = "AddChangeRatesRequest";
    
    /** The Constant CloseChangeRatesRequest. */
    public static final String CloseChangeRatesRequest = "CloseChangeRatesRequest";
    
    /** The Constant GetAllChangeRatesRequests. */
    public static final String GetAllChangeRatesRequests = "GetAllChangeRatesRequests";
    
    /** The Constant ChangeParkingSpotStatus. */
    public static final String ChangeParkingSpotStatus = "ChangeParkingSpotStatus";
    
    /** The Constant GetAllDisabledParkingSpots. */
    public static final String GetAllDisabledParkingSpots = "GetAllDisabledParkingSpots";
    
    /** The Constant ChangeExpireFullMembership. */
    public static final String ChangeExpireFullMembership = "ChangeExpireFullMembership";
    
    /** The Constant ChangeExpirePartialMembership. */
    public static final String ChangeExpirePartialMembership = "ChangeExpirePartialMembership";
    
    /** The Constant TryInsertGuestCar. */
    public static final String TryInsertGuestCar = "TryInsertGuestCar";
    
    /** The Constant RemoveCar. */
    public static final String RemoveCar = "RemoveCar";
    
    /** The Constant GetComplaintsReport. */
    public static final String GetComplaintsReport = "GetComplaintsReport";
    
    /** The Constant GetPerformanceReport. */
    public static final String GetPerformanceReport = "GetPerformanceReport";
    
    /** The Constant GetReservationReport. */
    public static final String GetReservationReport = "GetReservationReport";
    
    /** The Constant GetDisabledReport. */
    public static final String GetDisabledReport = "GetDisabledReport";
    
    /** The Constant GetActivityReport. */
    public static final String GetActivityReport = "GetActivityReport";
    
    /** The Constant GetStatusReport. */
    public static final String GetStatusReport = "GetStatusReport";
    
    /** The Constant LoginUser. */
    public static final String LoginUser = "LoginUser";
    
    /** The Constant LogoutUser. */
    public static final String LogoutUser = "LogoutUser";
    
    /** The Constant CloseReservation. */
    public static final String CloseReservation = "CloseReservation";
    
    /** The Constant AddCreditToCustomer. */
    public static final String AddCreditToCustomer = "AddCreditToCustomer";
}
