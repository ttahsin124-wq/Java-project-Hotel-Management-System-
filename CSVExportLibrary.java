
import java.util.ArrayList;
public class CSVExportLibrary 
{
    public void writeToCSV(String filename, String[][] data) 
    {
        StringBuilder sb = new StringBuilder();
        for (String[] row : data) {
            sb.append(String.join(",", row)).append("\n");
        }
        System.out.println("CSV Export: " + filename);
        System.out.println(sb.toString());
    }
    public String[][] convertCustomersToArray(ArrayList<Customer> customers) 
    {
        String[][] data = new String[customers.size() + 1][7];
       
        data[0] = new String[]{"ID", "Name", "Phone", "Email", "Room", "CheckIn", "CheckOut"};
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            data[i + 1] = new String[]{
                String.valueOf(c.getId()),
                c.getName(),
                c.getPhone(),
                c.getEmail(),
                c.getRoomNumber() != null ? String.valueOf(c.getRoomNumber()) : "N/A",
                c.getCheckIn().toString(),
                c.getCheckOut().toString()
            };
        }
        return data;
    }
}