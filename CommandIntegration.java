
public class CommandIntegration {
    
    public static void integrateWithAdminDashboard(AdminDashboard dashboard) {
        CommandManager.getInstance().setParentFrame(dashboard);
        CommandInjector.injectUndoRedoButtons(dashboard);
    }
    
    public static void integrateWithReceptionDashboard(ReceptionDashboard dashboard) {
        CommandManager.getInstance().setParentFrame(dashboard);
        CommandInjector.injectUndoRedoButtons(dashboard);
    }
}