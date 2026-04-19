import java.util.ArrayList;

public class RoomCollection {
    private ArrayList<HotelRoom> rooms;
    
    public RoomCollection(ArrayList<HotelRoom> rooms) 
    {
        this.rooms = rooms;
    }
    public Iterator<HotelRoom> getAllRoomsIterator() 
    {
        return new RoomIterator(rooms);
    }
    public Iterator<HotelRoom> getAvailableRoomsIterator() 
    {
        return new RoomIterator(rooms, new AvailableFilter());
    }
    public Iterator<HotelRoom> getOccupiedRoomsIterator() 
    {
        return new RoomIterator(rooms, new OccupiedFilter());
    }
    public Iterator<HotelRoom> getCleanRoomsIterator() 
    {
        return new RoomIterator(rooms, new CleanFilter());
    }
    public Iterator<HotelRoom> getDirtyRoomsIterator() 
    {
        return new RoomIterator(rooms, new DirtyFilter());
    }
    public Iterator<HotelRoom> getRoomsByCategoryIterator(RoomCategory category) 
    {
        return new RoomIterator(rooms, new CategoryFilter(category));
    }
    private static class AvailableFilter implements Filter<HotelRoom> 
    {
        @Override
        public boolean matches(HotelRoom room) 
        {
            return room.available;
        }
    }
    
    private static class OccupiedFilter implements Filter<HotelRoom> 
    {
        @Override
        public boolean matches(HotelRoom room) 
        {
            return !room.available;
        }
    }
    
    private static class CleanFilter implements Filter<HotelRoom> 
    {
        @Override
        public boolean matches(HotelRoom room) 
        {
            return room.cleaned;
        }
    }
    
    private static class DirtyFilter implements Filter<HotelRoom> 
    {
        @Override
        public boolean matches(HotelRoom room) 
        {
            return !room.cleaned;
        }
    }
    
    private static class CategoryFilter implements Filter<HotelRoom> 
    {
        private RoomCategory category;
        
        public CategoryFilter(RoomCategory category) 
        {
            this.category = category;
        }
        
        @Override
        public boolean matches(HotelRoom room) 
        {
            return room.category == category;
        }
    }
}
