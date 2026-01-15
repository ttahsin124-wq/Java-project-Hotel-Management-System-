import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginFrame extends JFrame 
{
    
    public LoginFrame() 
    {
        UITheme.apply();
        setTitle(" Hotel Management System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UITheme.DARK_BG);
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(UITheme.DARK_BG);
        JLabel titleLabel = new JLabel("Hotel Management System");
        titleLabel.setFont(UITheme.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        JButton btnAdmin = UITheme.createStyledButton(" Admin Login", UITheme.PRIMARY_COLOR);
        JButton btnReception = UITheme.createStyledButton("Reception Panel", UITheme.SUCCESS_COLOR);
        JButton btnGuest = UITheme.createStyledButton("Guest Portal", UITheme.WARNING_COLOR);
        btnAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReception.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGuest.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(btnAdmin);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(btnReception);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(btnGuest);
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("Hotel Management System ");
        footerLabel.setForeground(new Color(200, 200, 200));
        footerPanel.add(footerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        add(mainPanel);
        btnAdmin.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                showAdminLogin();
            }
        });
        btnReception.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new ReceptionDashboard().setVisible(true);
                dispose();
            }
        });
        btnGuest.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                JOptionPane.showMessageDialog(LoginFrame.this, "Guest Portal Coming Soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        setVisible(true);
    }
    
    private void showAdminLogin() 
    {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(UITheme.DARK_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel titleLabel = new JLabel(" Admin Authentication");
        titleLabel.setFont(UITheme.HEADER_FONT);
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(UITheme.TEXT_COLOR);
        loginPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        JTextField userField = UITheme.createStyledTextField();
        userField.setPreferredSize(new Dimension(200, 35));
        loginPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(UITheme.TEXT_COLOR);
        loginPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        JPasswordField passField = new JPasswordField();
        passField.setBackground(UITheme.CARD_BG);
        passField.setForeground(UITheme.TEXT_COLOR);
        passField.setCaretColor(UITheme.TEXT_COLOR);
        passField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)),BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        passField.setPreferredSize(new Dimension(200, 35));
        loginPanel.add(passField, gbc);
        int result = JOptionPane.showConfirmDialog(this, loginPanel, "Admin Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) 
        {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            if (username.equals("admin") && password.equals("admin123")) 
            {
                new AdminDashboard().setVisible(true);
                dispose();
            } else 
            {
                JOptionPane.showMessageDialog(this,"Invalid credentials!","Access Denied",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}