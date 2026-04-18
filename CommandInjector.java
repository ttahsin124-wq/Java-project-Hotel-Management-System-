import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class CommandInjector {
    private static CommandManager commandManager = CommandManager.getInstance();
    private static Map<JButton, Command> commandMap = new HashMap<>();
    
    public static void injectUndoRedoButtons(JFrame frame) {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(33, 33, 33));
        
        JButton undoButton = createCommandButton("↩️ Undo", new Color(52, 73, 94));
        JButton redoButton = createCommandButton("↪️ Redo", new Color(52, 73, 94));
        
        undoButton.addActionListener(e -> commandManager.undo());
        redoButton.addActionListener(e -> commandManager.redo());
        
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        
        frame.add(toolBar, BorderLayout.NORTH);
        
  
        setupKeyboardShortcuts(frame);
    }
    
    private static JButton createCommandButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private static void setupKeyboardShortcuts(JFrame frame) {
    
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, 
                 java.awt.event.InputEvent.CTRL_DOWN_MASK), "undo");
        frame.getRootPane().getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                commandManager.undo();
            }
        });

        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, 
                 java.awt.event.InputEvent.CTRL_DOWN_MASK), "redo");
        frame.getRootPane().getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                commandManager.redo();
            }
        });
    }
    
    public static void captureButtonAction(JButton button, Command command) {
        commandMap.put(button, command);
        
        ActionListener[] listeners = button.getActionListeners();
        if (listeners.length > 0) {
            ActionListener originalListener = listeners[0];
            button.removeActionListener(originalListener);
            button.addActionListener(e -> {
                commandManager.executeCommand(command);
                originalListener.actionPerformed(e);
            });
        }
    }
}