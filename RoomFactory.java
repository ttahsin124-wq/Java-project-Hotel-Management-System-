import java.util.ArrayList;

public interface RoomFactory 
{
    HotelRoom createRoom(int roomNo, double price, boolean available, boolean cleaned);
    ArrayList<HotelRoom> createDefaultRooms();
    String getRoomDescription();
}

class SingleRoomFactory implements RoomFactory 
{
    @Override
    public HotelRoom createRoom(int roomNo, double price, boolean available, boolean cleaned) 
    {
        return new HotelRoom(roomNo, RoomCategory.SINGLE, price, available, cleaned);
    }
    
    @Override
    public ArrayList<HotelRoom> createDefaultRooms() 
    {
        ArrayList<HotelRoom> rooms = new ArrayList<>();
        for (int i = 101; i <= 110; i++) 
        {
            rooms.add(new HotelRoom(i, RoomCategory.SINGLE, 100.0, true, true));
        }
        return rooms;
    }
    
    @Override
    public String getRoomDescription() 
    {
        return "Single Room - Perfect for solo travelers, includes basic amenities";
    }
}


class DoubleRoomFactory implements RoomFactory 
{
    @Override
    public HotelRoom createRoom(int roomNo, double price, boolean available, boolean cleaned) 
    {
        return new HotelRoom(roomNo, RoomCategory.DOUBLE, price, available, cleaned);
    }
    
    @Override
    public ArrayList<HotelRoom> createDefaultRooms() 
    {
        ArrayList<HotelRoom> rooms = new ArrayList<>();
        for (int i = 201; i <= 215; i++) 
        {
            rooms.add(new HotelRoom(i, RoomCategory.DOUBLE, 150.0, true, true));
        }
        return rooms;
    }
    
    @Override
    public String getRoomDescription() 
    {
        return "Double Room - Ideal for couples, queen-size bed, city view";
    }
}


class DeluxeRoomFactory implements RoomFactory 
{
    @Override

    public HotelRoom createRoom(int roomNo, double price, boolean available, boolean cleaned) 
    {
        return new HotelRoom(roomNo, RoomCategory.DELUXE, price, available, cleaned);
    }
    
    @Override
    public ArrayList<HotelRoom> createDefaultRooms() 
    {
        ArrayList<HotelRoom> rooms = new ArrayList<>();
        for (int i = 301; i <= 310; i++) 
        {
            rooms.add(new HotelRoom(i, RoomCategory.DELUXE, 250.0, true, true));
        }
        return rooms;
    }
    
    @Override
    public String getRoomDescription() 
    {
        return "Deluxe Room - Luxury amenities, king-size bed, sea view, mini-bar";
    }
}

class SuiteRoomFactory implements RoomFactory 
{
    @Override
    public HotelRoom createRoom(int roomNo, double price, boolean available, boolean cleaned) 
    {
        return new HotelRoom(roomNo, RoomCategory.SUITE, price, available, cleaned);
    }
    
    @Override
    public ArrayList<HotelRoom> createDefaultRooms() 
    {
        ArrayList<HotelRoom> rooms = new ArrayList<>();
        for (int i = 401; i <= 405; i++) 
        {
            rooms.add(new HotelRoom(i, RoomCategory.SUITE, 500.0, true, true));
        }
        return rooms;
    }
    
    @Override
    public String getRoomDescription() 
    {
        return "Suite - Premium luxury, separate living area, jacuzzi, panoramic view";
    }
}