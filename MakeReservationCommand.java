import java.util.ArrayList;

public class MakeReservationCommand implements Command {

  private Object reservationPanel;
  private Object reservation;
  private ArrayList<Object> previousReservations;
  private String reservationId;

  public MakeReservationCommand(Object reservationPanel, Object reservation, String reservationId) {
    this.reservationPanel = reservationPanel;
    this.reservation = reservation;
    this.reservationId = reservationId;
  }

  @Override
  public void execute() {
    try {
      java.lang.reflect.Method getReservationsMethod =
        reservationPanel.getClass().getMethod("getReservations");

      previousReservations = new ArrayList<>(
        (ArrayList) getReservationsMethod.invoke(reservationPanel)
      );

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo() {
    try {
      java.lang.reflect.Method setReservationsMethod =
        reservationPanel.getClass().getMethod("setReservations", ArrayList.class);

      setReservationsMethod.invoke(reservationPanel, previousReservations);

      java.lang.reflect.Method loadTableMethod =
        reservationPanel.getClass().getDeclaredMethod("loadReservationsToTable");

      loadTableMethod.setAccessible(true);
      loadTableMethod.invoke(reservationPanel);

      FileUtil.save("reservations.dat", previousReservations);

      // Also make room available again
      try {
        java.lang.reflect.Method getRoomMethod =
          reservation.getClass().getMethod("getRoom");

        Object room = getRoomMethod.invoke(reservation);

        java.lang.reflect.Method setAvailableMethod =
          room.getClass().getMethod("setAvailable", boolean.class);

        setAvailableMethod.invoke(room, true);

        FileUtil.save("rooms.dat", DataRepository.getInstance().getRooms());

      } catch (Exception e) {
        e.printStackTrace();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getDescription() {
    return "Make Reservation #" + reservationId;
  }

  @Override
  public boolean isReversible() {
    return true;
  }
}