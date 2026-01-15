import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class UpdateRoomStatusPanel extends JPanel 
{
    private ArrayList<HotelRoom> rooms;
    private final DefaultTableModel model;
    public UpdateRoomStatusPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);
        rooms = FileUtil.load("rooms.dat");
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.WARNING_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel title = new JLabel("Update Room Status");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(Color.WHITE);
        headerPanel.add(title, BorderLayout.WEST);
        String[] columns = {"Room No", "Category", "Available", "Cleaned"};
        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        loadTable();
        table.setBackground(UITheme.CARD_BG);
        table.setForeground(UITheme.TEXT_COLOR);
        table.setGridColor(new Color(70, 70, 70));
        table.setRowHeight(35);
        table.setFont(UITheme.SMALL_FONT);
        JTableHeader header = table.getTableHeader();
        header.setBackground(UITheme.SECONDARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(UITheme.NORMAL_FONT);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(UITheme.CARD_BG);
        JButton btnToggleAvailable = UITheme.createStyledButton("Toggle Availability", UITheme.PRIMARY_COLOR);
        JButton btnToggleCleaned = UITheme.createStyledButton(" Toggle Cleaned", UITheme.SUCCESS_COLOR);
        JButton btnRefresh = UITheme.createStyledButton("Refresh", UITheme.WARNING_COLOR);
        btnToggleAvailable.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) 
            {
                HotelRoom r = rooms.get(row);
                r.available = !r.available;
                FileUtil.save("rooms.dat", rooms);
                loadTable();
                JOptionPane.showMessageDialog(this, "Room " + r.roomNo + " availability updated!");
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Please select a room first!");
            }
        });
        btnToggleCleaned.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) 
            {
                HotelRoom r = rooms.get(row);
                r.cleaned = !r.cleaned;
                FileUtil.save("rooms.dat", rooms);
                loadTable();
                JOptionPane.showMessageDialog(this, "Room " + r.roomNo + " cleaning status updated!");
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Please select a room first!");
            }
        });
        btnRefresh.addActionListener(e -> loadTable());
        bottomPanel.add(btnToggleAvailable);
        bottomPanel.add(btnToggleCleaned);
        bottomPanel.add(btnRefresh);
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    private void loadTable() 
    {
        model.setRowCount(0);
        rooms = FileUtil.load("rooms.dat");
        for (HotelRoom r : rooms) {
            model.addRow(new Object[]{
                r.roomNo,
                r.category,
                r.available ? "Available" : " Occupied",
                r.cleaned ? "Clean" : "Needs Cleaning"
            });
        }
    }
}