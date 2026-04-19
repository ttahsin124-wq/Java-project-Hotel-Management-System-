
import java.util.ArrayList;
public class JSONExportLibrary {
    
    public void saveAsJSON(String filename, String jsonContent) {
        System.out.println("JSON Export: " + filename);
        System.out.println(jsonContent);
    }
    public String customersToJSON(ArrayList<Customer> customers) {
        StringBuilder json = new StringBuilder();
        json.append("{\n  \"customers\": [\n");
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            json.append("    {\n");
            json.append("      \"id\": ").append(c.getId()).append(",\n");
            json.append("      \"name\": \"").append(c.getName()).append("\",\n");
            json.append("      \"phone\": \"").append(c.getPhone()).append("\",\n");
            json.append("      \"email\": \"").append(c.getEmail()).append("\"\n");
            json.append("    }");
            if (i < customers.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("  ]\n}");
        return json.toString();
    }
}