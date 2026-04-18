import java.awt.*;
import javax.swing.*;

public class CommandInitializer {

  private static boolean initialized = false;

  public static void initializeCommands(JFrame frame) {
    if (initialized) return;

    findAllButtons(frame);

    initialized = true;
  }

  private static void findAllButtons(Container container) {
    for (Component comp : container.getComponents()) {

      if (comp instanceof JButton) {
        JButton button = (JButton) comp;
        String text = button.getText();

        if (text.contains("Add Room")) {


        } else if (text.contains("Delete Room")) {
         
        }

        

      } else if (comp instanceof Container) {
        findAllButtons((Container) comp);
      }
    }
  }
}