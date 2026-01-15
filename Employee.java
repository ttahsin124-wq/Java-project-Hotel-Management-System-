import java.io.Serializable;

public class Employee implements Serializable 
{
    private int id;
    private String name;
    private int age;
    private Gender gender;
    private Department department;
    private double salary;
    public Employee(int id, String name, int age, Gender gender, Department department, double salary) 
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.department = department;
        this.salary = salary;
    }
    public int getId() 
    { 
        return id; 
    }
    public String getName() 
    { 
        return name; 
    }
    public int getAge() 
    { 
        return age; 
    }
    public Gender getGender() 
    { 
        return gender; 
    }
    public Department getDepartment() 
    { 
        return department; 
    }
    public double getSalary() 
    { 
        return salary; 
    }
    public void setId(int id) 
    { 
        this.id = id; 

    }
    public void setName(String name) 
    { 
        this.name = name; 
    }
    public void setAge(int age) 
    { 
        this.age = age; 
    }
    public void setGender(Gender gender) 
    { 
        this.gender = gender; 
    }
    public void setDepartment(Department department) 
    { 
        this.department = department; 
    }
    public void setSalary(double salary) 
    { 
        this.salary = salary; 
    }
    
    @Override
    public String toString() 
    {
        return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", department=" + department + ", salary=" + salary + "]";
    }
}