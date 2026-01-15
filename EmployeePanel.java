import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class EmployeePanel extends JPanel 
{

    private DefaultTableModel model;
    private ArrayList<Employee> employees;
    private JTextField nameField, ageField, salaryField;
    private JComboBox<Gender> genderCombo;
    private JComboBox<Department> deptCombo;
    private JButton addButton, editButton, deleteButton, clearButton;
    private int selectedRow = -1;
    public EmployeePanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UITheme.CARD_BG);
        employees = FileUtil.load("employees.dat");
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.DARK_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel title = new JLabel("Employee Management");
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
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.PRIMARY_COLOR, 2),"Employee Form",0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR));
        panel.setBorder(BorderFactory.createCompoundBorder(panel.getBorder(),BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        nameField = UITheme.createStyledTextField();
        ageField = UITheme.createStyledTextField();
        salaryField = UITheme.createStyledTextField();
        genderCombo = new JComboBox<>(Gender.values());
        deptCombo = new JComboBox<>(Department.values());
        styleComboBox(genderCombo);
        styleComboBox(deptCombo);
        panel.add(createFormRow("Name:", nameField));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Age:", ageField));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Gender:", genderCombo));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Department:", deptCombo));
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createFormRow("Salary ($):", salaryField));
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(UITheme.CARD_BG);
        addButton = UITheme.createStyledButton("Add Employee", UITheme.SUCCESS_COLOR);
        editButton = UITheme.createStyledButton("Edit Employee", UITheme.WARNING_COLOR);
        deleteButton = UITheme.createStyledButton("Delete Employee", UITheme.DANGER_COLOR);
        clearButton = UITheme.createStyledButton("Clear Form", new Color(100, 100, 100));
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addButton.addActionListener(e -> addEmployee());
        editButton.addActionListener(e -> editEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
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

    private JScrollPane createTablePanel() 
    {
        String[] columns = {"ID", "Name", "Age", "Gender", "Department", "Salary"};
        model = new DefaultTableModel(columns, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };

        JTable table = new JTable(model);
        loadEmployeesToTable();
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) 
                {
                selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) 
                {
                    loadEmployeeToForm(selectedRow);
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
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.SECONDARY_COLOR, 2),"Employee List (" + employees.size() + ")",0, 0, UITheme.HEADER_FONT, UITheme.TEXT_COLOR));
        return scrollPane;
    }

    private void styleComboBox(JComboBox<?> comboBox) 
    {
        comboBox.setBackground(UITheme.CARD_BG);
        comboBox.setForeground(UITheme.TEXT_COLOR);
        comboBox.setFont(UITheme.NORMAL_FONT);
        comboBox.setRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
            {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? UITheme.PRIMARY_COLOR : UITheme.CARD_BG);
                label.setForeground(UITheme.TEXT_COLOR);
                return label;
            }
        });
    }
    private void addEmployee() 
    {
        try 
        {
            
            if (nameField.getText().trim().isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Please enter employee name!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int age = Integer.parseInt(ageField.getText().trim());
            double salary = Double.parseDouble(salaryField.getText().trim());
            if (age < 18 || age > 65) 
            {
                JOptionPane.showMessageDialog(this, "Age must be between 18 and 65!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (salary <= 0) 
            {
                JOptionPane.showMessageDialog(this, "Salary must be positive!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int newId = employees.isEmpty() ? 1 : employees.get(employees.size() - 1).getId() + 1;
            Employee emp = new Employee(
                newId,
                nameField.getText().trim(),
                age,
                (Gender) genderCombo.getSelectedItem(),
                (Department) deptCombo.getSelectedItem(),
                salary
            );
            employees.add(emp);
            FileUtil.save("employees.dat", employees);
            model.addRow(new Object[]{
                emp.getId(),
                emp.getName(),
                emp.getAge(),
                emp.getGender().toString(),
                emp.getDepartment().toString(),
                String.format("$%.2f", emp.getSalary())
            });
            clearForm();
            JOptionPane.showMessageDialog(this, "Employee added successfully!\nID: " + newId, "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Invalid number format!\nPlease check age and salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void editEmployee() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit!");
            return;
        }
        try 
        {
           
            if (nameField.getText().trim().isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Please enter employee name!");
                return;
            }
            int age = Integer.parseInt(ageField.getText().trim());
            double salary = Double.parseDouble(salaryField.getText().trim());
            if (age < 18 || age > 65) 
            {
                JOptionPane.showMessageDialog(this, "Age must be between 18 and 65!");
                return;
            }
            Employee emp = employees.get(selectedRow);
            emp.setName(nameField.getText().trim());
            emp.setAge(age);
            emp.setGender((Gender) genderCombo.getSelectedItem());
            emp.setDepartment((Department) deptCombo.getSelectedItem());
            emp.setSalary(salary);
            FileUtil.save("employees.dat", employees);
            model.setValueAt(emp.getName(), selectedRow, 1);
            model.setValueAt(emp.getAge(), selectedRow, 2);
            model.setValueAt(emp.getGender().toString(), selectedRow, 3);
            model.setValueAt(emp.getDepartment().toString(), selectedRow, 4);
            model.setValueAt(String.format("$%.2f", emp.getSalary()), selectedRow, 5);
            clearForm();
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Invalid number format!");
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteEmployee() 
    {
        if (selectedRow < 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this employee?\nThis action cannot be undone.","Confirm Deletion",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) 
            {
            Employee deletedEmp = employees.remove(selectedRow);
            FileUtil.save("employees.dat", employees);
            model.removeRow(selectedRow);
            selectedRow = -1;
            clearForm();
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Employee '" + deletedEmp.getName() + "' deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadEmployeeToForm(int row) 
    {
        if (row >= 0 && row < employees.size()) 
        {
            Employee emp = employees.get(row);
            nameField.setText(emp.getName());
            ageField.setText(String.valueOf(emp.getAge()));
            salaryField.setText(String.valueOf(emp.getSalary()));
            genderCombo.setSelectedItem(emp.getGender());
            deptCombo.setSelectedItem(emp.getDepartment());
        }
    }
    private void clearForm() 
    {
        nameField.setText("");
        ageField.setText("");
        salaryField.setText("");
        genderCombo.setSelectedIndex(0);
        deptCombo.setSelectedIndex(0);
        nameField.requestFocus();
        selectedRow = -1;
    }

    private void loadEmployeesToTable() 
    {
        model.setRowCount(0);
        employees = FileUtil.load("employees.dat");
        
        for (Employee emp : employees) 
        {
            model.addRow(new Object[]{
                emp.getId(),
                emp.getName(),
                emp.getAge(),
                emp.getGender().toString(),
                emp.getDepartment().toString(),
                String.format("$%.2f", emp.getSalary())
            });
        }
    }
}