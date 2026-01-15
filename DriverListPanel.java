import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class DriverListPanel extends JPanel 
{

    public DriverListPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);
        ArrayList<Driver> drivers = FileUtil.load("drivers.dat");
        String[] columns = {"Name", "Age", "Gender", "Car Company", "Car Name", "Location"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Driver d : drivers) 
        {
            model.addRow(new Object[]
            {
                d.name, d.age, d.gender, d.carCompany, d.carName, d.location
            });
        }
        JTable table = new JTable(model);
        table.setBackground(UITheme.CARD_BG);
        table.setForeground(UITheme.TEXT_COLOR);
        table.setGridColor(new Color(70, 70, 70));
        table.setRowHeight(35);
        table.setFont(UITheme.SMALL_FONT);
        JTableHeader header = table.getTableHeader();
        header.setBackground(UITheme.SECONDARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(UITheme.NORMAL_FONT);
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel title = new JLabel("Available Drivers");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(Color.WHITE);
        JLabel stats = new JLabel("Total: " + drivers.size() + " drivers");
        stats.setForeground(Color.WHITE);
        stats.setFont(UITheme.NORMAL_FONT);
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(stats, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}