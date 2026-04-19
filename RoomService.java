import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomService {
    private FileUtil fileUtil;
    private Map<RoomCategory, RoomFactory> factoryMap;
    
    public RoomService() {
        fileUtil = FileUtil.getInstance();
        initializeFactories();
    }
    
    private void initializeFactories() 
    {
        factoryMap = new HashMap<>();
        factoryMap.put(RoomCategory.SINGLE, new SingleRoomFactory());
        factoryMap.put(RoomCategory.DOUBLE, new DoubleRoomFactory());
        factoryMap.put(RoomCategory.DELUXE, new DeluxeRoomFactory());
        factoryMap.put(RoomCategory.SUITE, new SuiteRoomFactory());
    }
    public HotelRoom createRoom(int roomNo, RoomCategory category, double price, 
                                boolean available, boolean cleaned) 
        {
        RoomFactory factory = factoryMap.get(category);
        if (factory == null) 
        {
            throw new IllegalArgumentException("Unknown room category: " + category);
        }
        return factory.createRoom(roomNo, price, available, cleaned);
    }
    
    public boolean addRoom(int roomNo, RoomCategory category, double price, 
                          boolean available, boolean cleaned) 
    {
        ArrayList<HotelRoom> rooms = fileUtil.load("rooms.dat");
        for (HotelRoom room : rooms) 
        {
            if (room.getRoomNo() == roomNo) 
            {
                return false;
            }
        }
        
        HotelRoom newRoom = createRoom(roomNo, category, price, available, cleaned);
        rooms.add(newRoom);
        fileUtil.save("rooms.dat", rooms);
        return true;
    }
    
    public void initializeDefaultRooms() 
    {
        ArrayList<HotelRoom> existingRooms = fileUtil.load("rooms.dat");
        ArrayList<HotelRoom> allRooms = new ArrayList<>(existingRooms);
        
        for (RoomCategory category : RoomCategory.values()) 
            {
            RoomFactory factory = factoryMap.get(category);
            if (factory != null) 
            {
                
                boolean hasCategory = false;
                for (HotelRoom room : existingRooms) 
                {
                    if (room.getCategory() == category) 
                    {
                        hasCategory = true;
                        break;
                    }
                }
                if (!hasCategory) 
                {
                    allRooms.addAll(factory.createDefaultRooms());
                }
            }
        }
        
        fileUtil.save("rooms.dat", allRooms);
    }
  
    public ArrayList<HotelRoom> getRoomsByCategory(RoomCategory category) 
    {
        ArrayList<HotelRoom> allRooms = fileUtil.load("rooms.dat");
        ArrayList<HotelRoom> filteredRooms = new ArrayList<>();
        
        for (HotelRoom room : allRooms) 
        {
            if (room.getCategory() == category) 
            {
                filteredRooms.add(room);
            }
        }
        return filteredRooms;
    }
    
    public String getRoomCategoryDescription(RoomCategory category) 
    {
        RoomFactory factory = factoryMap.get(category);
        if (factory != null) 
        {
            return factory.getRoomDescription();
        }
        return "No description available";
    }
}