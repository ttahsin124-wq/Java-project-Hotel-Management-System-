import javax.swing.SwingUtilities;

public class MainApp 
{
    public static void main(String[] args) 
    {

        SwingUtilities.invokeLater(() -> 
        {
            LoginFrame loginFrame = new LoginFrame();
            new LoginFrame().setVisible(true);
            CommandManager.getInstance().setParentFrame(loginFrame);
            
        });
    }
}