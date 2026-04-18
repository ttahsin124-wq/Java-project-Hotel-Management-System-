import java.util.ArrayList;

public class AddCustomerCommand implements Command {

  private Object customerPanel;
  private Object customer;
  private ArrayList<Object> previousCustomers;
  private String customerName;

  public AddCustomerCommand(Object customerPanel, Object customer, String customerName) {
    this.customerPanel = customerPanel;
    this.customer = customer;
    this.customerName = customerName;
  }

  @Override
  public void execute() {
    try {
      java.lang.reflect.Method getCustomersMethod =
        customerPanel.getClass().getMethod("getCustomers");

      previousCustomers = new ArrayList<>(
        (ArrayList) getCustomersMethod.invoke(customerPanel)
      );

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo() {
    try {
      java.lang.reflect.Method setCustomersMethod =
        customerPanel.getClass().getMethod("setCustomers", ArrayList.class);

      setCustomersMethod.invoke(customerPanel, previousCustomers);

      java.lang.reflect.Method loadTableMethod =
        customerPanel.getClass().getDeclaredMethod("loadCustomersToTable");

      loadTableMethod.setAccessible(true);
      loadTableMethod.invoke(customerPanel);

      FileUtil.save("customers.dat", previousCustomers);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getDescription() {
    return "Add Customer: " + customerName;
  }

  @Override
  public boolean isReversible() {
    return true;
  }
}