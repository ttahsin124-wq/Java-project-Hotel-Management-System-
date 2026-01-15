import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReceptionDashboard extends JFrame 
{
    
    private  JPanel mainContentPanel;
    private CardLayout cardLayout;
    private JLabel welcomeLabel;
    public ReceptionDashboard() 
    {
        UITheme.apply();
        setTitle("Reception Panel - Hotel Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UITheme.DARK_BG);
        JPanel headerPanel = createHeader();
        JPanel sidebarPanel = createSidebar();
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(UITheme.CARD_BG);
        initializeContentPanels();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }
    private JPanel createHeader() 
    {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.DARK_BG);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel title = new JLabel("Reception Panel");
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(Color.WHITE);
        welcomeLabel = new JLabel("Welcome, Receptionist!");
        welcomeLabel.setFont(UITheme.NORMAL_FONT);
        welcomeLabel.setForeground(Color.WHITE);
        JButton logoutBtn = createStyledButton("Logout", UITheme.DANGER_COLOR);
        logoutBtn.addActionListener(e -> 
            {
            int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you want to logout?","Confirm Logout",JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) 
            {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(title);
        leftPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        leftPanel.add(welcomeLabel);
        header.add(leftPanel, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        return header;
    }
    private JPanel createSidebar() 
    {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.SECONDARY_COLOR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JLabel quickActions = new JLabel("Quick Actions");
        quickActions.setFont(UITheme.HEADER_FONT);
        quickActions.setForeground(UITheme.TEXT_COLOR);
        quickActions.setAlignmentX(Component.LEFT_ALIGNMENT);
        quickActions.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        sidebar.add(quickActions);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        String[] buttons = {
            "New Customer Registration",
            "Make Reservation",
            "Check-in Guest",
            "Check-out Guest",
            "Search Customer",
            "View Reservations",
            "View Room Status",
            "Pick-up Service",
            "Update Room Status",
            "View All Customers",
            "View Reports",
            "Settings"
        };
        String[] cardNames = {
            "newCustomer", "makeReservation", "checkIn", "checkOut", "searchCustomer", "viewReservations", "roomStatus", "pickup", "updateRoom", "allCustomers", "reports", "settings"
        };
        
        for (int i = 0; i < buttons.length; i++) 
        {
            JButton btn = createSidebarButton(buttons[i], cardNames[i]);
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }
    private JButton createSidebarButton(String text, String cardName) 
    {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(UITheme.CARD_BG);
        button.setForeground(UITheme.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setFont(UITheme.SMALL_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                cardLayout.show(mainContentPanel, cardName);
                highlightActiveButton(button);
            }
        });
        button.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                if (!button.getBackground().equals(UITheme.SUCCESS_COLOR)) 
                {
                    button.setBackground(UITheme.SUCCESS_COLOR.darker());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                if (!button.getBackground().equals(UITheme.SUCCESS_COLOR)) 
                {
                    button.setBackground(UITheme.CARD_BG);
                }
            }
        });
        return button;
    }
    private JButton createStyledButton(String text, Color bg) 
    {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(UITheme.NORMAL_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(bg.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(bg);
            }
        });
        return button;
    }
    private void highlightActiveButton(JButton activeButton) 
    {
        
        for (Component comp : ((JPanel)activeButton.getParent()).getComponents()) 
        {
            if (comp instanceof JButton) 
            {
                JButton btn = (JButton) comp;
                if (!btn.getText().equals("Quick Actions")) 
                {
                    btn.setBackground(UITheme.CARD_BG);
                }
            }
        }
        activeButton.setBackground(UITheme.SUCCESS_COLOR);
    }
    private void initializeContentPanels() 
    {
        CustomerFormPanel customerPanel = new CustomerFormPanel();
        mainContentPanel.add(customerPanel, "newCustomer");
        ReservationPanel reservationPanel = new ReservationPanel();
        mainContentPanel.add(reservationPanel, "makeReservation");
        JPanel checkInPanel = createCheckInPanel();
        mainContentPanel.add(checkInPanel, "checkIn");
        CheckOutPanel checkOutPanel = new CheckOutPanel();
        mainContentPanel.add(checkOutPanel, "checkOut");
        SearchCustomerPanel searchPanel = new SearchCustomerPanel();
        mainContentPanel.add(searchPanel, "searchCustomer");
        ViewReservationsPanel reservationsPanel = new ViewReservationsPanel();
        mainContentPanel.add(reservationsPanel, "viewReservations");
        RoomListPanel roomPanel = new RoomListPanel();
        mainContentPanel.add(roomPanel, "roomStatus");
        DriverListPanel pickupPanel = new DriverListPanel();
        mainContentPanel.add(pickupPanel, "pickup");
        UpdateRoomStatusPanel updateRoomPanel = new UpdateRoomStatusPanel();
        mainContentPanel.add(updateRoomPanel, "updateRoom");
        CustomerListPanel allCustomersPanel = new CustomerListPanel();
        mainContentPanel.add(allCustomersPanel, "allCustomers");
        JPanel reportsPanel = createReceptionReportsPanel();
        mainContentPanel.add(reportsPanel, "reports");
        JPanel settingsPanel = createReceptionSettingsPanel();
        mainContentPanel.add(settingsPanel, "settings");
    }
    class SearchCustomerPanel extends JPanel 
    {
        private JTextField txtSearch;
        private JComboBox<String> searchTypeCombo;
        public SearchCustomerPanel() 
        {
            setLayout(new BorderLayout());
            setBackground(UITheme.CARD_BG);
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(UITheme.DARK_BG);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            JLabel title = new JLabel("Search Customer");
            title.setFont(UITheme.HEADER_FONT);
            title.setForeground(Color.WHITE);
            headerPanel.add(title, BorderLayout.WEST);
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            searchPanel.setBackground(UITheme.CARD_BG);
            searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            searchTypeCombo = new JComboBox<>(new String[]{"By Name", "By Phone", "By Email", "By Room"});
            searchTypeCombo.setBackground(UITheme.CARD_BG);
            searchTypeCombo.setForeground(UITheme.TEXT_COLOR);
            txtSearch = new JTextField(20);
            txtSearch.setBackground(UITheme.CARD_BG);
            txtSearch.setForeground(UITheme.TEXT_COLOR);
            txtSearch.setCaretColor(UITheme.TEXT_COLOR);
            JButton btnSearch = createStyledButton("Search", UITheme.PRIMARY_COLOR);
            JButton btnClear = createStyledButton("Clear", new Color(100, 100, 100));
            searchPanel.add(new JLabel("Search Type:"));
            searchPanel.add(searchTypeCombo);
            searchPanel.add(new JLabel("Search Text:"));
            searchPanel.add(txtSearch);
            searchPanel.add(btnSearch);
            searchPanel.add(btnClear);
            JTextArea resultsArea = new JTextArea(15, 50);
            resultsArea.setBackground(UITheme.CARD_BG);
            resultsArea.setForeground(UITheme.TEXT_COLOR);
            resultsArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultsArea);
            btnSearch.addActionListener(e -> {
                String searchText = txtSearch.getText().trim();
                String searchType = (String) searchTypeCombo.getSelectedItem();
                if (searchText.isEmpty()) 
                {
                    resultsArea.setText("Please enter search text!");
                    return;
                }
                ArrayList<Customer> customers = FileUtil.load("customers.dat");
                StringBuilder results = new StringBuilder();
                results.append("Search Results for '").append(searchText).append("' (").append(searchType).append("):\n");
                results.append("=".repeat(50)).append("\n\n");
                int found = 0;
                for (Customer customer : customers) 
                {
                    boolean match = false;
                    switch (searchType) 
                    {
                        case "By Name":
                            match = customer.getName().toLowerCase().contains(searchText.toLowerCase());
                            break;
                        case "By Phone":
                            match = customer.getPhone().contains(searchText);
                            break;
                        case "By Email":
                            match = customer.getEmail().toLowerCase().contains(searchText.toLowerCase());
                            break;
                        case "By Room":
                            if (customer.getRoomNumber() != null) 
                            {
                                match = customer.getRoomNumber().toString().contains(searchText);
                            }
                            break;
                    }
                    
                    if (match) 
                    {
                        found++;
                        results.append("Customer ID: ").append(customer.getId()).append("\n");
                        results.append("Name: ").append(customer.getName()).append("\n");
                        results.append("Phone: ").append(customer.getPhone()).append("\n");
                        results.append("Email: ").append(customer.getEmail()).append("\n");
                        results.append("Room: ").append(customer.getRoomNumber() != null ? customer.getRoomNumber() : "Not assigned").append("\n");
                        results.append("Check-in: ").append(customer.getCheckIn()).append("\n");
                        results.append("Check-out: ").append(customer.getCheckOut()).append("\n");
                        results.append("-".repeat(50)).append("\n");
                    }
                }
                
                if (found == 0) 
                {
                    results.append("No customers found.\n");
                } 
                else 
                {
                    results.append("\nTotal found: ").append(found).append(" customer(s)\n");
                }
                
                resultsArea.setText(results.toString());
            });
            
            btnClear.addActionListener(e -> {
                txtSearch.setText("");
                resultsArea.setText("");
            });
            
            add(headerPanel, BorderLayout.NORTH);
            add(searchPanel, BorderLayout.CENTER);
            add(scrollPane, BorderLayout.SOUTH);
        }
    }
    class ViewReservationsPanel extends JPanel 
    {
        private final JTextArea reservationsArea;
        public ViewReservationsPanel() 
        {
            setLayout(new BorderLayout());
            setBackground(UITheme.CARD_BG);
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(UITheme.DARK_BG);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            JLabel title = new JLabel("All Reservations");
            title.setFont(UITheme.HEADER_FONT);
            title.setForeground(Color.WHITE);
            JButton refreshBtn = createStyledButton("Refresh", UITheme.PRIMARY_COLOR);
            refreshBtn.addActionListener(e -> loadReservations());
            headerPanel.add(title, BorderLayout.WEST);
            headerPanel.add(refreshBtn, BorderLayout.EAST);
            reservationsArea = new JTextArea();
            reservationsArea.setBackground(UITheme.CARD_BG);
            reservationsArea.setForeground(UITheme.TEXT_COLOR);
            reservationsArea.setEditable(false);
            reservationsArea.setFont(UITheme.NORMAL_FONT);
            JScrollPane scrollPane = new JScrollPane(reservationsArea);
            add(headerPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            loadReservations();
        }
        private void loadReservations() 
        {
            ArrayList<Reservation> reservations = FileUtil.load("reservations.dat");
            StringBuilder sb = new StringBuilder();
            sb.append("TOTAL RESERVATIONS: ").append(reservations.size()).append("\n");
            sb.append("=".repeat(80)).append("\n\n");
            if (reservations.isEmpty()) 
            {
                sb.append("No reservations found.\n");
            } 
            else 
            {
                for (Reservation res : reservations) 
                {
                    sb.append("Reservation ID: ").append(res.getReservationId()).append("\n");
                    sb.append("Customer: ").append(res.getCustomer().getName()).append("\n");
                    sb.append("Room: ").append(res.getRoom().roomNo).append("\n");
                    sb.append("Check-in: ").append(res.getCheckIn()).append("\n");
                    sb.append("Check-out: ").append(res.getCheckOut()).append("\n");
                    sb.append("Amount: $").append(String.format("%.2f", res.getTotalAmount())).append("\n");
                    sb.append("Status: ").append(res.getStatus()).append("\n");
                    sb.append("Special Requests: ").append(res.getSpecialRequests()).append("\n");
                    sb.append("-".repeat(80)).append("\n");
                }
            }
            reservationsArea.setText(sb.toString());
        }
    }
    
    private JPanel createCheckInPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CARD_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("Guest Check-in");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(UITheme.TEXT_COLOR);
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UITheme.CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField reservationIdField = new JTextField();
        reservationIdField.setBackground(UITheme.CARD_BG);
        reservationIdField.setForeground(UITheme.TEXT_COLOR);
        JTextField customerNameField = new JTextField();
        customerNameField.setBackground(UITheme.CARD_BG);
        customerNameField.setForeground(UITheme.TEXT_COLOR);
        customerNameField.setEditable(false);
        JTextField roomField = new JTextField();
        roomField.setBackground(UITheme.CARD_BG);
        roomField.setForeground(UITheme.TEXT_COLOR);
        roomField.setEditable(false);
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createFormLabel("Reservation ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(reservationIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createFormLabel("Customer Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(customerNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createFormLabel("Room Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(roomField, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton findBtn = createStyledButton("Find Reservation", UITheme.PRIMARY_COLOR);
        findBtn.addActionListener(e -> {
            String resId = reservationIdField.getText().trim();
            if (resId.isEmpty()) 
            {
                JOptionPane.showMessageDialog(panel, "Please enter reservation ID!");
                return;
            }
            try 
            {
                int reservationId = Integer.parseInt(resId);
                ArrayList<Reservation> reservations = FileUtil.load("reservations.dat");
                boolean found = false;
                for (Reservation res : reservations) 
                {
                    if (res.getReservationId() == reservationId) 
                    {
                        found = true;
                        customerNameField.setText(res.getCustomer().getName());
                        roomField.setText(String.valueOf(res.getRoom().roomNo));
                        if (res.getStatus().equals("CHECKED_IN")) 
                        {
                            JOptionPane.showMessageDialog(panel, "This guest is already checked in!");
                        }
                        break;
                    }
                }
                if (!found) 
                {
                    JOptionPane.showMessageDialog(panel,  "Reservation not found with ID: " + reservationId);
                }
                
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(panel, "Please enter valid reservation ID!");
            }
        });
        formPanel.add(findBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton checkInBtn = createStyledButton("Check-in Guest", UITheme.SUCCESS_COLOR);
        checkInBtn.addActionListener(e -> {
            String resId = reservationIdField.getText().trim();
            if (resId.isEmpty()) 
            {
                JOptionPane.showMessageDialog(panel, "Please find reservation first!");
                return;
            }
            try 
            {
                int reservationId = Integer.parseInt(resId);
                ArrayList<Reservation> reservations = FileUtil.load("reservations.dat");
                boolean updated = false;
                for (Reservation res : reservations) 
                {
                    if (res.getReservationId() == reservationId) 
                    {
                        if (res.getStatus().equals("CONFIRMED")) 
                        {
                            res.setStatus("CHECKED_IN");
                            FileUtil.save("reservations.dat", reservations);
                            updated = true;
                            JOptionPane.showMessageDialog(panel, "Guest checked in successfully!\n" +"Customer: " + res.getCustomer().getName() + "\n" +"Room: " + res.getRoom().roomNo);
                            reservationIdField.setText("");
                            customerNameField.setText("");
                            roomField.setText("");
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(panel, "Cannot check-in. Reservation status is: " + res.getStatus());
                        }
                        break;
                    }
                }
                if (!updated) 
                {
                    JOptionPane.showMessageDialog(panel, "Check-in failed!");
                }
                
            } 
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });
        formPanel.add(checkInBtn, gbc);
        panel.add(title, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createReceptionReportsPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CARD_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("Reception Reports");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(UITheme.TEXT_COLOR);
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBackground(UITheme.CARD_BG);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        ArrayList<Customer> customers = FileUtil.load("customers.dat");
        ArrayList<Reservation> reservations = FileUtil.load("reservations.dat");
        ArrayList<HotelRoom> rooms = FileUtil.load("rooms.dat");
        int checkedInCount = 0;
        for (Reservation res : reservations) 
        {
            if (res.getStatus().equals("CHECKED_IN"))
            {
                checkedInCount++;
            }
        }
        int availableRooms = 0;
        for (HotelRoom room : rooms) 
        {
            if (room.available) 
            {
                availableRooms++;
            }
        }
        int pendingCheckouts = 0;
        for (Reservation res : reservations) 
        {
            if (res.getStatus().equals("CHECKED_IN")) 
            {
                pendingCheckouts++;
            }
        }
        int occupancyRate = rooms.size() > 0 ?  ((rooms.size() - availableRooms) * 100) / rooms.size() : 0;
        
        statsPanel.add(createMiniStatCard("Checked-in", String.valueOf(checkedInCount), UITheme.SUCCESS_COLOR));
        statsPanel.add(createMiniStatCard("Available Rooms", String.valueOf(availableRooms), UITheme.PRIMARY_COLOR));
        statsPanel.add(createMiniStatCard("Pending Check-outs", String.valueOf(pendingCheckouts), UITheme.WARNING_COLOR));
        statsPanel.add(createMiniStatCard("Occupancy", occupancyRate + "%", new Color(155, 89, 182)));
        JTextArea summaryArea = new JTextArea(10, 50);
        summaryArea.setBackground(UITheme.CARD_BG);
        summaryArea.setForeground(UITheme.TEXT_COLOR);
        summaryArea.setEditable(false);
        summaryArea.setFont(UITheme.NORMAL_FONT);
        StringBuilder summary = new StringBuilder();
        summary.append("DAILY SUMMARY\n");
        summary.append("=============\n\n");
        summary.append("Total Customers: ").append(customers.size()).append("\n");
        summary.append("Total Reservations: ").append(reservations.size()).append("\n");
        summary.append("Total Rooms: ").append(rooms.size()).append("\n");
        summary.append("Available Rooms: ").append(availableRooms).append("\n");
        summary.append("Occupied Rooms: ").append(rooms.size() - availableRooms).append("\n");
        summary.append("Occupancy Rate: ").append(occupancyRate).append("%\n");
        summaryArea.setText(summary.toString());
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        panel.add(title, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createReceptionSettingsPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CARD_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("Reception Settings");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(UITheme.TEXT_COLOR);
        JPanel settingsForm = new JPanel(new GridBagLayout());
        settingsForm.setBackground(UITheme.CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        settingsForm.add(createFormLabel("Receptionist Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField("Receptionist");
        nameField.setBackground(UITheme.CARD_BG);
        nameField.setForeground(UITheme.TEXT_COLOR);
        settingsForm.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        settingsForm.add(createFormLabel("Shift Timing:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> shiftCombo = new JComboBox<>(new String[]{"Morning (8AM-4PM)", "Evening (4PM-12AM)", "Night (12AM-8AM)"});
        shiftCombo.setBackground(UITheme.CARD_BG);
        shiftCombo.setForeground(UITheme.TEXT_COLOR);
        settingsForm.add(shiftCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        settingsForm.add(createFormLabel("Notification Sound:"), gbc);
        gbc.gridx = 1;
        JCheckBox soundCheck = new JCheckBox("Enable");
        soundCheck.setBackground(UITheme.CARD_BG);
        soundCheck.setForeground(UITheme.TEXT_COLOR);
        settingsForm.add(soundCheck, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton saveBtn = createStyledButton("Save Preferences", UITheme.SUCCESS_COLOR);
        saveBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Settings saved successfully!");
        });
        settingsForm.add(saveBtn, gbc);
        panel.add(title, BorderLayout.NORTH);
        panel.add(settingsForm, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createMiniStatCard(String title, String value, Color color) 
    {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(UITheme.SMALL_FONT);
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }
    
    private JLabel createFormLabel(String text) 
    {
        JLabel label = new JLabel(text);
        label.setForeground(UITheme.TEXT_COLOR);
        label.setFont(UITheme.NORMAL_FONT);
        return label;
    }
}