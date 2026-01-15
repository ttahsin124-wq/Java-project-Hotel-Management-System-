import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

public class AdminDashboard extends JFrame 
{
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private JLabel statusLabel;
    public AdminDashboard() 
    {
        UITheme.apply();
        setTitle("Admin Dashboard - Hotel Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UITheme.DARK_BG);
        JPanel headerPanel = createHeaderPanel();
        JPanel sidebarPanel = createSidebarPanel();
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(UITheme.CARD_BG);
        initializePanels();
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(UITheme.SECONDARY_COLOR);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusLabel = new JLabel("Ready");
        statusLabel.setForeground(UITheme.TEXT_COLOR);
        statusPanel.add(statusLabel, BorderLayout.WEST);
        JLabel userLabel = new JLabel("Admin | " + new Date());
        userLabel.setForeground(UITheme.TEXT_COLOR);
        statusPanel.add(userLabel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }
    private JPanel createHeaderPanel() 
    {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.DARK_BG);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel title = new JLabel("Hotel Management System - Admin Panel");
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(Color.WHITE);
        
        JButton logoutBtn = createStyledButton("Logout", UITheme.DANGER_COLOR);
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) 
            {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        header.add(title, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        return header;
    }
    private JPanel createSidebarPanel() 
    {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.SECONDARY_COLOR);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JButton btnDashboard = createMenuButton("Dashboard", "dashboard");
        btnDashboard.setBackground(UITheme.PRIMARY_COLOR);
        JButton btnEmployees = createMenuButton("Employee Management", "employees");
        JButton btnRooms = createMenuButton("Room Management", "rooms");
        JButton btnDrivers = createMenuButton("Driver Management", "drivers");
        JButton btnCustomers = createMenuButton("Customer Management", "customers");
        JButton btnReservations = createMenuButton("Reservation Management", "reservations");
        sidebar.add(btnReservations);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton btnReports = createMenuButton("Reports & Analytics", "reports");
        JButton btnSettings = createMenuButton("System Settings", "settings");
        sidebar.add(btnDashboard);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnEmployees);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnRooms);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnDrivers);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnCustomers);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnReports);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnSettings);
        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }
    private JButton createMenuButton(String text, String cardName) 
    {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(230, 45));
        button.setBackground(UITheme.CARD_BG);
        button.setForeground(UITheme.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setFont(UITheme.NORMAL_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                cardLayout.show(mainContentPanel, cardName);
                statusLabel.setText("📍" + text);
                highlightActiveButton(button);
            }
        });
        
        button.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                if (!button.getBackground().equals(UITheme.PRIMARY_COLOR)) 
                {
                    button.setBackground(UITheme.PRIMARY_COLOR.darker());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                if (!button.getBackground().equals(UITheme.PRIMARY_COLOR)) 
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
                comp.setBackground(UITheme.CARD_BG);
            }
        }
        
        activeButton.setBackground(UITheme.PRIMARY_COLOR);
    }
    
    private void initializePanels() 
    {
      
        JPanel dashboardPanel = createDashboardPanel();
        mainContentPanel.add(dashboardPanel, "dashboard");
        EmployeePanel employeePanel = new EmployeePanel();
        mainContentPanel.add(employeePanel, "employees");
        RoomPanel roomPanel = new RoomPanel();
        mainContentPanel.add(roomPanel, "rooms");
        DriverPanel driverPanel = new DriverPanel();
        mainContentPanel.add(driverPanel, "drivers");
        CustomerFormPanel customerPanel = new CustomerFormPanel();
        mainContentPanel.add(customerPanel, "customers");
        ReservationPanel reservationPanel = new ReservationPanel();
        mainContentPanel.add(reservationPanel, "reservations");
        JPanel reportsPanel = createReportsPanel();
        mainContentPanel.add(reportsPanel, "reports");
        JPanel settingsPanel = createSettingsPanel();
        mainContentPanel.add(settingsPanel, "settings");

    }
    
    private JPanel createDashboardPanel()
     {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CARD_BG);
        
        JLabel title = new JLabel("Dashboard Overview", SwingConstants.CENTER);
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(UITheme.TEXT_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
    
        ArrayList<Employee> employees = FileUtil.load("employees.dat");
        ArrayList<HotelRoom> rooms = FileUtil.load("rooms.dat");
        ArrayList<Customer> customers = FileUtil.load("customers.dat");
        
        int totalEmployees = employees.size();
        
        int availableRooms = 0;
        for (HotelRoom room : rooms) 
        {
            if (room.available) 
            {
                availableRooms++;
            }
        }
        int totalCustomers = customers.size();
        
        double revenueToday = customers.size() * 100.0;
        int issues = 0;
        for (HotelRoom room : rooms) 
        {
            if (!room.cleaned) 
            {
                issues++;
            }
        }
        int occupiedRooms = rooms.size() - availableRooms;
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        statsPanel.setBackground(UITheme.CARD_BG);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        statsPanel.add(createStatCard("Total Employees", String.valueOf(totalEmployees), UITheme.PRIMARY_COLOR));
        statsPanel.add(createStatCard("Available Rooms", String.valueOf(availableRooms), UITheme.SUCCESS_COLOR));
        statsPanel.add(createStatCard("Total Customers", String.valueOf(totalCustomers), UITheme.WARNING_COLOR));
        statsPanel.add(createStatCard("Occupied Rooms", String.valueOf(occupiedRooms), UITheme.SECONDARY_COLOR));
        statsPanel.add(createStatCard("Revenue Today", String.format("$%.2f", revenueToday), new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Cleaning Issues", String.valueOf(issues), UITheme.DANGER_COLOR));
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBackground(new Color(50, 50, 50));
        activityPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UITheme.PRIMARY_COLOR),
            "Recent Activities",
            0, 0,
            UITheme.NORMAL_FONT,
            UITheme.TEXT_COLOR
        ));
        DefaultListModel<String> activityListModel = new DefaultListModel<>();
        
        int customerCount = Math.min(customers.size(), 3);
        for (int i = Math.max(0, customers.size() - customerCount); i < customers.size(); i++) 
        {
            Customer customer = customers.get(i);
            activityListModel.addElement("Customer " + customer.getName() + " checked in");
        }
        int employeeCount = Math.min(employees.size(), 2);
        for (int i = Math.max(0, employees.size() - employeeCount); i < employees.size(); i++) 
        {
            Employee employee = employees.get(i);
            activityListModel.addElement("Employee " + employee.getName() + " added");
        }
        for (HotelRoom room : rooms) 
        {
            if (!room.cleaned && activityListModel.size() < 6) 
            {
                activityListModel.addElement("Room " + room.roomNo + " needs cleaning");
            }
        }
        
        
        if (activityListModel.isEmpty()) 
        {
            activityListModel.addElement("No recent activities");
        }
        
        JList<String> activityList = new JList<>(activityListModel);
        activityList.setBackground(new Color(40, 40, 40));
        activityList.setForeground(UITheme.TEXT_COLOR);
        activityList.setFont(UITheme.SMALL_FONT);
        JScrollPane scrollPane = new JScrollPane(activityList);
        
        
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.setBackground(new Color(50, 50, 50));
        JButton refreshBtn = new JButton("Refresh Dashboard");
        refreshBtn.setBackground(UITheme.PRIMARY_COLOR);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.addActionListener(e -> {
            
            cardLayout.show(mainContentPanel, "dashboard");
            
            mainContentPanel.removeAll();
            initializePanels();
            cardLayout.show(mainContentPanel, "dashboard");
            statusLabel.setText(" Dashboard refreshed");
        });
        refreshPanel.add(refreshBtn);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(refreshPanel, BorderLayout.SOUTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) 
    {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(UITheme.SMALL_FONT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createReportsPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CARD_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Reports & Analytics");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(UITheme.TEXT_COLOR);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(UITheme.CARD_BG);
        tabbedPane.setForeground(UITheme.TEXT_COLOR);
        JPanel financialPanel = createFinancialReport();
        tabbedPane.addTab("Financial", financialPanel);
        JPanel occupancyPanel = createOccupancyReport();
        tabbedPane.addTab("Occupancy", occupancyPanel);
        JPanel employeePanel = createEmployeeReport();
        tabbedPane.addTab("Employee", employeePanel);
        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exportPanel.setBackground(UITheme.CARD_BG);
        JButton btnExportPDF = createStyledButton("Export PDF", UITheme.PRIMARY_COLOR);
        JButton btnExportExcel = createStyledButton("Export Excel", UITheme.SUCCESS_COLOR);
        exportPanel.add(btnExportPDF);
        exportPanel.add(btnExportExcel);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(exportPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createFinancialReport() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CARD_BG);
        
        
        ArrayList<Customer> customers = FileUtil.load("customers.dat");
        ArrayList<HotelRoom> rooms = FileUtil.load("rooms.dat");
        
        
        double totalRevenue = customers.size() * 100.0; 
        double avgRevenuePerCustomer = customers.size() > 0 ? totalRevenue / customers.size() : 0;
        int totalBookings = customers.size();
        String reportText = String.format(
            "FINANCIAL REPORT\n" +
            "================\n\n" +
            "Total Revenue: $%.2f\n" +
            "Average Revenue per Customer: $%.2f\n" +
            "Total Bookings: %d\n" +
            "Available Rooms: %d\n" +
            "Occupancy Rate: %.1f%%\n\n" +
            "Generated on: %s",
            totalRevenue,
            avgRevenuePerCustomer,
            totalBookings,
            getAvailableRoomCount(),
            calculateOccupancyRate(),
            new Date()
        );
        
        JTextArea reportArea = new JTextArea(reportText);
        reportArea.setBackground(UITheme.CARD_BG);
        reportArea.setForeground(UITheme.TEXT_COLOR);
        reportArea.setFont(UITheme.NORMAL_FONT);
        reportArea.setEditable(false);
        
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createOccupancyReport() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CARD_BG);
        
        ArrayList<HotelRoom> rooms = FileUtil.load("rooms.dat");
        
        int totalRooms = rooms.size();
        int availableRooms = 0;
        int occupiedRooms = 0;
        int cleanRooms = 0;
        int dirtyRooms = 0;
        
        for (HotelRoom room : rooms) 
        {
            if (room.available) 
            {
                availableRooms++;
            } 
            else 
            {
                occupiedRooms++;
            }
            
            if (room.cleaned) 
            {
                cleanRooms++;
            } 
            else 
            {
                dirtyRooms++;
            }
        }
        
        double occupancyRate = totalRooms > 0 ? (occupiedRooms * 100.0) / totalRooms : 0;
        
        String reportText = String.format(
            "OCCUPANCY REPORT\n" +
            "================\n\n" +
            "Total Rooms: %d\n" +
            "Available Rooms: %d\n" +
            "Occupied Rooms: %d\n" +
            "Occupancy Rate: %.1f%%\n" +
            "Clean Rooms: %d\n" +
            "Rooms Needing Cleaning: %d\n\n" +
            "Room Status:\n",
            totalRooms, availableRooms, occupiedRooms, occupancyRate, cleanRooms, dirtyRooms
        );
        
        
        for (HotelRoom room : rooms) 
        {
            reportText += String.format("Room %d: %s, %s\n",
                room.roomNo,
                room.available ? "Available" : "Occupied",
                room.cleaned ? "Clean" : "Needs Cleaning"
            );
        }
        
        JTextArea reportArea = new JTextArea(reportText);
        reportArea.setBackground(UITheme.CARD_BG);
        reportArea.setForeground(UITheme.TEXT_COLOR);
        reportArea.setFont(UITheme.SMALL_FONT);
        reportArea.setEditable(false);
        
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createEmployeeReport() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CARD_BG);
        
        ArrayList<Employee> employees = FileUtil.load("employees.dat");
        
        String reportText = "EMPLOYEE REPORT\n" +
                           "================\n\n" +
                           "Total Employees: " + employees.size() + "\n\n";
        
        if (employees.isEmpty()) 
        {
            reportText += "No employees registered.\n";
        } 
        else 
        {
            reportText += "Employee Details:\n";
            for (Employee emp : employees) 
            {
                reportText += String.format(
                    "ID: %d, Name: %s, Dept: %s, Salary: $%.2f\n",
                    emp.getId(), emp.getName(), emp.getDepartment(), emp.getSalary()
                );
            }
        }
        
        JTextArea reportArea = new JTextArea(reportText);
        reportArea.setBackground(UITheme.CARD_BG);
        reportArea.setForeground(UITheme.TEXT_COLOR);
        reportArea.setFont(UITheme.SMALL_FONT);
        reportArea.setEditable(false);
        
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }
    
    private int getAvailableRoomCount() 
    {
        ArrayList<HotelRoom> rooms = FileUtil.load("rooms.dat");
        int count = 0;
        for (HotelRoom room : rooms) 
        {
            if (room.available) 
            {
                count++;
            }
        }
        return count;
    }
    
    private double calculateOccupancyRate() 
    {
        ArrayList<HotelRoom> rooms = FileUtil.load("rooms.dat");
        if (rooms.isEmpty()) return 0;
        
        int occupied = 0;
        for (HotelRoom room : rooms) 
        {
            if (!room.available) 
            {
                occupied++;
            }
        }
        return (occupied * 100.0) / rooms.size();
    }
    
    private JPanel createSettingsPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CARD_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("System Settings");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(UITheme.TEXT_COLOR);
        
        JPanel settingsForm = new JPanel(new GridBagLayout());
        settingsForm.setBackground(UITheme.CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        
        gbc.gridx = 0; gbc.gridy = 0;
        settingsForm.add(createSettingsLabel("Hotel Name:"), gbc);
        gbc.gridx = 1;
        JTextField hotelNameField = new JTextField("Grand Hotel");
        hotelNameField.setBackground(UITheme.CARD_BG);
        hotelNameField.setForeground(UITheme.TEXT_COLOR);
        settingsForm.add(hotelNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        settingsForm.add(createSettingsLabel("Admin Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField("admin@hotel.com");
        emailField.setBackground(UITheme.CARD_BG);
        emailField.setForeground(UITheme.TEXT_COLOR);
        settingsForm.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        settingsForm.add(createSettingsLabel("Backup Frequency:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> backupCombo = new JComboBox<>(new String[]{"Daily", "Weekly", "Monthly"});
        backupCombo.setBackground(UITheme.CARD_BG);
        backupCombo.setForeground(UITheme.TEXT_COLOR);
        settingsForm.add(backupCombo, gbc);
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(UITheme.CARD_BG);
        JButton btnSave = createStyledButton("Save Settings", UITheme.SUCCESS_COLOR);
        btnSave.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Settings saved successfully!");
        });
        buttonPanel.add(btnSave);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(settingsForm, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JLabel createSettingsLabel(String text) 
    {
        JLabel label = new JLabel(text);
        label.setForeground(UITheme.TEXT_COLOR);
        label.setFont(UITheme.NORMAL_FONT);
        return label;
    }
}