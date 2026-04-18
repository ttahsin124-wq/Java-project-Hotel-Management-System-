import java.lang.reflect.Method;
import java.util.ArrayList;

public class AddRoomCommand implements Command {

  private Object roomPanel;
  private Object room;
  private ArrayList<Object> previousRooms;
  private String roomNumber;

  public AddRoomCommand(Object roomPanel, Object room, String roomNumber) {
    this.roomPanel = roomPanel;
    this.room = room;
    this.roomNumber = roomNumber;
  }

  @Override
  public void execute() {
    try {
      Method getRoomsMethod = roomPanel.getClass().getMethod("getRooms");
      previousRooms = new ArrayList<>((ArrayList) getRoomsMethod.invoke(roomPanel));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo() {
    try {
      Method setRoomsMethod = roomPanel.getClass()
        .getMethod("setRooms", ArrayList.class);
      setRoomsMethod.invoke(roomPanel, previousRooms);

      Method loadTableMethod = roomPanel.getClass()
        .getDeclaredMethod("loadRoomsToTable");
      loadTableMethod.setAccessible(true);
      loadTableMethod.invoke(roomPanel);

      Method saveMethod = roomPanel.getClass().getMethod("saveRooms");
      if (saveMethod != null) {
        saveMethod.invoke(roomPanel);
      } else {
        FileUtil.save("rooms.dat", previousRooms);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getDescription() {
    return "Add Room #" + roomNumber;
  }

  @Override
  public boolean isReversible() {
    return true;
  }
}