import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVAdapter implements DataExporter 
{
    
    @Override
    public void exportCustomers(ArrayList<Customer> customers, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_customers.csv"))) 
        {
          
            writer.println("ID,Name,Phone,Email,Room Number,Check-In,Check-Out");
            for (Customer c : customers) {
                writer.printf("%d,%s,%s,%s,%s,%s,%s%n",
                    c.getId(),
                    c.getName(),
                    c.getPhone(),
                    c.getEmail(),
                    c.getRoomNumber() != null ? c.getRoomNumber() : "Not assigned",
                    c.getCheckIn(),
                    c.getCheckOut()
                );
            }
            System.out.println("✅ Exported " + customers.size() + " customers to CSV");
        } catch (Exception e) {
            System.err.println("❌ Error exporting customers: " + e.getMessage());
        }
    }
    
    @Override
    public void exportRooms(ArrayList<HotelRoom> rooms, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_rooms.csv"))) {
            writer.println("Room No,Category,Price,Available,Cleaned");
            
            for (HotelRoom r : rooms) {
                writer.printf("%d,%s,%.2f,%s,%s%n",
                    r.roomNo,
                    r.category,
                    r.price,
                    r.available ? "Yes" : "No",
                    r.cleaned ? "Yes" : "No"
                );
            }
            System.out.println("✅ Exported " + rooms.size() + " rooms to CSV");
        } catch (Exception e) {
            System.err.println("❌ Error exporting rooms: " + e.getMessage());
        }
    }
    
    @Override
    public void exportEmployees(ArrayList<Employee> employees, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_employees.csv"))) {
            writer.println("ID,Name,Age,Gender,Department,Salary");
            
            for (Employee e : employees) {
                writer.printf("%d,%s,%d,%s,%s,%.2f%n",
                    e.getId(),
                    e.getName(),
                    e.getAge(),
                    e.getGender(),
                    e.getDepartment(),
                    e.getSalary()
                );
            }
            System.out.println("✅ Exported " + employees.size() + " employees to CSV");
        } catch (Exception e) {
            System.err.println("❌ Error exporting employees: " + e.getMessage());
        }
    }
    
    @Override
    public void exportReservations(ArrayList<Reservation> reservations, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_reservations.csv"))) {
            writer.println("Reservation ID,Customer Name,Room No,Check-In,Check-Out,Total Amount,Status");
            
            for (Reservation r : reservations) {
                writer.printf("%d,%s,%d,%s,%s,%.2f,%s%n",
                    r.getReservationId(),
                    r.getCustomer().getName(),
                    r.getRoom().roomNo,
                    r.getCheckIn(),
                    r.getCheckOut(),
                    r.getTotalAmount(),
                    r.getStatus()
                );
            }
            System.out.println("✅ Exported " + reservations.size() + " reservations to CSV");
        } catch (Exception e) {
            System.err.println("❌ Error exporting reservations: " + e.getMessage());
        }
    }
    
    @Override
    public void exportDrivers(ArrayList<Driver> drivers, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_drivers.csv"))) {
            writer.println("Name,Age,Gender,Car Company,Car Name,Location");
            
            for (Driver d : drivers) {
                writer.printf("%s,%d,%s,%s,%s,%s%n",
                    d.getName(),
                    d.getAge(),
                    d.getGender(),
                    d.getCarCompany(),
                    d.getCarName(),
                    d.getLocation()
                );
            }
            System.out.println("✅ Exported " + drivers.size() + " drivers to CSV");
        } catch (Exception e) {
            System.err.println("❌ Error exporting drivers: " + e.getMessage());
        }
    }
    
    @Override
    public String getFormatName() {
        return "CSV (Comma Separated Values)";
    }
}