import java.util.*;
import javax.swing.SwingUtilities;

public class DataRepository {
    private static DataRepository instance;
    
    // all list to save data's from different panal
    private ArrayList<Customer> customers;
    private ArrayList<HotelRoom> rooms;
    private ArrayList<Reservation> reservations;
    

    private List<CustomerListener> customerListeners = new ArrayList<>();
    private List<RoomListener> roomListeners = new ArrayList<>();
    private List<ReservationListener> reservationListeners = new ArrayList<>();
    
    private DataRepository() {
        loadAll();
    }
    
    public static DataRepository getInstance() {
        if (instance == null) instance = new DataRepository();
        return instance;
    }
    
    private void loadAll() {
        customers = FileUtil.load("customers.dat");
        rooms = FileUtil.load("rooms.dat");
        reservations = FileUtil.load("reservations.dat");
    }
    
    private void saveAll() {
        FileUtil.save("customers.dat", customers);
        FileUtil.save("rooms.dat", rooms);
        FileUtil.save("reservations.dat", reservations);
    }
    
    // --- Customer operations ---
    public void addCustomer(Customer c) {
        customers.add(c);
        saveAll();
        notifyCustomerChanged();
    }
    
    public void updateCustomer(int index, Customer c) {
        customers.set(index, c);
        saveAll();
        notifyCustomerChanged();
    }
    
    public void deleteCustomer(int index) {
        customers.remove(index);
        saveAll();
        notifyCustomerChanged();
    }
    
    public ArrayList<Customer> getCustomers() { return customers; }
    
    // --- Room operations ---
    public void addRoom(HotelRoom r) {
        rooms.add(r);
        saveAll();
        notifyRoomChanged();
    }
    
    public void updateRoom(int index, HotelRoom r) {
        rooms.set(index, r);
        saveAll();
        notifyRoomChanged();
    }
    
    public void deleteRoom(int index) {
        rooms.remove(index);
        saveAll();
        notifyRoomChanged();
    }
    
    public ArrayList<HotelRoom> getRooms() { return rooms; }
    
    // --- Reservation operations ---
    public void addReservation(Reservation r) {
        reservations.add(r);
        saveAll();
        notifyReservationChanged();
    }
    
    public void updateReservation(int index, Reservation r) {
        reservations.set(index, r);
        saveAll();
        notifyReservationChanged();
    }
    
    public void deleteReservation(int index) {
        reservations.remove(index);
        saveAll();
        notifyReservationChanged();
    }
    
    public ArrayList<Reservation> getReservations() { return reservations; }
    
    // --- Listener registration & notification ---
    public void addCustomerListener(CustomerListener l) { customerListeners.add(l); }
    public void addRoomListener(RoomListener l) { roomListeners.add(l); }
    public void addReservationListener(ReservationListener l) { reservationListeners.add(l); }
    
    private void notifyCustomerChanged() {
        SwingUtilities.invokeLater(() -> {
            for (CustomerListener l : customerListeners) l.onCustomerDataChanged();
        });
    }
    
    private void notifyRoomChanged() {
        SwingUtilities.invokeLater(() -> {
            for (RoomListener l : roomListeners) l.onRoomDataChanged();
        });
    }
    
    private void notifyReservationChanged() {
        SwingUtilities.invokeLater(() -> {
            for (ReservationListener l : reservationListeners) l.onReservationDataChanged();
        });
    }
    
    // Listener interfaces
    public interface CustomerListener {
        void onCustomerDataChanged();
    }
    public interface RoomListener {
        void onRoomDataChanged();
    }
    public interface ReservationListener {
        void onReservationDataChanged();
    }
}