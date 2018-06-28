import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class AddressBook 
{
	private static HashMap<String, String[]> contacts = new HashMap<>();
	static FileReader fileReader;
	static BufferedReader bufferedReader;

	static String fieldNames[];
	static int maxId = 0;
	
/*	public static void main(String[]args)
	{
		initAddressBook("contacts.csv");
		System.out.println(search("Ashkey Lane"));
		
	}*/

	// read from file and create Address Book
	public static void initAddressBook(String fileName) 
	{
        try 
        {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			readContacts();
		}
        catch (FileNotFoundException e) 
        {		
			System.out.println("File Not Found");
		}
	}

	// search details of the requested contact in the address book
	public static String search(String name) 
	{
		 String[] details = searchContact(name);		
		 try
		 {
			 return details[0];
		 }
		 catch(NullPointerException e)
		 {
			 return "404";
		 }
	}
	
	private static void readContacts()
    {       
        try 
        {                                     
            String[] info;
            String line = "";
            while((line = bufferedReader.readLine())!= null)
            {
                info = line.split(",");
                addContact(info[0],Arrays.copyOfRange(info,1, info.length));
                maxId++;
            }
            
            if(fileReader != null) 
            {
            	//fileReader.close();
            }	
            if(bufferedReader != null) 
            {
            	bufferedReader.close();
            }
        }
        catch (IOException e)
        {
            //System.out.println("File reading failed");
        }
    }
	
	private static void addContact(String name, String[] details)
    {
        contacts.put(name, details);
    }

	private static String[] searchContact(String name) 
	{
		if (contacts.containsKey(name)) 
		{
			String[] contactDetails = contacts.get(name);
			return contactDetails;
		}
		return null;
	}
}
