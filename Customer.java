import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable 
{
    private int id;
    private String name;
    private String phone;
    private String email;
    private Date checkIn;
    private Date checkOut;
    private Integer roomNumber; 
    public Customer(int id, String name, String phone, String email, Date checkIn, Date checkOut,Integer roomNumber) 
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomNumber=roomNumber;
    }
    public int getId() 
    { 
        return id; 

    }
    public String getName() 
    { 
        return name; 
    }
    public String getPhone() 
    { 
        return phone; 
    }
    public String getEmail() 
    { 
        return email; 
    }
    public Date getCheckIn() 
    { 
        return checkIn; 
    }
    public Date getCheckOut() 
    { 
        return checkOut; 
    }
    public Integer getRoomNumber() 
    { 
        return roomNumber; 
    }
    public void setRoomNumber(Integer roomNumber) 
    { 
        this.roomNumber = roomNumber; 
    }
    public void setId(int id) 
    { 
        this.id = id; 
    }
    public void setName(String name) 
    { 
        this.name = name; 
    }
    public void setPhone(String phone) 
    { 
        this.phone = phone; 
    }
    public void setEmail(String email) 
    { 
        this.email = email; 
    }
    public void setCheckIn(Date checkIn)
    { 
        this.checkIn = checkIn; 
    }
    public void setCheckOut(Date checkOut) 
    { 
        this.checkOut = checkOut; 
    }
    
    @Override
    public String toString() 
    {
        return "Customer [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", checkIn=" + checkIn + ", checkOut=" + checkOut + "]";
    }
}