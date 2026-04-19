import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

// Adapter for JSON format
public class JSONAdapter implements DataExporter 
{
    
    @Override
    public void exportCustomers(ArrayList<Customer> customers, String destination) 
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_customers.json"))) 
        {
            writer.println("{");
            writer.println("  \"customers\": [");
            
            for (int i = 0; i < customers.size(); i++) {
                Customer c = customers.get(i);
                writer.println("    {");
                writer.println("      \"id\": " + c.getId() + ",");
                writer.println("      \"name\": \"" + escapeJson(c.getName()) + "\",");
                writer.println("      \"phone\": \"" + c.getPhone() + "\",");
                writer.println("      \"email\": \"" + c.getEmail() + "\",");
                writer.println("      \"roomNumber\": " + (c.getRoomNumber() != null ? c.getRoomNumber() : "null") + ",");
                writer.println("      \"checkIn\": \"" + c.getCheckIn() + "\",");
                writer.println("      \"checkOut\": \"" + c.getCheckOut() + "\"");
                writer.print("    }");
                if (i < customers.size() - 1) writer.println(",");
                else writer.println();
            }
            
            writer.println("  ]");
            writer.println("}");
            System.out.println("✅ Exported " + customers.size() + " customers to JSON");
        } catch (Exception e) {
            System.err.println("❌ Error exporting customers: " + e.getMessage());
        }
    }
    
    @Override
    public void exportRooms(ArrayList<HotelRoom> rooms, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_rooms.json"))) {
            writer.println("{");
            writer.println("  \"rooms\": [");
            
            for (int i = 0; i < rooms.size(); i++) {
                HotelRoom r = rooms.get(i);
                writer.println("    {");
                writer.println("      \"roomNo\": " + r.roomNo + ",");
                writer.println("      \"category\": \"" + r.category + "\",");
                writer.println("      \"price\": " + r.price + ",");
                writer.println("      \"available\": " + r.available + ",");
                writer.println("      \"cleaned\": " + r.cleaned);
                writer.print("    }");
                if (i < rooms.size() - 1) writer.println(",");
                else writer.println();
            }
            
            writer.println("  ]");
            writer.println("}");
            System.out.println("✅ Exported " + rooms.size() + " rooms to JSON");
        } catch (Exception e) {
            System.err.println("❌ Error exporting rooms: " + e.getMessage());
        }
    }
    
    @Override
    public void exportEmployees(ArrayList<Employee> employees, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_employees.json"))) {
            writer.println("{");
            writer.println("  \"employees\": [");
            
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                writer.println("    {");
                writer.println("      \"id\": " + e.getId() + ",");
                writer.println("      \"name\": \"" + escapeJson(e.getName()) + "\",");
                writer.println("      \"age\": " + e.getAge() + ",");
                writer.println("      \"gender\": \"" + e.getGender() + "\",");
                writer.println("      \"department\": \"" + e.getDepartment() + "\",");
                writer.println("      \"salary\": " + e.getSalary());
                writer.print("    }");
                if (i < employees.size() - 1) writer.println(",");
                else writer.println();
            }
            
            writer.println("  ]");
            writer.println("}");
            System.out.println("✅ Exported " + employees.size() + " employees to JSON");
        } catch (Exception e) {
            System.err.println("❌ Error exporting employees: " + e.getMessage());
        }
    }
    
    @Override
    public void exportReservations(ArrayList<Reservation> reservations, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_reservations.json"))) {
            writer.println("{");
            writer.println("  \"reservations\": [");
            
            for (int i = 0; i < reservations.size(); i++) {
                Reservation r = reservations.get(i);
                writer.println("    {");
                writer.println("      \"reservationId\": " + r.getReservationId() + ",");
                writer.println("      \"customerName\": \"" + escapeJson(r.getCustomer().getName()) + "\",");
                writer.println("      \"roomNo\": " + r.getRoom().roomNo + ",");
                writer.println("      \"checkIn\": \"" + r.getCheckIn() + "\",");
                writer.println("      \"checkOut\": \"" + r.getCheckOut() + "\",");
                writer.println("      \"totalAmount\": " + r.getTotalAmount() + ",");
                writer.println("      \"status\": \"" + r.getStatus() + "\"");
                writer.print("    }");
                if (i < reservations.size() - 1) writer.println(",");
                else writer.println();
            }
            
            writer.println("  ]");
            writer.println("}");
            System.out.println("✅ Exported " + reservations.size() + " reservations to JSON");
        } catch (Exception e) {
            System.err.println("❌ Error exporting reservations: " + e.getMessage());
        }
    }
    
    @Override
    public void exportDrivers(ArrayList<Driver> drivers, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(destination + "_drivers.json"))) {
            writer.println("{");
            writer.println("  \"drivers\": [");
            
            for (int i = 0; i < drivers.size(); i++) {
                Driver d = drivers.get(i);
                writer.println("    {");
                writer.println("      \"name\": \"" + escapeJson(d.getName()) + "\",");
                writer.println("      \"age\": " + d.getAge() + ",");
                writer.println("      \"gender\": \"" + d.getGender() + "\",");
                writer.println("      \"carCompany\": \"" + escapeJson(d.getCarCompany()) + "\",");
                writer.println("      \"carName\": \"" + escapeJson(d.getCarName()) + "\",");
                writer.println("      \"location\": \"" + escapeJson(d.getLocation()) + "\"");
                writer.print("    }");
                if (i < drivers.size() - 1) writer.println(",");
                else writer.println();
            }
            
            writer.println("  ]");
            writer.println("}");
            System.out.println("✅ Exported " + drivers.size() + " drivers to JSON");
        } catch (Exception e) {
            System.err.println("❌ Error exporting drivers: " + e.getMessage());
        }
    }
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    @Override
    public String getFormatName() {
        return "JSON (JavaScript Object Notation)";
    }
}