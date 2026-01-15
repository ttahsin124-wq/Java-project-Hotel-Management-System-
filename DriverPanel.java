import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class DriverPanel extends JPanel 
{

    private DefaultTableModel model;
    private ArrayList<Driver> drivers;
    private JTextField nameField, ageField, companyField, carField, locationField;
    private JComboBox<Gender> genderCombo;
    private JButton addButton, editButton, deleteButton, clearButton;
    private int selectedRow = -1;

    public DriverPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);
        drivers = FileUtil.load("drivers.dat");
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.DARK_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel title = new JLabel("Driver Management");
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
            BorderFactory.createLineBorder(UITheme.PRIMARY_COLOR, 2),
            "Driver Form",
            0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR
        ));
        panel.setBorder(BorderFactory.createCompoundBorder(
            panel.getBorder(),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        nameField = UITheme.createStyledTextField();
        ageField = UITheme.createStyledTextField();
        companyField = UITheme.createStyledTextField();
        carField = UITheme.createStyledTextField();
        locationField = UITheme.createStyledTextField();
        genderCombo = new JComboBox<>(Gender.values());
        styleComboBox(genderCombo);
        panel.add(createFormRow("Name:", nameField));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Age:", ageField));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Gender:", genderCombo));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Car Company:", companyField));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Car Name:", carField));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Location:", locationField));
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(UITheme.CARD_BG);
        addButton = UITheme.createStyledButton("Add Driver", UITheme.SUCCESS_COLOR);
        editButton = UITheme.createStyledButton("Edit Driver", UITheme.WARNING_COLOR);
        deleteButton = UITheme.createStyledButton("Delete Driver", UITheme.DANGER_COLOR);
        clearButton = UITheme.createStyledButton("Clear Form", new Color(100, 100, 100));
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addButton.addActionListener(e -> addDriver());
        editButton.addActionListener(e -> editDriver());
        deleteButton.addActionListener(e -> deleteDriver());
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

    private void styleComboBox(JComboBox<Gender> comboBox) 
    {
        comboBox.setBackground(UITheme.CARD_BG);
        comboBox.setForeground(UITheme.TEXT_COLOR);
        comboBox.setFont(UITheme.NORMAL_FONT);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) 
            {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? UITheme.PRIMARY_COLOR : UITheme.CARD_BG);
                label.setForeground(UITheme.TEXT_COLOR);
                return label;
            }
        });
    }

    private JScrollPane createTablePanel() 
    {
        String[] columns = {"Name", "Age", "Gender", "Car Company", "Car Name", "Location"};
        model = new DefaultTableModel(columns, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };
        JTable table = new JTable(model);
        loadDriversToTable();
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) 
                {
                selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) 
                {
                    loadDriverToForm(selectedRow);
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
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UITheme.SECONDARY_COLOR, 2),"Driver List (" + drivers.size() + ")",0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR
        ));
        return scrollPane;
    }

    private void addDriver() 
    {
        try 
        {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            Gender gender = (Gender) genderCombo.getSelectedItem();
            String company = companyField.getText().trim();
            String car = carField.getText().trim();
            String location = locationField.getText().trim();
            if (name.isEmpty() || company.isEmpty() || car.isEmpty() || location.isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }
            for (Driver d : drivers) 
            {
                if (d.getName().equalsIgnoreCase(name) && d.getCarName().equalsIgnoreCase(car)) 
                {
                    JOptionPane.showMessageDialog(this,"Driver with name '" + name + "' and car '" + car + "' already exists!","Duplicate Driver",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            Driver driver = new Driver(name, age, gender, company, car, location);
            drivers.add(driver);
            FileUtil.save("drivers.dat", drivers);
            model.addRow(new Object[]
            {
                driver.getName(),
                driver.getAge(),
                driver.getGender(),
                driver.getCarCompany(),
                driver.getCarName(),
                driver.getLocation()
            });
            clearForm();
            JOptionPane.showMessageDialog(this, "Driver added successfully!");
            
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Please enter valid age!");
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void editDriver() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select a driver to edit!");
            return;
        }
        try 
        {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            Gender gender = (Gender) genderCombo.getSelectedItem();
            String company = companyField.getText().trim();
            String car = carField.getText().trim();
            String location = locationField.getText().trim();
            if (name.isEmpty() || company.isEmpty() || car.isEmpty() || location.isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }
            Driver driver = drivers.get(selectedRow);
            driver.setName(name);
            driver.setAge(age);
            driver.setGender(gender);
            driver.setCarCompany(company);
            driver.setCarName(car);
            driver.setLocation(location);
            FileUtil.save("drivers.dat", drivers);
            model.setValueAt(driver.getName(), selectedRow, 0);
            model.setValueAt(driver.getAge(), selectedRow, 1);
            model.setValueAt(driver.getGender(), selectedRow, 2);
            model.setValueAt(driver.getCarCompany(), selectedRow, 3);
            model.setValueAt(driver.getCarName(), selectedRow, 4);
            model.setValueAt(driver.getLocation(), selectedRow, 5);
            clearForm();
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Driver updated successfully!");
            
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Please enter valid age!");
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    private void deleteDriver() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select a driver to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this driver?\nThis action cannot be undone.","Confirm Deletion",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) 
            {
            Driver deletedDriver = drivers.remove(selectedRow);
            FileUtil.save("drivers.dat", drivers);
            model.removeRow(selectedRow);
            selectedRow = -1;
            clearForm();
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Driver '" + deletedDriver.getName() + "' deleted successfully!");
        }
    }
    private void loadDriverToForm(int row) 
    {
        if (row >= 0 && row < drivers.size()) 
        {
            Driver driver = drivers.get(row);
            nameField.setText(driver.getName());
            ageField.setText(String.valueOf(driver.getAge()));
            companyField.setText(driver.getCarCompany());
            carField.setText(driver.getCarName());
            locationField.setText(driver.getLocation());
            genderCombo.setSelectedItem(driver.getGender());
        }
    }
    private void clearForm() 
    {
        nameField.setText("");
        ageField.setText("");
        companyField.setText("");
        carField.setText("");
        locationField.setText("");
        genderCombo.setSelectedIndex(0);
        nameField.requestFocus();
        selectedRow = -1;
    }
    private void loadDriversToTable() 
    {
        model.setRowCount(0);
        drivers = FileUtil.load("drivers.dat");
        for (Driver driver : drivers) 
        {
            model.addRow(new Object[]
            {
                driver.getName(),
                driver.getAge(),
                driver.getGender(),
                driver.getCarCompany(),
                driver.getCarName(),
                driver.getLocation()
            });
        }
    }
}