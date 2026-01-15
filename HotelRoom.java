import java.io.Serializable;

public class HotelRoom implements Serializable 
{
    public int roomNo;
    public RoomCategory category;
    public double price;
    public boolean available;
    public boolean cleaned;
    public HotelRoom(int roomNo, RoomCategory category,double price, boolean available, boolean cleaned) 
    {
        this.roomNo = roomNo;
        this.category = category;
        this.price = price;
        this.available = available;
        this.cleaned = cleaned;
    }
    public int getRoomNo() 
    { 
        return roomNo; 
    }
    public void setRoomNo(int roomNo) 
    { 
        this.roomNo = roomNo; 
    }
    
    public RoomCategory getCategory() 
    { 
        return category; 
    }
    public void setCategory(RoomCategory category) 
    { 
        this.category = category; 
    }
    
    public double getPrice() 
    { 
        return price; 
    }
    public void setPrice(double price) 
    { 
        this.price = price; 
    }
    
    public boolean isAvailable() 
    { 
        return available; 
    }
    public void setAvailable(boolean available) 
    { 
        this.available = available; 
    }
    
    public boolean isCleaned() 
    { 
        return cleaned; 
    }
    public void setCleaned(boolean cleaned) 
    { 
        this.cleaned = cleaned;
    }
}