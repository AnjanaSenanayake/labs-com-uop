package pdn.co324;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class AddressBook 
{
	public HashMap<String, String[]> contacts = new HashMap<>();
	static FileReader fileReader;
	static BufferedReader bufferedReader;

	static String fieldNames[];
	static int maxId = 0;

	// read from file and create Address Book
	public void initAddressBook(String fileName)
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

	private void readContacts()
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
	
	public void addContact(String name, String[] details)
    {
        contacts.put(name, details);
    }

	public String[] searchContact(String name)
	{
		if (contacts.containsKey(name))
		{
			String[] contactDetails = contacts.get(name);
			return contactDetails;
		}
		return null;
	}
}
