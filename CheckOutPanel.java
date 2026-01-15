import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class CheckOutPanel extends JPanel 
{

    private JTable table;
    private DefaultTableModel model;
    private ArrayList<Customer> customers;

    public CheckOutPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);

        customers = FileUtil.load("customers.dat");
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.DANGER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel title = new JLabel("Guest Check-Out");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(Color.WHITE);
        
        headerPanel.add(title, BorderLayout.WEST);

        
        String[] columns = {"ID", "Name", "Phone", "Email", "Check-In", "Check-Out"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        loadTable();
        table.setBackground(UITheme.CARD_BG);
        table.setForeground(UITheme.TEXT_COLOR);
        table.setGridColor(new Color(70, 70, 70));
        table.setRowHeight(35);
        table.setFont(UITheme.SMALL_FONT);
        table.setSelectionBackground(UITheme.DANGER_COLOR);
        table.setSelectionForeground(Color.WHITE);
        JTableHeader header = table.getTableHeader();
        header.setBackground(UITheme.SECONDARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(UITheme.NORMAL_FONT);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(UITheme.CARD_BG);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnCheckOut = UITheme.createStyledButton("Check Out Selected Guest", UITheme.DANGER_COLOR);
        JButton btnRefresh = UITheme.createStyledButton("Refresh List", UITheme.WARNING_COLOR);
        
        btnCheckOut.addActionListener(e -> checkOutCustomer());
        btnRefresh.addActionListener(e -> loadTable());
        
        btnPanel.add(btnCheckOut);
        btnPanel.add(btnRefresh);

        add(headerPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadTable() 
    {
        model.setRowCount(0);
        customers = FileUtil.load("customers.dat");
        
        for (Customer c : customers) 
        {
            model.addRow(new Object[]{
                c.getId(), c.getName(), c.getPhone(), c.getEmail(),c.getCheckIn(), c.getCheckOut()
            });
        }
    }

    private void checkOutCustomer() 
    {
        int row = table.getSelectedRow();
        if (row >= 0) 
        {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to check out " + customers.get(row).getName() + "?\n" +
                "This action will remove them from the system.",
                "Confirm Check-Out",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
            if (confirm == JOptionPane.YES_OPTION) 
            {
                Customer removedCustomer = customers.remove(row);
                FileUtil.save("customers.dat", customers);
                loadTable();
                
                JOptionPane.showMessageDialog(this,
                    "✅ " + removedCustomer.getName() + " has been checked out successfully!",
                    "Check-Out Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        else 
        {
            JOptionPane.showMessageDialog(this,
                "Please select a customer to check out!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}