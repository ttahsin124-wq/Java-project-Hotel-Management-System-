import java.util.ArrayList;

public class ExportManager 
{
    private DataExporter currentExporter;
    
    public ExportManager() 
    {
        this.currentExporter = new CSVAdapter();
    }
    
    public void setExporter(DataExporter exporter) 
    {
        this.currentExporter = exporter;
    }
    
    public DataExporter getExporter() 
    {
        return currentExporter;
    }
    
    public void exportAllData(String filePath) 
    {
        FileUtil fileUtil = FileUtil.getInstance();
        ArrayList<Customer> customers = fileUtil.load("customers.dat");
        ArrayList<HotelRoom> rooms = fileUtil.load("rooms.dat");
        ArrayList<Employee> employees = fileUtil.load("employees.dat");
        ArrayList<Reservation> reservations = fileUtil.load("reservations.dat");
        ArrayList<Driver> drivers = fileUtil.load("drivers.dat");
        if (currentExporter != null) 
        {
            currentExporter.exportCustomers(customers, filePath);
            currentExporter.exportRooms(rooms, filePath);
            currentExporter.exportEmployees(employees, filePath);
            currentExporter.exportReservations(reservations, filePath);
            currentExporter.exportDrivers(drivers, filePath);
        }
    }
    
    public String getCurrentFormat() 
    {
        return currentExporter != null ? currentExporter.getFormatName() : "No exporter selected";
    }
}