import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class ReceptionFacade {
    private ArrayList<HotelRoom> rooms;
    private ArrayList<Reservation> reservations;
    private ArrayList<Customer> customers;
    private ArrayList<Driver> drivers;
    
    public ReceptionFacade() 
    {
        loadAllData();
    }
    
    private void loadAllData() 
    {
        rooms = FileUtil.load("rooms.dat");
        reservations = FileUtil.load("reservations.dat");
        customers = FileUtil.load("customers.dat");
        drivers = FileUtil.load("drivers.dat");
    }
    
    private void saveAllData() 
    {
        FileUtil.save("rooms.dat", rooms);
        FileUtil.save("reservations.dat", reservations);
        FileUtil.save("customers.dat", customers);
        FileUtil.save("drivers.dat", drivers);
    }
    
    public boolean checkInGuest(int reservationId) {
        loadAllData();
        for (Reservation res : reservations) {
            if (res.getReservationId() == reservationId && res.getStatus().equals("CONFIRMED")) {
                res.setStatus("CHECKED_IN");
                res.getRoom().available = false;
                res.getCustomer().setRoomNumber(res.getRoom().roomNo);
                res.getCustomer().setCheckIn(new Date());
                saveAllData();
                return true;
            }
        }
        return false;
    }
    
    public CheckOutResult checkOutGuest(int customerId) 
    {
        loadAllData();
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            if (customer.getId() == customerId) {
                for (Reservation res : reservations) {
                    if (res.getCustomer().getId() == customerId && !res.getStatus().equals("CHECKED_OUT")) {
                        res.setStatus("CHECKED_OUT");
                        res.getRoom().available = true;
                        double bill = calculateBill(res);
                        Customer removed = customers.remove(i);
                        saveAllData();
                        return new CheckOutResult(true, removed, bill);
                    }
                }
            }
        }
        return new CheckOutResult(false, null, 0);
    }
    
    public ReservationResult makeReservation(int customerId, int roomNo, Date checkIn, Date checkOut, String requests) 
    {
        loadAllData();
        Customer customer = customers.stream().filter(c -> c.getId() == customerId).findFirst().orElse(null);
        HotelRoom room = rooms.stream().filter(r -> r.roomNo == roomNo && r.available).findFirst().orElse(null);
        
        if (customer == null || room == null || checkOut.before(checkIn)) 
            return new ReservationResult(false, null, "Invalid reservation details");
        
        int newId = reservations.stream().mapToInt(Reservation::getReservationId).max().orElse(0) + 1;
        long nights = (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
        double amount = (nights < 1 ? 1 : nights) * room.price;
        
        Reservation reservation = new Reservation(newId, customer, room, checkIn, checkOut, amount, "CONFIRMED", requests);
        room.available = false;
        customer.setRoomNumber(room.roomNo);
        reservations.add(reservation);
        saveAllData();
        return new ReservationResult(true, reservation, "Success");
    }
    
    public ArrayList<Customer> searchCustomers(String term, String type) {
        loadAllData();
        ArrayList<Customer> results = new ArrayList<>();
        for (Customer c : customers) {
            boolean match = switch (type) {
                case "By Name" -> c.getName().toLowerCase().contains(term.toLowerCase());
                case "By Phone" -> c.getPhone().contains(term);
                case "By Email" -> c.getEmail().toLowerCase().contains(term.toLowerCase());
                case "By Room" -> c.getRoomNumber() != null && c.getRoomNumber().toString().equals(term);
                default -> false;
            };
            if (match) results.add(c);
        }
        return results;
    }
    
    public DashboardStats getDashboardStats() {
        loadAllData();
        int available = 0, occupied = 0, checkedIn = 0;
        for (HotelRoom r : rooms) { if (r.available) available++; else occupied++; }
        for (Reservation r : reservations) { if (r.getStatus().equals("CHECKED_IN")) checkedIn++; }
        return new DashboardStats(customers.size(), reservations.size(), available, occupied, checkedIn);
    }
    
    public PickupResult requestPickup(int customerId, String location) {
        loadAllData();
        if (customers.stream().noneMatch(c -> c.getId() == customerId)) 
            return new PickupResult(false, null, "Customer not found");
        
        Driver driver = drivers.stream().filter(d -> d.getLocation().equalsIgnoreCase(location)).findFirst().orElse(null);
        return driver == null ? new PickupResult(false, null, "No driver available"): new PickupResult(true, driver, "Driver " + driver.getName() + " assigned");
    }
    
    public boolean updateRoomStatus(int roomNo, boolean available, boolean cleaned) {
        loadAllData();
        for (HotelRoom r : rooms) {
            if (r.roomNo == roomNo) {
                r.available = available;
                r.cleaned = cleaned;
                saveAllData();
                return true;
            }
        }
        return false;
    }
    
    private double calculateBill(Reservation res) {
        long nights = (res.getCheckOut().getTime() - res.getCheckIn().getTime()) / (1000 * 60 * 60 * 24);
        nights = nights < 1 ? 1 : nights;
        double roomCharges = nights * res.getRoom().price;
        return roomCharges + (roomCharges * 0.18) + 100; 
    }
}

class CheckOutResult { boolean success; Customer customer; double billAmount;
    CheckOutResult(boolean s, Customer c, double b) { success = s; customer = c; billAmount = b; } }

class ReservationResult { boolean success; Reservation reservation; String message;
    ReservationResult(boolean s, Reservation r, String m) { success = s; reservation = r; message = m; } }

class DashboardStats { int tc, tr, ar, or, cg;
    DashboardStats(int tc, int tr, int ar, int or, int cg) { this.tc = tc; this.tr = tr; this.ar = ar; this.or = or; this.cg = cg; } }

class PickupResult { boolean success; Driver driver; String message;
    PickupResult(boolean s, Driver d, String m) { success = s; driver = d; message = m; } }


public class ReceptionDashboard extends JFrame {
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private JLabel statsLabel;
    private ReceptionFacade hotelFacade;
    
    public ReceptionDashboard() {
        UITheme.apply();
        hotelFacade = new ReceptionFacade();
        
        setTitle("Reception Panel - Hotel Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UITheme.DARK_BG);
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createSidebar(), BorderLayout.WEST);
        
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(UITheme.CARD_BG);
        initializeContentPanels();
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
        updateStats();
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.DARK_BG);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel title = new JLabel("Reception Panel");
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(Color.WHITE);
        
        statsLabel = new JLabel();
        statsLabel.setFont(UITheme.SMALL_FONT);
        statsLabel.setForeground(UITheme.SUCCESS_COLOR);
        
        JButton logoutBtn = createStyledButton("Logout", UITheme.DANGER_COLOR);
        logoutBtn.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Logout?", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(title);
        leftPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        leftPanel.add(statsLabel);
        
        header.add(leftPanel, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        return header;
    }
    
    private void updateStats() {
        DashboardStats s = hotelFacade.getDashboardStats();
        statsLabel.setText(String.format(" %d checked-in | %d available | %d reservations", s.cg, s.ar, s.tr));
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.SECONDARY_COLOR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        String[][] menu = {
            {"New Customer Registration", "newCustomer"},
            {"Make Reservation", "makeReservation"},
            {"Check-in Guest", "checkIn"},
            {"Check-out Guest", "checkOut"},
            {"Search Customer", "searchCustomer"},
            {"View Reservations", "viewReservations"},
            {"View Room Status", "roomStatus"},
            {"Pick-up Service", "pickup"},
            {"Update Room Status", "updateRoom"},
            {"View All Customers", "allCustomers"},
            {"View Reports", "reports"},
            {"Settings", "settings"}
        };
        
        for (String[] item : menu) {
            JButton btn = createSidebarButton(item[0], item[1]);
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        
        JButton refreshBtn = createSidebarButton("Refresh", "refresh");
        refreshBtn.addActionListener(e -> { updateStats(); JOptionPane.showMessageDialog(this, "Refreshed!"); });
        sidebar.add(refreshBtn);
        sidebar.add(Box.createVerticalGlue());
        
        return sidebar;
    }
    
    private JButton createSidebarButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setBackground(UITheme.CARD_BG);
        btn.setForeground(UITheme.TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setFont(UITheme.SMALL_FONT);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addActionListener(e -> {
            if (!cardName.equals("refresh")) cardLayout.show(mainContentPanel, cardName);
            highlightActiveButton(btn);
        });
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(UITheme.SUCCESS_COLOR))
                    btn.setBackground(UITheme.SUCCESS_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(UITheme.SUCCESS_COLOR))
                    btn.setBackground(UITheme.CARD_BG);
            }
        });
        return btn;
    }
    
    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(UITheme.NORMAL_FONT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bg.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bg); }
        });
        return btn;
    }
    
    private void highlightActiveButton(JButton active) {
        for (Component c : ((JPanel)active.getParent()).getComponents()) {
            if (c instanceof JButton && !((JButton)c).getText().contains("Refresh"))
                c.setBackground(UITheme.CARD_BG);
        }
        active.setBackground(UITheme.SUCCESS_COLOR);
    }
    
    private void initializeContentPanels() {
        mainContentPanel.add(new CustomerFormPanel(), "newCustomer");
        mainContentPanel.add(new ReservationPanel(), "makeReservation");
        mainContentPanel.add(new CheckInPanel(hotelFacade), "checkIn");
        mainContentPanel.add(new CheckOutPanel(hotelFacade), "checkOut");
        mainContentPanel.add(new SearchCustomerPanel(hotelFacade), "searchCustomer");
        mainContentPanel.add(new ViewReservationsPanel(), "viewReservations");
        mainContentPanel.add(new RoomListPanel(), "roomStatus");
        mainContentPanel.add(new PickupServicePanel(hotelFacade), "pickup");
        mainContentPanel.add(new UpdateRoomStatusPanel(), "updateRoom");
        mainContentPanel.add(new CustomerListPanel(), "allCustomers");
        mainContentPanel.add(new ReceptionReportsPanel(hotelFacade), "reports");
        mainContentPanel.add(new ReceptionSettingsPanel(), "settings");
    }
    

    
    class CheckInPanel extends JPanel {
        private ReceptionFacade facade;
        private JTextField reservationIdField, customerNameField, roomField;
        
        CheckInPanel(ReceptionFacade facade) {
            this.facade = facade;
            setLayout(new BorderLayout());
            setBackground(UITheme.CARD_BG);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(UITheme.CARD_BG);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            reservationIdField = createTextField();
            customerNameField = createTextField();
            customerNameField.setEditable(false);
            roomField = createTextField();
            roomField.setEditable(false);
            
            addRow(formPanel, gbc, 0, "Reservation ID:", reservationIdField);
            addRow(formPanel, gbc, 1, "Customer Name:", customerNameField);
            addRow(formPanel, gbc, 2, "Room Number:", roomField);
            
            JButton findBtn = createStyledButton("Find Reservation", UITheme.PRIMARY_COLOR);
            findBtn.addActionListener(e -> findReservation());
            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
            formPanel.add(findBtn, gbc);
            
            JButton checkInBtn = createStyledButton("Check-in Guest", UITheme.SUCCESS_COLOR);
            checkInBtn.addActionListener(e -> performCheckIn());
            gbc.gridy = 4;
            formPanel.add(checkInBtn, gbc);
            
            add(new JLabel("Guest Check-in", SwingConstants.CENTER), BorderLayout.NORTH);
            add(formPanel, BorderLayout.CENTER);
        }
        
        private void findReservation() {
            String id = reservationIdField.getText().trim();
            if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter ID!"); return; }
            
            ArrayList<Reservation> res = FileUtil.load("reservations.dat");
            for (Reservation r : res) {
                if (r.getReservationId() == Integer.parseInt(id)) {
                    customerNameField.setText(r.getCustomer().getName());
                    roomField.setText(String.valueOf(r.getRoom().roomNo));
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Not found!");
        }
        
        private void performCheckIn() {
            String id = reservationIdField.getText().trim();
            if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Find reservation first!"); return; }
            
            if (facade.checkInGuest(Integer.parseInt(id))) {
                JOptionPane.showMessageDialog(this, "Checked in successfully!");
                reservationIdField.setText(""); customerNameField.setText(""); roomField.setText("");
                updateStats();
            } else {
                JOptionPane.showMessageDialog(this, "Check-in failed!");
            }
        }
    }
    
    class CheckOutPanel extends JPanel {
        private ReceptionFacade facade;
        private JTable table;
        private DefaultTableModel model;
        
        CheckOutPanel(ReceptionFacade facade) {
            this.facade = facade;
            setLayout(new BorderLayout());
            
            String[] cols = {"ID","Name","Phone", "Room"};
            model = new DefaultTableModel(cols, 0);
            table = new JTable(model);
            loadTable();
            styleTable();
            
            JButton btn = UITheme.createStyledButton("Check Out Selected", UITheme.DANGER_COLOR);
            btn.addActionListener(e -> checkOut());
            
            add(createHeader("Guest Check-Out", UITheme.DANGER_COLOR), BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);
            add(btn, BorderLayout.SOUTH);
        }
        
        private void loadTable() {
            model.setRowCount(0);
            FileUtil.load("customers.dat").stream()
                .filter(c -> ((Customer)c).getRoomNumber() != null)
                .forEach(c -> model.addRow(new Object[]{((Customer)c).getId(), ((Customer)c).getName(), 
                    ((Customer)c).getPhone(), ((Customer)c).getRoomNumber()}));
        }
        
        private void checkOut() {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a customer!"); return; }
            
            int id = (int) table.getValueAt(row, 0);
            String name = (String) table.getValueAt(row, 1);
            
            if (JOptionPane.showConfirmDialog(this, "Check out " + name + "?", "Confirm", 
                    JOptionPane.YES_NO_OPTION) == 0) {
                CheckOutResult r = facade.checkOutGuest(id);
                if (r.success) {
                    JOptionPane.showMessageDialog(this, String.format("%s checked out!\nBill: $%.2f", name, r.billAmount));
                    loadTable();
                    updateStats();
                }
            }
        }
        
        private void styleTable() {
            table.setBackground(UITheme.CARD_BG);
            table.setForeground(UITheme.TEXT_COLOR);
            table.setRowHeight(35);
            table.setSelectionBackground(UITheme.DANGER_COLOR);
        }
    }
    
    class SearchCustomerPanel extends JPanel {
        private ReceptionFacade facade;
        private JTextField searchField;
        private JComboBox<String> typeCombo;
        private JTextArea resultsArea;
        
        SearchCustomerPanel(ReceptionFacade facade) {
            this.facade = facade;
            setLayout(new BorderLayout());
            
            JPanel topPanel = new JPanel(new FlowLayout());
            topPanel.setBackground(UITheme.CARD_BG);
            
            typeCombo = new JComboBox<>(new String[]{"By Name", "By Phone", "By Email", "By Room"});
            searchField = new JTextField(15);
            JButton searchBtn = createStyledButton("Search", UITheme.PRIMARY_COLOR);
            searchBtn.addActionListener(e -> search());
            
            topPanel.add(new JLabel("Search:"));
            topPanel.add(typeCombo);
            topPanel.add(searchField);
            topPanel.add(searchBtn);
            
            resultsArea = new JTextArea(15, 50);
            resultsArea.setEditable(false);
            resultsArea.setBackground(UITheme.CARD_BG);
            resultsArea.setForeground(UITheme.TEXT_COLOR);
            
            add(createHeader("Search Customer", UITheme.DARK_BG), BorderLayout.NORTH);
            add(topPanel, BorderLayout.CENTER);
            add(new JScrollPane(resultsArea), BorderLayout.SOUTH);
        }
        
        private void search() {
            String term = searchField.getText().trim();
            if (term.isEmpty()) { resultsArea.setText("Enter search text!"); return; }
            
            ArrayList<Customer> results = facade.searchCustomers(term, (String)typeCombo.getSelectedItem());
            StringBuilder sb = new StringBuilder("Results: " + results.size() + "\n" + "=".repeat(40) + "\n");
            results.forEach(c -> sb.append(String.format("ID: %d | %s | %s | Room: %s\n", 
                c.getId(), c.getName(), c.getPhone(), 
                c.getRoomNumber() == null ? "N/A" : c.getRoomNumber())));
            resultsArea.setText(sb.toString());
        }
    }
    
    class PickupServicePanel extends JPanel {
        private ReceptionFacade facade;
        private JTextField customerIdField, locationField;
        private JTextArea resultArea;
        
        PickupServicePanel(ReceptionFacade facade) {
            this.facade = facade;
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(UITheme.CARD_BG);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            
            customerIdField = createTextField();
            locationField = createTextField();
            
            addRow(formPanel, gbc, 0, "Customer ID:", customerIdField);
            addRow(formPanel, gbc, 1, "Location:", locationField);
            
            JButton requestBtn = createStyledButton("Request Pickup", UITheme.SUCCESS_COLOR);
            requestBtn.addActionListener(e -> request());
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            formPanel.add(requestBtn, gbc);
            
            resultArea = new JTextArea(5, 40);
            resultArea.setEditable(false);
            resultArea.setBackground(UITheme.CARD_BG);
            resultArea.setForeground(UITheme.TEXT_COLOR);
            
            add(new JLabel("Pick-up Service", SwingConstants.CENTER), BorderLayout.NORTH);
            add(formPanel, BorderLayout.CENTER);
            add(new JScrollPane(resultArea), BorderLayout.SOUTH);
        }
        
        private void request() {
            try {
                int id = Integer.parseInt(customerIdField.getText().trim());
                String loc = locationField.getText().trim();
                if (loc.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter location!"); return; }
                
                PickupResult r = facade.requestPickup(id, loc);
                resultArea.setText(r.success ? 
                    String.format("Driver %s assigned\nCar: %s %s", 
                        r.driver.getName(), r.driver.getCarCompany(), r.driver.getCarName()) :
                    "XX " + r.message);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID!");
            }
        }
    }
    
    class ReceptionReportsPanel extends JPanel {
        ReceptionReportsPanel(ReceptionFacade facade) {
            setLayout(new BorderLayout());
            setBackground(UITheme.CARD_BG);
            
            DashboardStats s = facade.getDashboardStats();
            int rate = s.or == 0 ? 0 : (s.or * 100) / (s.ar + s.or);
            
            String report = String.format(
                "RECEPTION REPORT\n═══════════════\n\nCustomers: %d\nReservations: %d\nChecked-in: %d\nAvailable Rooms: %d\nOccupied: %d\nOccupancy: %d%%\n\nGenerated: %s",
                s.tc, s.tr, s.cg, s.ar, s.or, rate, new Date()
            );
            
            JTextArea area = new JTextArea(report);
            area.setEditable(false);
            area.setBackground(UITheme.CARD_BG);
            area.setForeground(UITheme.TEXT_COLOR);
            area.setFont(UITheme.NORMAL_FONT);
            
            add(createHeader("Reports", UITheme.DARK_BG), BorderLayout.NORTH);
            add(area, BorderLayout.CENTER);
        }
    }
    
    class ReceptionSettingsPanel extends JPanel {
        ReceptionSettingsPanel() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(UITheme.CARD_BG);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            
            JTextField nameField = createTextField();
            nameField.setText("Receptionist");
            
            JComboBox<String> shiftBox = new JComboBox<>(new String[]{"Morning", "Evening", "Night"});
            shiftBox.setBackground(UITheme.CARD_BG);
            
            addRow(form, gbc, 0, "Name:", nameField);
            addRow(form, gbc, 1, "Shift:", shiftBox);
            
            JButton saveBtn = createStyledButton("Save", UITheme.SUCCESS_COLOR);
            saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Saved!"));
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            form.add(saveBtn, gbc);
            
            add(createHeader("Settings", UITheme.DARK_BG), BorderLayout.NORTH);
            add(form, BorderLayout.CENTER);
        }
    }
    
    class ViewReservationsPanel extends JPanel {
        ViewReservationsPanel() {
            setLayout(new BorderLayout());
            JTextArea area = new JTextArea();
            area.setEditable(false);
            area.setBackground(UITheme.CARD_BG);
            area.setForeground(UITheme.TEXT_COLOR);
            
            StringBuilder sb = new StringBuilder("RESERVATIONS\n═══════════\n\n");
            ArrayList<Reservation> list = FileUtil.load("reservations.dat");
            list.forEach(r -> sb.append(String.format("ID: %d | %s | Room %d | %s | $%.2f\n", 
                r.getReservationId(), r.getCustomer().getName(), r.getRoom().roomNo, r.getStatus(), r.getTotalAmount())));
            
            area.setText(sb.toString());
            add(createHeader("All Reservations", UITheme.DARK_BG), BorderLayout.NORTH);
            add(new JScrollPane(area), BorderLayout.CENTER);
        }
    }

    
    private JPanel createHeader(String title, Color bg) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);
        p.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel l = new JLabel(title);
        l.setFont(UITheme.HEADER_FONT);
        l.setForeground(Color.WHITE);
        p.add(l);
        return p;
    }
    
    private JTextField createTextField() {
        JTextField f = new JTextField(15);
        f.setBackground(UITheme.CARD_BG);
        f.setForeground(UITheme.TEXT_COLOR);
        f.setCaretColor(UITheme.TEXT_COLOR);
        return f;
    }
    
    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, Component field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        JLabel l = new JLabel(label);
        l.setForeground(UITheme.TEXT_COLOR);
        panel.add(l, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
}