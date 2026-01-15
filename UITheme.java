import java.awt.*;
import javax.swing.*;
public class UITheme 
{
   
    public static final Color DARK_BG = new Color(20, 20, 20);          
    public static final Color CARD_BG = new Color(33, 33, 33);          
    public static final Color SECONDARY_COLOR = new Color(45, 45, 45);  
    public static final Color PRIMARY_COLOR = new Color(52, 73, 94);    
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);  
    public static final Color DANGER_COLOR = new Color(231, 76, 60);    
    public static final Color WARNING_COLOR = new Color(241, 196, 15);  
    public static final Color TEXT_COLOR = Color.WHITE;  
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    public static void apply() 
    {
        try 
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", DARK_BG);
            UIManager.put("nimbusBlueGrey", CARD_BG);
            UIManager.put("control", CARD_BG);
            UIManager.put("text", TEXT_COLOR);
            UIManager.put("nimbusSelection", PRIMARY_COLOR);
            UIManager.put("nimbusLightBackground", CARD_BG);
            UIManager.put("Button.font", NORMAL_FONT);
            UIManager.put("Label.font", NORMAL_FONT);
            UIManager.put("TextField.font", NORMAL_FONT);
            UIManager.put("PasswordField.font", NORMAL_FONT);
            UIManager.put("ComboBox.font", NORMAL_FONT);
            UIManager.put("Table.font", SMALL_FONT);
            UIManager.put("TableHeader.font", HEADER_FONT);
            UIManager.put("List.font", NORMAL_FONT);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public static JButton createStyledButton(String text, Color bg) 
    {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setFont(HEADER_FONT);
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(bg.darker(), 2),BorderFactory.createEmptyBorder(10, 20, 10, 20)));
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
    public static JTextField createStyledTextField() 
    {
        JTextField field = new JTextField();
        field.setBackground(CARD_BG);
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(TEXT_COLOR);
        field.setFont(NORMAL_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)),BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        return field;
    }
    public static JComboBox<String> createStyledComboBox(String[] items) 
    {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBackground(CARD_BG);
        combo.setForeground(TEXT_COLOR);
        combo.setFont(NORMAL_FONT);
        combo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)),BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return combo;
    }
}