import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class RoomPanel extends JPanel 
{

    private DefaultTableModel model;
    private ArrayList<HotelRoom> rooms;
    private JTextField roomNoField, priceField;
    private JComboBox<String> categoryCombo;
    private JCheckBox availableCheck, cleanedCheck;
    private JButton addButton, editButton, deleteButton, clearButton;
    private int selectedRow = -1;

    public RoomPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);
        rooms = FileUtil.load("rooms.dat");
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.DARK_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel title = new JLabel("Room Management");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(Color.WHITE);
        headerPanel.add(title, BorderLayout.WEST);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450);
        splitPane.setBackground(UITheme.CARD_BG);
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        splitPane.setLeftComponent(createFormPanel());
        splitPane.setRightComponent(createTablePanel());
        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }
    private JPanel createFormPanel() 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.CARD_BG);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.PRIMARY_COLOR, 2),"Room Form",0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR));
        panel.setBorder(BorderFactory.createCompoundBorder(panel.getBorder(),BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        roomNoField = UITheme.createStyledTextField();
        priceField = UITheme.createStyledTextField();
        categoryCombo = UITheme.createStyledComboBox(new String[]{"SINGLE", "DOUBLE", "DELUXE", "SUITE"});
        availableCheck = new JCheckBox("Available");
        cleanedCheck = new JCheckBox("Cleaned");
        styleCheckBox(availableCheck);
        styleCheckBox(cleanedCheck);
        panel.add(createFormRow("Room No:", roomNoField));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Category:", categoryCombo));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Price ($):", priceField));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createCheckBoxRow("Status:", availableCheck, cleanedCheck));
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(UITheme.CARD_BG);
        addButton = UITheme.createStyledButton("Add Room", UITheme.SUCCESS_COLOR);
        editButton = UITheme.createStyledButton("Edit Room", UITheme.WARNING_COLOR);
        deleteButton = UITheme.createStyledButton("Delete Room", UITheme.DANGER_COLOR);
        clearButton = UITheme.createStyledButton("Clear Form", new Color(100, 100, 100));
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addButton.addActionListener(e -> addRoom());
        editButton.addActionListener(e -> editRoom());
        deleteButton.addActionListener(e -> deleteRoom());
        clearButton.addActionListener(e -> clearForm());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        panel.add(buttonPanel);
        return panel;
    }
    private JPanel createFormRow(String label, Component field) 
    {
        JPanel rowPanel = new JPanel(new BorderLayout(15, 0));
        rowPanel.setBackground(UITheme.CARD_BG);
        JLabel lbl = new JLabel(label);
        lbl.setForeground(UITheme.TEXT_COLOR);
        lbl.setFont(UITheme.NORMAL_FONT);
        lbl.setPreferredSize(new Dimension(100, 30));
        field.setPreferredSize(new Dimension(200, 35));
        rowPanel.add(lbl, BorderLayout.WEST);
        rowPanel.add(field, BorderLayout.CENTER);
        return rowPanel;
    }
    private JPanel createCheckBoxRow(String label, JCheckBox... checkBoxes) 
    {
        JPanel rowPanel = new JPanel(new BorderLayout(15, 0));
        rowPanel.setBackground(UITheme.CARD_BG);
        JLabel lbl = new JLabel(label);
        lbl.setForeground(UITheme.TEXT_COLOR);
        lbl.setFont(UITheme.NORMAL_FONT);
        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        checkPanel.setBackground(UITheme.CARD_BG);
        for (JCheckBox cb : checkBoxes) 
        {
            checkPanel.add(cb);
        }
        rowPanel.add(lbl, BorderLayout.WEST);
        rowPanel.add(checkPanel, BorderLayout.CENTER);
        return rowPanel;
    }
    private void styleCheckBox(JCheckBox checkBox) 
    {
        checkBox.setBackground(UITheme.CARD_BG);
        checkBox.setForeground(UITheme.TEXT_COLOR);
        checkBox.setFont(UITheme.NORMAL_FONT);
        checkBox.setFocusPainted(false);
    }
    private JScrollPane createTablePanel() 
    {
        String[] columns = {"Room No", "Category", "Price", "Available", "Cleaned"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };
        JTable table = new JTable(model);
        loadRoomsToTable();
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) 
            {
                selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) 
                {
                    loadRoomToForm(selectedRow);
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    addButton.setEnabled(false);
                } 
                else 
                {
                    clearForm();
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    addButton.setEnabled(true);
                }
            }
        });
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
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.SECONDARY_COLOR, 2),"Room List (" + rooms.size() + ")",0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR));
        return scrollPane;
    }
    private void addRoom() 
    {
        try 
        {
            int roomNo = Integer.parseInt(roomNoField.getText().trim());
            String category = (String) categoryCombo.getSelectedItem();
            double price = Double.parseDouble(priceField.getText().trim());
            boolean available = availableCheck.isSelected();
            boolean cleaned = cleanedCheck.isSelected();
            for (HotelRoom r : rooms) 
            {
                if (r.roomNo == roomNo) 
                {
                    JOptionPane.showMessageDialog(this,"Room number " + roomNo + " already exists!","Duplicate Room",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            RoomCategory cat = RoomCategory.valueOf(category);
            HotelRoom room = new HotelRoom(roomNo, cat, price, available, cleaned);
            rooms.add(room);
            FileUtil.save("rooms.dat", rooms);
            model.addRow(new Object[]{
                room.roomNo,
                room.category,
                String.format("$%.2f", room.price),
                room.available ? "Yes" : "NO",
                room.cleaned ? "Yes" : "No"
            });
            clearForm();
            JOptionPane.showMessageDialog(this, "Room added successfully!");
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void editRoom() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select a room to edit!");
            return;
        }
        
        try 
        {
            int roomNo = Integer.parseInt(roomNoField.getText().trim());
            String category = (String) categoryCombo.getSelectedItem();
            double price = Double.parseDouble(priceField.getText().trim());
            boolean available = availableCheck.isSelected();
            boolean cleaned = cleanedCheck.isSelected();
            HotelRoom room = rooms.get(selectedRow);
            room.roomNo = roomNo;
            room.category = RoomCategory.valueOf(category);
            room.price = price;
            room.available = available;
            room.cleaned = cleaned;
            FileUtil.save("rooms.dat", rooms);
            model.setValueAt(room.roomNo, selectedRow, 0);
            model.setValueAt(room.category, selectedRow, 1);
            model.setValueAt(String.format("$%.2f", room.price), selectedRow, 2);
            model.setValueAt(room.available ? "Yes" : "No", selectedRow, 3);
            model.setValueAt(room.cleaned ? "Yes" : "No", selectedRow, 4);
            clearForm();
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Room updated successfully!");
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteRoom() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select a room to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this room?\nThis action cannot be undone.","Confirm Deletion",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) 
        {
            HotelRoom deletedRoom = rooms.remove(selectedRow);
            FileUtil.save("rooms.dat", rooms);
            model.removeRow(selectedRow);
            selectedRow = -1;
            clearForm();
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Room " + deletedRoom.roomNo + " deleted successfully!");
        }
    }

    private void loadRoomToForm(int row) 
    {
        if (row >= 0 && row < rooms.size()) 
        {
            HotelRoom room = rooms.get(row);
            roomNoField.setText(String.valueOf(room.roomNo));
            priceField.setText(String.valueOf(room.price));
            categoryCombo.setSelectedItem(room.category.toString());
            availableCheck.setSelected(room.available);
            cleanedCheck.setSelected(room.cleaned);
        }
    }

    private void clearForm() 
    {
        roomNoField.setText("");
        priceField.setText("");
        categoryCombo.setSelectedIndex(0);
        availableCheck.setSelected(true);
        cleanedCheck.setSelected(true);
        roomNoField.requestFocus();
        selectedRow = -1;
    }

    private void loadRoomsToTable() 
    {
        model.setRowCount(0);
        rooms = FileUtil.load("rooms.dat");
        for (HotelRoom room : rooms) 
        {
            model.addRow(new Object[]{
                room.roomNo,
                room.category,
                String.format("$%.2f", room.price),
                room.available ? "Yes" : "No",
                room.cleaned ? "Yes" : "No"
            });
        }
    }
}