import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;

public class CustomerFormPanel extends JPanel 
{

    private ArrayList<Customer> customers;
    private DefaultTableModel model;
    private JTextField txtId, txtName, txtPhone, txtEmail, txtRoomNumber;
    private JSpinner checkInSpinner, checkOutSpinner;
    private JButton addButton, editButton, deleteButton, clearButton;
    private JTable table;
    private int selectedRow = -1;

    public CustomerFormPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);
        customers = FileUtil.load("customers.dat");
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.DARK_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel title = new JLabel("Customer Management");
        title.setFont(UITheme.HEADER_FONT);
        title.setForeground(Color.WHITE);
        headerPanel.add(title, BorderLayout.WEST);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
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
            BorderFactory.createLineBorder(UITheme.SUCCESS_COLOR, 2),
            "Customer Form",
            0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR
        ));
        panel.setBorder(BorderFactory.createCompoundBorder(
            panel.getBorder(),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        txtId = UITheme.createStyledTextField();
        txtName = UITheme.createStyledTextField();
        txtPhone = UITheme.createStyledTextField();
        txtEmail = UITheme.createStyledTextField();
        txtRoomNumber = UITheme.createStyledTextField();
        checkInSpinner = new JSpinner(new SpinnerDateModel());
        checkOutSpinner = new JSpinner(new SpinnerDateModel());
        styleSpinner(checkInSpinner);
        styleSpinner(checkOutSpinner);
        panel.add(createFormRow("Customer ID:", txtId));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Full Name:", txtName));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Phone:", txtPhone));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Email:", txtEmail));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Room Number (Optional):", txtRoomNumber));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Check-in Date:", checkInSpinner));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Check-out Date:", checkOutSpinner));
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(UITheme.CARD_BG);
        addButton = UITheme.createStyledButton("Add Customer", UITheme.SUCCESS_COLOR);
        editButton = UITheme.createStyledButton("Edit Customer", UITheme.WARNING_COLOR);
        deleteButton = UITheme.createStyledButton("Delete Customer", UITheme.DANGER_COLOR);
        clearButton = UITheme.createStyledButton("Clear Form", new Color(100, 100, 100));
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addButton.addActionListener(e -> addCustomer());
        editButton.addActionListener(e -> editCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
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
        lbl.setPreferredSize(new Dimension(150, 30));
        field.setPreferredSize(new Dimension(200, 35));
        rowPanel.add(lbl, BorderLayout.WEST);
        rowPanel.add(field, BorderLayout.CENTER);
        return rowPanel;
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
        String[] columns = {"ID", "Name", "Phone", "Email", "Room", "Check-in", "Check-out"};
        model = new DefaultTableModel(columns, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };

        table = new JTable(model);
        loadCustomersToTable();
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) 
            {
                selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) 
                {
                    loadCustomerToForm(selectedRow);
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
        table.setSelectionBackground(UITheme.SUCCESS_COLOR);
        table.setSelectionForeground(Color.WHITE);
        table.getColumnModel().getColumn(0).setPreferredWidth(60);  
        table.getColumnModel().getColumn(1).setPreferredWidth(150); 
        table.getColumnModel().getColumn(2).setPreferredWidth(100); 
        table.getColumnModel().getColumn(3).setPreferredWidth(150); 
        table.getColumnModel().getColumn(4).setPreferredWidth(70);  
        table.getColumnModel().getColumn(5).setPreferredWidth(120); 
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        JTableHeader header = table.getTableHeader();
        header.setBackground(UITheme.SECONDARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(UITheme.NORMAL_FONT);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UITheme.SECONDARY_COLOR, 2),
            "Customer List (" + customers.size() + ")",
            0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR
        ));
        return scrollPane;
    }

    private void addCustomer() 
    {
        try 
        {
           
            if (txtName.getText().trim().isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Please enter customer name!","Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (txtPhone.getText().trim().isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Please enter phone number!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();
            Date checkIn = (Date) checkInSpinner.getValue();
            Date checkOut = (Date) checkOutSpinner.getValue();
            Integer roomNumber = null;
            if (!txtRoomNumber.getText().trim().isEmpty()) 
                {
                try 
                {
                    roomNumber = Integer.parseInt(txtRoomNumber.getText().trim());
                } 
                catch (NumberFormatException e) 
                {
                    JOptionPane.showMessageDialog(this, "Invalid room number format! Please enter a valid number or leave empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (checkOut.before(checkIn)) 
            {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            for (Customer c : customers) 
            {
                if (c.getId() == id) 
                {
                    JOptionPane.showMessageDialog(this,"Customer ID " + id + " already exists!\nPlease use a different ID.","Duplicate ID",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            Customer customer = new Customer(id, name, phone, email, checkIn, checkOut, roomNumber);
            customers.add(customer);
            FileUtil.save("customers.dat", customers);
            model.addRow(new Object[]{
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getRoomNumber() != null ? customer.getRoomNumber() : "Not assigned",
                customer.getCheckIn(),
                customer.getCheckOut()
            });
            clearForm();
            JOptionPane.showMessageDialog(this, "Customer added successfully!\nID: " + id + "\nName: " + name, "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Invalid ID format! Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editCustomer() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit!");
            return;
        }
        try 
        {
            
            if (txtName.getText().trim().isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Please enter customer name!");
                return;
            }
            
            if (txtPhone.getText().trim().isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Please enter phone number!");
                return;
            }
            
            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();
            Date checkIn = (Date) checkInSpinner.getValue();
            Date checkOut = (Date) checkOutSpinner.getValue();
            Integer roomNumber = null;
            if (!txtRoomNumber.getText().trim().isEmpty()) 
            {
                try 
                {
                    roomNumber = Integer.parseInt(txtRoomNumber.getText().trim());
                } 
                catch (NumberFormatException e) 
                {
                    JOptionPane.showMessageDialog(this, "Invalid room number format! Please enter a valid number or leave empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (checkOut.before(checkIn)) 
            {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date!");
                return;
            }
            Customer customer = customers.get(selectedRow);
            customer.setId(id);
            customer.setName(name);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setCheckIn(checkIn);
            customer.setCheckOut(checkOut);
            customer.setRoomNumber(roomNumber);
            FileUtil.save("customers.dat", customers);
            model.setValueAt(customer.getId(), selectedRow, 0);
            model.setValueAt(customer.getName(), selectedRow, 1);
            model.setValueAt(customer.getPhone(), selectedRow, 2);
            model.setValueAt(customer.getEmail(), selectedRow, 3);
            model.setValueAt(customer.getRoomNumber() != null ? customer.getRoomNumber() : "Not assigned", selectedRow, 4);
            model.setValueAt(customer.getCheckIn(), selectedRow, 5);
            model.setValueAt(customer.getCheckOut(), selectedRow, 6);
            clearForm();
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Customer updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Invalid ID format!");
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteCustomer() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this customer?\nThis action cannot be undone.","Confirm Deletion",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) 
        {
            Customer deletedCustomer = customers.remove(selectedRow);
            FileUtil.save("customers.dat", customers);
            model.removeRow(selectedRow);
            selectedRow = -1;
            clearForm();
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Customer '" + deletedCustomer.getName() + "' deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadCustomerToForm(int row) 
    {
        if (row >= 0 && row < customers.size()) 
        {
            Customer customer = customers.get(row);
            txtId.setText(String.valueOf(customer.getId()));
            txtName.setText(customer.getName());
            txtPhone.setText(customer.getPhone());
            txtEmail.setText(customer.getEmail());
            if (customer.getRoomNumber() != null) 
            {
                txtRoomNumber.setText(String.valueOf(customer.getRoomNumber()));
            } 
            else 
            {
                txtRoomNumber.setText("");
            }
            
            checkInSpinner.setValue(customer.getCheckIn());
            checkOutSpinner.setValue(customer.getCheckOut());
        }
    }

    private void clearForm() 
    {
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtRoomNumber.setText("");
        checkInSpinner.setValue(new Date());
        checkOutSpinner.setValue(new Date(System.currentTimeMillis() + 86400000)); 
        txtId.requestFocus();
        selectedRow = -1;
    }

    private void loadCustomersToTable() 
    {
        model.setRowCount(0);
        customers = FileUtil.load("customers.dat");
        
        for (Customer customer : customers) 
        {
            model.addRow(new Object[]{
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getRoomNumber() != null ? customer.getRoomNumber() : "Not assigned",
                customer.getCheckIn(),
                customer.getCheckOut()
            });
        }
    }
}