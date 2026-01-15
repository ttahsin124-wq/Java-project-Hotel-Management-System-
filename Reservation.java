import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable 
{
    private int reservationId;
    private Customer customer;
    private HotelRoom room;
    private Date checkIn;
    private Date checkOut;
    private double totalAmount;
    private String status; 
    private Date createdDate;
    private String specialRequests;
    public Reservation(int reservationId, Customer customer, HotelRoom room, Date checkIn, Date checkOut, double totalAmount, String status, String specialRequests) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdDate = new Date();
        this.specialRequests = specialRequests;
    }
    public int getReservationId() 
    { 
        return reservationId; 
    }
    public Customer getCustomer() 
    { 
        return customer; 
    }
    public HotelRoom getRoom() 
    { 
        return room; 
    }
    public Date getCheckIn() 
    { 
        return checkIn; 
    }
    public Date getCheckOut() 
    { 
        return checkOut; 
    }
    public double getTotalAmount() 
    { 
        return totalAmount; 
    }
    public String getStatus() 
    { 
        return status; 
    }
    public Date getCreatedDate() 
    { 
        return createdDate; 
    }
    public String getSpecialRequests() 
    { 
        return specialRequests; 
    }
    
    public void setStatus(String status) 
    { 
        this.status = status; 
    }
    public void setCheckIn(Date checkIn) 
    { 
        this.checkIn = checkIn; 
    }
    public void setCheckOut(Date checkOut) 
    { 
        this.checkOut = checkOut; 
    }
    public void setTotalAmount(double totalAmount) 
    { 
        this.totalAmount = totalAmount; 
    }
    public void setSpecialRequests(String specialRequests) 
    { 
        this.specialRequests = specialRequests; 
    }
    public void setRoom(HotelRoom room) 
    {
    this.room = room;
    }
    @Override
    public String toString() 
    {
        return "Reservation [ID=" + reservationId + ", Customer=" + customer.getName() +  ", Room=" + room.roomNo + ", Status=" + status + "]";
    }
}