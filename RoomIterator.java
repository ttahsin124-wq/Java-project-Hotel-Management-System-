import java.util.ArrayList;

public class RoomIterator implements Iterator<HotelRoom> 
{
    private ArrayList<HotelRoom> rooms;
    private int position = 0;
    private Filter<HotelRoom> filter;
    public RoomIterator(ArrayList<HotelRoom> rooms) 
    {
        this.rooms = rooms;
        this.filter = null;
    }
    public RoomIterator(ArrayList<HotelRoom> rooms, Filter<HotelRoom> filter)
    {
        this.rooms = rooms;
        this.filter = filter;
    }
    
    @Override
    public boolean hasNext() 
    {
        if (filter == null) {
            return position < rooms.size();
        }
        int tempPosition = position;
        while (tempPosition < rooms.size()) 
        {
            if (filter.matches(rooms.get(tempPosition))) 
            {
                return true;
            }
            tempPosition++;
        }
        return false;
    }
    
    @Override
    public HotelRoom next() 
    {
        if (filter == null) 
        {
            return position < rooms.size() ? rooms.get(position++) : null;
        }
        while (position < rooms.size()) 
        {
            HotelRoom room = rooms.get(position++);
            if (filter.matches(room)) 
            {
                return room;
            }
        }
        return null;
    }
    
    @Override
    public void reset() 
    {
        position = 0;
    }
}