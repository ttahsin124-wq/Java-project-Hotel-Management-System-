import java.io.*;
import java.util.ArrayList;


public class FileUtil 
{

    public static <T> void save(String file, ArrayList<T> list) 
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) 
        {
            oos.writeObject(list);
        } 
        catch (IOException e) 
        {
            System.err.println("Error saving to " + file + ": " + e.getMessage());
            
            try 
            {
                new File(file).createNewFile();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> load(String file) 
    {
        File dataFile = new File(file);
        if (!dataFile.exists()) 
        {
            return new ArrayList<>();
        }
        if (dataFile.length() == 0) 
        {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) 
        {
            return (ArrayList<T>) ois.readObject();
        } 
        catch (EOFException e) 
        {
            return new ArrayList<>();
        } 
        catch (IOException | ClassNotFoundException e) 
        {
            System.err.println("Error loading from " + file + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}