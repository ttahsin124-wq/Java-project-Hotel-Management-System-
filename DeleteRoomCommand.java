import java.util.ArrayList;

public class DeleteRoomCommand implements Command {

  private Object roomPanel;
  private Object deletedRoom;
  private int deletedIndex;
  private ArrayList<Object> previousRooms;
  private String roomNumber;

  public DeleteRoomCommand(Object roomPanel, Object room, int index, String roomNumber) {
    this.roomPanel = roomPanel;
    this.deletedRoom = room;
    this.deletedIndex = index;
    this.roomNumber = roomNumber;
  }

  @Override
  public void execute() {
    try {
      java.lang.reflect.Method getRoomsMethod =
        roomPanel.getClass().getMethod("getRooms");

      previousRooms = new ArrayList<>(
        (ArrayList) getRoomsMethod.invoke(roomPanel)
      );

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo() {
    try {
      java.lang.reflect.Method getRoomsMethod =
        roomPanel.getClass().getMethod("getRooms");

      ArrayList<Object> currentRooms =
        (ArrayList) getRoomsMethod.invoke(roomPanel);

      currentRooms.add(deletedIndex, deletedRoom);


      java.lang.reflect.Method loadTableMethod =
        roomPanel.getClass().getDeclaredMethod("loadRoomsToTable");

      loadTableMethod.setAccessible(true);
      loadTableMethod.invoke(roomPanel);

      FileUtil.save("rooms.dat", currentRooms);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getDescription() {
    return "Delete Room #" + roomNumber;
  }

  @Override
  public boolean isReversible() {
    return true;
  }
}