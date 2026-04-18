import java.util.Stack;
import javax.swing.*;

public class CommandManager {

  private static CommandManager instance;
  private Stack<Command> undoStack = new Stack<>();
  private Stack<Command> redoStack = new Stack<>();
  private JFrame parentFrame;

  private CommandManager() {}

  public static CommandManager getInstance() {
    if (instance == null) {
      instance = new CommandManager();
    }
    return instance;
  }

  public void setParentFrame(JFrame frame) {
    this.parentFrame = frame;
  }

  public void executeCommand(Command command) {
    try {
      command.execute();

      if (command.isReversible()) {
        undoStack.push(command);
        redoStack.clear();
      }

      updateUI();

    } catch (Exception e) {
      if (parentFrame != null) {
        JOptionPane.showMessageDialog(
          parentFrame,
          "Command Error: " + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      }
    }
  }

  public void undo() {
    if (!undoStack.isEmpty()) {
      Command command = undoStack.pop();
      command.undo();
      redoStack.push(command);

      updateUI();

      if (parentFrame != null) {
        JOptionPane.showMessageDialog(
          parentFrame,
          "Undo: " + command.getDescription(),
          "Undo Successful",
          JOptionPane.INFORMATION_MESSAGE
        );
      }

    } else {
      if (parentFrame != null) {
        JOptionPane.showMessageDialog(
          parentFrame,
          "Nothing to undo!",
          "Undo",
          JOptionPane.INFORMATION_MESSAGE
        );
      }
    }
  }

  public void redo() {
    if (!redoStack.isEmpty()) {
      Command command = redoStack.pop();
      command.execute();
      undoStack.push(command);

      updateUI();

      if (parentFrame != null) {
        JOptionPane.showMessageDialog(
          parentFrame,
          "Redo: " + command.getDescription(),
          "Redo Successful",
          JOptionPane.INFORMATION_MESSAGE
        );
      }

    } else {
      if (parentFrame != null) {
        JOptionPane.showMessageDialog(
          parentFrame,
          "Nothing to redo!",
          "Redo",
          JOptionPane.INFORMATION_MESSAGE
        );
      }
    }
  }

  private void updateUI() {
    if (parentFrame != null) {
      parentFrame.repaint();
    }
  }

  public void clear() {
    undoStack.clear();
    redoStack.clear();
  }
}