import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ButtonActionWrapper implements ActionListener {

  private JButton originalButton;
  private ActionListener originalListener;
  private Command command;
  private CommandManager commandManager;

  public ButtonActionWrapper(JButton button, Command command) {
    this.originalButton = button;
    this.command = command;
    this.commandManager = CommandManager.getInstance();

    ActionListener[] listeners = button.getActionListeners();
    if (listeners.length > 0) {
      this.originalListener = listeners[0];
    }

    for (ActionListener l : listeners) {
      button.removeActionListener(l);
    }
    button.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    commandManager.executeCommand(command);


    if (originalListener != null) {
      originalListener.actionPerformed(e);
    }
  }
}