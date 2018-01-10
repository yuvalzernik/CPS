package clientServerCPS;

// These consts are used in the server side to determine what method should be called.

// For each const there should be one method in the ServerRequestHandler with the exact name.

public class ClientServerConsts
{
    public static final int PORT = 5555;
    
    public static final String RegisterFullMembership = "RegisterFullMembership";
    
    public static final String AddCustomerIfNotExists = "AddCustomerIfNotExists";
    
    public static final String RegisterPartialMembership = "RegisterPartialMembership";
    
    public static final String GetFullMembership = "GetFullMembership";
    
    public static final String GetCustomer = "GetCustomer";
    
    public static final String GetPartialMembership = "GetPartialMembership";
    
    public static final String GetReservation = "GetReservation";
    
    public static final String Reservation = "Reservation";
    
    public static final String GetEmployee = "GetEmployee";
    
    public static final String AddParkinglot = "AddParkinglot";
    
    public static final String GetParkingLot = "GetParkingLot";
    
    public static final String ChangeParkinglotStatus = "ChangeParkinglotStatus";
    
    public static final String GetAllParkinglots = "GetAllParkinglots";
    
    public static final String AddComplaint = "AddComplaint";
    
    public static final String GetAllActiveComplaints = "GetAllActiveComplaints";
    
    public static final String CloseComplaint = "CloseComplaint";
    
    public static final String AddChangeRatesRequest = "AddChangeRatesRequest";
    
    public static final String CloseChangeRatesRequest = "CloseChangeRatesRequest";
    
    public static final String GetAllChangeRatesRequests = "GetAllChangeRatesRequests";
    
    public static final String ChangeParkingSpotStatus = "ChangeParkingSpotStatus";
    
    public static final String GetAllDisabledParkingSpots = "GetAllDisabledParkingSpots";
    
    public static final String ChangeExpireFullMembership = "ChangeExpireFullMembership";
    
    public static final String ChangeExpirePartialMembership = "ChangeExpirePartialMembership";
    
    public static final String TryInsertGuestCar = "TryInsertGuestCar";
    
    public static final String RemoveCar = "RemoveCar";
    
    public static final String GetComplaintsReport = "GetComplaintsReport";
    
    public static final String GetPerformanceReport = "GetPerformanceReport";
    
    public static final String GetReservationReport = "GetReservationReport";
    
    public static final String GetDisabledReport = "GetDisabledReport";
    
    public static final String GetActivityReport = "GetActivityReport";
    
    public static final String GetStatusReport = "GetStatusReport";
    
    public static final String LoginUser = "LoginUser";
    
    public static final String LogoutUser = "LogoutUser";
    
    public static final String CloseReservation = "CloseReservation";
    
    public static final String AddCreditToCustomer = "AddCreditToCustomer";
}
