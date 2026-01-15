import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class CustomerListPanel extends JPanel 
{

    private  JTable table;
    private  DefaultTableModel model;
    private ArrayList<Customer> customers;
    public CustomerListPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);
        customers = FileUtil.load("customers.dat");
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel title = new JLabel(" All Customers");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(Color.WHITE);
        JLabel stats = new JLabel("Total: " + customers.size() + " customers");
        stats.setForeground(Color.WHITE);
        stats.setFont(UITheme.NORMAL_FONT);
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(stats, BorderLayout.EAST);
        String[] columns = {"ID", "Name", "Phone", "Email", "Check-In", "Check-Out"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        loadTable();
        table.setBackground(UITheme.CARD_BG);
        table.setForeground(UITheme.TEXT_COLOR);
        table.setGridColor(new Color(70, 70, 70));
        table.setRowHeight(35);
        table.setFont(UITheme.SMALL_FONT);
        table.setSelectionBackground(UITheme.PRIMARY_COLOR);
        table.setSelectionForeground(Color.WHITE);
        JTableHeader header = table.getTableHeader();
        header.setBackground(UITheme.SECONDARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(UITheme.NORMAL_FONT);
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
    private void loadTable() 
    {
        model.setRowCount(0);
        customers = FileUtil.load("customers.dat");
        for (Customer c : customers) 
        {
            model.addRow(new Object[]
            {
                c.getId(), c.getName(), c.getPhone(), c.getEmail(),c.getCheckIn(), c.getCheckOut()
            });
        }
    }
}