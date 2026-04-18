public class EditRoomCommand implements Command {

  private Object roomPanel;
  private Object oldRoom;
  private Object newRoom;
  private int roomIndex;
  private String roomNumber;

  public EditRoomCommand(Object roomPanel, Object oldRoom, Object newRoom, int index, String roomNumber) {
    this.roomPanel = roomPanel;
    this.oldRoom = oldRoom;
    this.newRoom = newRoom;
    this.roomIndex = index;
    this.roomNumber = roomNumber;
  }

  @Override
  public void execute() {

  }

  @Override
  public void undo() {
    try {
        java.lang.reflect.Method getRoomsMethod =
        roomPanel.getClass().getMethod("getRooms");

      java.util.ArrayList<Object> rooms =
        (java.util.ArrayList) getRoomsMethod.invoke(roomPanel);


      java.lang.reflect.Field[] fields =
        oldRoom.getClass().getDeclaredFields();

      for (java.lang.reflect.Field field : fields) {
        field.setAccessible(true);
        Object value = field.get(oldRoom);
        field.set(rooms.get(roomIndex), value);
      }

      java.lang.reflect.Method loadTableMethod =
        roomPanel.getClass().getDeclaredMethod("loadRoomsToTable");

      loadTableMethod.setAccessible(true);
      loadTableMethod.invoke(roomPanel);

      FileUtil.save("rooms.dat", rooms);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getDescription() {
    return "Edit Room #" + roomNumber;
  }

  @Override
  public boolean isReversible() {
    return true;
  }
}