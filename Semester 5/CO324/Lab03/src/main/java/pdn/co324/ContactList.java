package pdn.co324;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactList 
{
    private static HashMap<String,  String[]> contacts;

    public ContactList()
    {
        contacts = new HashMap<>();
    }

    public void addContact(String name, String[] details)
    {
        contacts.put(name, details);
    }
    
    public void deleteContact(String name)
    {
        if(contacts.containsKey(name))
        {
            contacts.remove(name);
        }
    }

    public void searchContact(String name)
    {
        if(contacts.containsKey(name))
        {
            String[] contactDetails = contacts.get(name);
            for (String number:contactDetails)
            {
                System.out.println(number);
            }
        }
    }

    public ArrayList<String> getCurrentList()
    {
        ArrayList<String> contactList = new ArrayList<>();

        for (HashMap.Entry<String, String[]> contact : contacts.entrySet())
        {
            String fullName[] = contact.getKey().split(" ");
            String contactData = fullName[0] + "," + fullName[1];
            String details[] = contact.getValue();
            
            for (int i = 0; i < details.length; i++)
            {
            	contactData += details[i];
                if(i == details.length-1)
                {
                	contactData += "\n";
                }
                else 
                {
                	contactData += ",";
                }
            }
            contactList.add(contactData);
        }
        return contactList;
    }

    public int getSize()
    {
        return contacts.size();
    }
}
