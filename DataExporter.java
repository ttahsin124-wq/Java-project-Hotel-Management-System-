import java.util.ArrayList;

// Target Interface 
public interface DataExporter {
    void exportCustomers(ArrayList<Customer> customers, String destination);
    void exportRooms(ArrayList<HotelRoom> rooms, String destination);
    void exportEmployees(ArrayList<Employee> employees, String destination);
    void exportReservations(ArrayList<Reservation> reservations, String destination);
    void exportDrivers(ArrayList<Driver> drivers, String destination);
    String getFormatName();
}