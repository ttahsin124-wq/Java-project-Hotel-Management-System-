import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
public class RoomListPanel extends JPanel 
{

    public RoomListPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);
        ArrayList<HotelRoom> rooms = FileUtil.load("rooms.dat");
        String[] columns = {"Room No", "Category", "Price", "Available", "Cleaned"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (HotelRoom r : rooms) 
        {
            model.addRow(new Object[]
            {
                r.roomNo,
                r.category,
                String.format("$%.2f", r.price),
                r.available ? " Available" : " Occupied",
                r.cleaned ? "Clean" : "Needs Cleaning"
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
        JLabel title = new JLabel("Room Status Overview");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(Color.WHITE);
        JLabel stats = new JLabel("Total: " + rooms.size() + " rooms");
        stats.setForeground(Color.WHITE);
        stats.setFont(UITheme.NORMAL_FONT);
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(stats, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}