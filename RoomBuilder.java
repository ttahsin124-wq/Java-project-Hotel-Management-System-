// Builder Pattern for creating rooms with optional features
public class RoomBuilder {
    private int roomNo;
    private RoomCategory category;
    private double price;
    private boolean available;
    private boolean cleaned;
    private String specialFeatures;
    private String view;
    
    public RoomBuilder setRoomNo(int roomNo) 
    {
        this.roomNo = roomNo;
        return this;
    }
    
    public RoomBuilder setCategory(RoomCategory category) 
    {
        this.category = category;
        
        if (this.price == 0) 
            {
            switch (category) 
            {
                case SINGLE: this.price = 100.0; break;
                case DOUBLE: this.price = 150.0; break;
                case DELUXE: this.price = 250.0; break;
                case SUITE: this.price = 500.0; break;
            }
        }
        return this;
    }
    
    public RoomBuilder setPrice(double price) 
    {
        this.price = price;
        return this;
    }
    
    public RoomBuilder setAvailable(boolean available) 
    {
        this.available = available;
        return this;
    }
    
    public RoomBuilder setCleaned(boolean cleaned) 
    {
        this.cleaned = cleaned;
        return this;
    }
    
    public RoomBuilder setSpecialFeatures(String features) 
    {
        this.specialFeatures = features;
        return this;
    }
    
    public RoomBuilder setView(String view) 
    {
        this.view = view;
        return this;
    }
    
    public HotelRoom build() 
    {
        
        if (roomNo <= 0) 
        {
            throw new IllegalArgumentException("Room number must be positive");
        }
        if (category == null) 
        {
            throw new IllegalArgumentException("Room category must be specified");
        }
        if (price <= 0) 
        {
            throw new IllegalArgumentException("Price must be positive");
        }
        
        return new HotelRoom(roomNo, category, price, available, cleaned);
    }
}