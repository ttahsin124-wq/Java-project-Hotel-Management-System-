import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;

public class ReservationPanel extends JPanel 
{

    private DefaultTableModel model;
    private ArrayList<Reservation> reservations;
    private ArrayList<Customer> customers;
    private ArrayList<HotelRoom> rooms;
    private JComboBox<Customer> customerCombo;
    private JComboBox<HotelRoom> roomCombo;
    private JSpinner checkInSpinner, checkOutSpinner;
    private JTextField amountField;
    private JTextArea requestsArea;
    private JComboBox<String> statusCombo;
    private JButton addButton, editButton, deleteButton, clearButton;
    private int selectedRow = -1;
    private HotelRoom selectedRoomForEditing = null; 
    public ReservationPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);
        reservations = FileUtil.load("reservations.dat");
        customers = FileUtil.load("customers.dat");
        rooms = FileUtil.load("rooms.dat");
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.DARK_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel title = new JLabel("Reservation Management");
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
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UITheme.PRIMARY_COLOR, 2),"Reservation Form",0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR));
        panel.setBorder(BorderFactory.createCompoundBorder(panel.getBorder(),BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        customerCombo = new JComboBox<>();
        updateCustomerCombo();
        roomCombo = new JComboBox<>();
        updateRoomCombo(true); 
        checkInSpinner = new JSpinner(new SpinnerDateModel());
        checkOutSpinner = new JSpinner(new SpinnerDateModel());
        styleSpinner(checkInSpinner);
        styleSpinner(checkOutSpinner);
        amountField = UITheme.createStyledTextField();
        statusCombo = UITheme.createStyledComboBox(
            new String[]{"CONFIRMED", "CHECKED_IN", "CHECKED_OUT", "CANCELLED"}
        );
        requestsArea = new JTextArea(3, 20);
        requestsArea.setBackground(UITheme.CARD_BG);
        requestsArea.setForeground(UITheme.TEXT_COLOR);
        requestsArea.setFont(UITheme.NORMAL_FONT);
        requestsArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel.add(createFormRow("Customer:", customerCombo));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Room:", roomCombo));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Check-in:", checkInSpinner));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Check-out:", checkOutSpinner));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Total Amount:", amountField));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Status:", statusCombo));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Special Requests:", new JScrollPane(requestsArea)));
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(UITheme.CARD_BG);
        addButton = UITheme.createStyledButton("Add Reservation", UITheme.SUCCESS_COLOR);
        editButton = UITheme.createStyledButton("Edit Reservation", UITheme.WARNING_COLOR);
        deleteButton = UITheme.createStyledButton("Delete Reservation", UITheme.DANGER_COLOR);
        clearButton = UITheme.createStyledButton("Clear Form", new Color(100, 100, 100));
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addButton.addActionListener(e -> addReservation());
        editButton.addActionListener(e -> editReservation());
        deleteButton.addActionListener(e -> deleteReservation());
        clearButton.addActionListener(e -> clearForm());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        panel.add(buttonPanel);
        return panel;
    }

    private void updateCustomerCombo() 
    {
        customerCombo.removeAllItems();
        customers = FileUtil.load("customers.dat");
        for (Customer customer : customers) 
        {
            customerCombo.addItem(customer);
        }
        styleComboBox(customerCombo);
    }

    private void updateRoomCombo(boolean forAddMode) 
    {
        roomCombo.removeAllItems();
        rooms = FileUtil.load("rooms.dat");
        if (!forAddMode && selectedRoomForEditing != null) 
        {
            roomCombo.addItem(selectedRoomForEditing);
        }
        for (HotelRoom room : rooms) 
        {
            if (room.available) 
            {
                boolean alreadyAdded = false;
                if (!forAddMode && selectedRoomForEditing != null) 
                {
                    alreadyAdded = (room.roomNo == selectedRoomForEditing.roomNo);
                }
                if (!alreadyAdded) 
                {
                    roomCombo.addItem(room);
                }
            }
        }
        styleComboBox(roomCombo);
    }
    private JPanel createFormRow(String label, Component field) 
    {
        JPanel rowPanel = new JPanel(new BorderLayout(15, 0));
        rowPanel.setBackground(UITheme.CARD_BG);
        JLabel lbl = new JLabel(label);
        lbl.setForeground(UITheme.TEXT_COLOR);
        lbl.setFont(UITheme.NORMAL_FONT);
        lbl.setPreferredSize(new Dimension(120, 30));
        field.setPreferredSize(new Dimension(200, 35));
        if (field instanceof JScrollPane) 
        {
            field.setPreferredSize(new Dimension(200, 80));
        }
        rowPanel.add(lbl, BorderLayout.WEST);
        rowPanel.add(field, BorderLayout.CENTER);
        return rowPanel;
    }

    private void styleComboBox(JComboBox<?> comboBox) 
    {
        comboBox.setBackground(UITheme.CARD_BG);
        comboBox.setForeground(UITheme.TEXT_COLOR);
        comboBox.setFont(UITheme.NORMAL_FONT);
        comboBox.setRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) 
                {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? UITheme.PRIMARY_COLOR : UITheme.CARD_BG);
                label.setForeground(UITheme.TEXT_COLOR);
                if (value instanceof Customer) 
                {
                    Customer customer = (Customer) value;
                    label.setText(customer.getName() + " (ID: " + customer.getId() + ")");
                } 
                else if (value instanceof HotelRoom) 
                {
                    HotelRoom room = (HotelRoom) value;
                    String availability = room.available ? "Available" : "Occupied";
                    label.setText("Room " + room.roomNo + " - " + room.category + " ($" + room.price + ") [" + availability + "]");
                }
                return label;
            }
        });
    }

    private void styleSpinner(JSpinner spinner) 
    {
        spinner.setBackground(UITheme.CARD_BG);
        spinner.setForeground(UITheme.TEXT_COLOR);
        spinner.setFont(UITheme.NORMAL_FONT);
        JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        tf.setBackground(UITheme.CARD_BG);
        tf.setForeground(UITheme.TEXT_COLOR);
        tf.setCaretColor(UITheme.TEXT_COLOR);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private JScrollPane createTablePanel() 
    {
        String[] columns = {"Reservation ID", "Customer", "Room", "Check-in", "Check-out", "Amount", "Status"};
        model = new DefaultTableModel(columns, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };
        JTable table = new JTable(model);
        loadReservationsToTable();
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) 
            {
                selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) 
                {
                    loadReservationToForm(selectedRow);
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
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.SECONDARY_COLOR, 2),"Reservation List (" + reservations.size() + ")",0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR));
        return scrollPane;
    }

    private void addReservation() 
    {
        try 
        {
            Customer customer = (Customer) customerCombo.getSelectedItem();
            HotelRoom room = (HotelRoom) roomCombo.getSelectedItem();
            Date checkIn = (Date) checkInSpinner.getValue();
            Date checkOut = (Date) checkOutSpinner.getValue();
            double amount = Double.parseDouble(amountField.getText().trim());
            String status = (String) statusCombo.getSelectedItem();
            String requests = requestsArea.getText().trim();
            if (customer == null) 
            {
                JOptionPane.showMessageDialog(this, "Please select a customer!");
                return;
            }
            if (room == null) 
            {
                JOptionPane.showMessageDialog(this, "Please select a room!");
                return;
            }
            if (!room.available) 
            {
                JOptionPane.showMessageDialog(this, "Selected room is not available! Please choose another room.");
                return;
            }
            if (checkOut.before(checkIn)) 
            {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date!");
                return;
            }
            if (amount <= 0) 
            {
                JOptionPane.showMessageDialog(this, "Amount must be positive!");
                return;
            }
            int newId = 1;
            if (!reservations.isEmpty()) 
            {
                for (Reservation r : reservations) 
                {
                    if (r.getReservationId() > newId) 
                    {
                        newId = r.getReservationId();
                    }
                }
                newId++; 
            }
            Reservation reservation = new Reservation(newId, customer, room, checkIn, checkOut, amount, status, requests);
            room.available = false;
            FileUtil.save("rooms.dat", rooms);
            reservations.add(reservation);
            FileUtil.save("reservations.dat", reservations);
            customer.setRoomNumber(room.roomNo);
            FileUtil.save("customers.dat", customers);
            model.addRow(new Object[]{
                reservation.getReservationId(),
                customer.getName(),
                "Room " + room.roomNo,
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                String.format("$%.2f", reservation.getTotalAmount()),
                reservation.getStatus()
            });
            clearForm();
            updateRoomCombo(true); 
            JOptionPane.showMessageDialog(this, "Reservation added successfully! ID: " + newId);
            
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Please enter valid amount!");
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void editReservation() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select a reservation to edit!");
            return;
        }
        
        try 
        {
            Reservation reservation = reservations.get(selectedRow);
            Customer customer = (Customer) customerCombo.getSelectedItem();
            HotelRoom room = (HotelRoom) roomCombo.getSelectedItem();
            Date checkIn = (Date) checkInSpinner.getValue();
            Date checkOut = (Date) checkOutSpinner.getValue();
            double amount = Double.parseDouble(amountField.getText().trim());
            String status = (String) statusCombo.getSelectedItem();
            String requests = requestsArea.getText().trim();
            if (customer == null) 
            {
                JOptionPane.showMessageDialog(this, "Please select a customer!");
                return;
            }
            if (room == null) 
            {
                JOptionPane.showMessageDialog(this, "Please select a room!");
                return;
            }
            
            if (checkOut.before(checkIn)) 
            {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date!");
                return;
            }
            HotelRoom oldRoom = reservation.getRoom();
            reservation.setCheckIn(checkIn);
            reservation.setCheckOut(checkOut);
            reservation.setTotalAmount(amount);
            reservation.setStatus(status);
            reservation.setSpecialRequests(requests);
            reservation.setRoom(room); 
            reservation.getCustomer().setRoomNumber(room.roomNo);
            if (oldRoom.roomNo != room.roomNo) 
            {
                oldRoom.available = true;
                room.available = false;
            }
            FileUtil.save("rooms.dat", rooms);
            FileUtil.save("customers.dat", customers);
            FileUtil.save("reservations.dat", reservations);
            model.setValueAt(reservation.getReservationId(), selectedRow, 0);
            model.setValueAt(customer.getName(), selectedRow, 1);
            model.setValueAt("Room " + room.roomNo, selectedRow, 2);
            model.setValueAt(reservation.getCheckIn(), selectedRow, 3);
            model.setValueAt(reservation.getCheckOut(), selectedRow, 4);
            model.setValueAt(String.format("$%.2f", reservation.getTotalAmount()), selectedRow, 5);
            model.setValueAt(reservation.getStatus(), selectedRow, 6);
            clearForm();
            updateRoomCombo(true); 
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Reservation updated successfully!");
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Please enter valid amount!");
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteReservation() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select a reservation to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this reservation?\nThis action cannot be undone.","Confirm Deletion",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) 
            {
            Reservation reservation = reservations.remove(selectedRow);
            reservation.getRoom().available = true;
            FileUtil.save("rooms.dat", rooms);
            reservation.getCustomer().setRoomNumber(0);
            FileUtil.save("customers.dat", customers);
            FileUtil.save("reservations.dat", reservations);
            model.removeRow(selectedRow);
            selectedRow = -1;
            clearForm();
            updateRoomCombo(true); 
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, 
                "Reservation #" + reservation.getReservationId() + " deleted successfully!");
        }
    }

    private void loadReservationToForm(int row) 
    {
        if (row >= 0 && row < reservations.size()) 
        {
            Reservation reservation = reservations.get(row);
            selectedRoomForEditing = reservation.getRoom();
            Customer reservationCustomer = reservation.getCustomer();
            for (int i = 0; i < customerCombo.getItemCount(); i++) 
            {
                Customer comboCustomer = customerCombo.getItemAt(i);
                if (comboCustomer.getId() == reservationCustomer.getId()) 
                {
                    customerCombo.setSelectedIndex(i);
                    break;
                }
            }
            updateRoomCombo(false);
            for (int i = 0; i < roomCombo.getItemCount(); i++) 
            {
                HotelRoom comboRoom = roomCombo.getItemAt(i);
                if (comboRoom.roomNo == selectedRoomForEditing.roomNo) 
                {
                    roomCombo.setSelectedIndex(i);
                    break;
                }
            }
            
            checkInSpinner.setValue(reservation.getCheckIn());
            checkOutSpinner.setValue(reservation.getCheckOut());
            amountField.setText(String.valueOf(reservation.getTotalAmount()));
            statusCombo.setSelectedItem(reservation.getStatus());
            requestsArea.setText(reservation.getSpecialRequests());
        }
    }

    private void clearForm() 
    {
        if (customerCombo.getItemCount() > 0) 
        {
            customerCombo.setSelectedIndex(0);
        }
        selectedRoomForEditing = null;
        updateRoomCombo(true); 
        checkInSpinner.setValue(new Date());
        checkOutSpinner.setValue(new Date(System.currentTimeMillis() + 86400000));
        amountField.setText("");
        statusCombo.setSelectedIndex(0);
        requestsArea.setText("");
        selectedRow = -1;
    }

    private void loadReservationsToTable() 
    {
        model.setRowCount(0);
        reservations = FileUtil.load("reservations.dat");
        for (Reservation reservation : reservations) 
        {
            model.addRow(new Object[]{
                reservation.getReservationId(),
                reservation.getCustomer().getName(),
                "Room " + reservation.getRoom().roomNo,
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                String.format("$%.2f", reservation.getTotalAmount()),
                reservation.getStatus()
            });
        }
    }
}