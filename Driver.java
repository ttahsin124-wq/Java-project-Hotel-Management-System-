import java.io.Serializable;

public class Driver implements Serializable 
{
    public String name;
    public int age;
    public Gender gender;
    public String carCompany;
    public String carName;
    public String location;
    public Driver(String name, int age, Gender gender,String carCompany, String carName, String location) 
    {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.carCompany = carCompany;
        this.carName = carName;
        this.location = location;
    }
    public String getName() 
    { 
        return name; 

    }
    public void setName(String name) 
    {  
        this.name = name; 
    }
    
    public int getAge() 
    { 
        return age; 
    }
    public void setAge(int age) 
    { 
        this.age = age; 
    }
    
    public Gender getGender() 
    { 
        return gender; 
    }
    public void setGender(Gender gender) 
    { 
        this.gender = gender; 
    }
    
    public String getCarCompany() 
    { 
        return carCompany; 
    }
    public void setCarCompany(String carCompany) 
    { 
        this.carCompany = carCompany; 
    }
    
    public String getCarName() 
    { 
        return carName; 
    }
    public void setCarName(String carName) 
    { 
        this.carName = carName; 
    }
    
    public String getLocation() 
    { 
        return location; 
    }
    public void setLocation(String location) 
    { 
        this.location = location; 
    }
}