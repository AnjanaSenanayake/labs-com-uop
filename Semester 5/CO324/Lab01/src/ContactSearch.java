

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ContactSearch
{
	static String search = "-s";
    static String addContact = "-a";
    static String deleteContact = "-d";
    

    static ContactList contactList = new ContactList();
    static FileWriter fileWriter;
    static BufferedWriter writer;
    static FileReader fileReader;
    static BufferedReader bufferedReader;

    static String csvFile="";
    static File inputFile;
    static File newFile;

    static String fieldNames[];
    static int maxId = 0;

    public static void main(String []args)
    {
    	try 
        {
            try
            {
                csvFile = args[0];
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("No Input File");
            }

            fileReader = new FileReader(csvFile);
            bufferedReader = new BufferedReader(fileReader);
            readContacts(csvFile);
            Scanner scanner = new Scanner(System.in);
            String[] token;

            for (String line = scanner.nextLine(); !line.equals("q"); line = scanner.nextLine())
            {
                token = line.split(" ");
                
                if(token[0].equals(addContact))
                {
                    add(token[1], token[2], Arrays.copyOfRange(token, 3, token.length));
                }
                else if(token[0].equals(search))
                {
                    search(token[1] + " " + token[2]);
                }
                else if(token[0].equals(deleteContact))
                {
                    delete(token[1] + " " + token[2]);
                }
                else
                {
                    System.out.println("Invalid Argument");
                    System.out.println("Search -> -s");
                    System.out.println("Add -> -a");
                    System.out.println("Delete -> -d");
                    System.out.println("Quit -> q");
                }
            }
            scanner.close();
        }
    	catch (Exception e) 
    	{
            System.err.println(e);
        }
    }


    private static void readContacts(String file)
    {
        try 
        {
            fieldNames = bufferedReader.readLine().split(",");
            
            int firstNameIndex = getFieldIndex("first_name");
            int lastNameIndex = getFieldIndex("last_name");
            int phoneNumIndex = getFieldIndex("phone");
            
            String[] info;
            for (String line = bufferedReader.readLine(); line!= null; line = bufferedReader.readLine())
            {
                info = line.split(",");
                contactList.addContact(info[firstNameIndex] + " " + info[lastNameIndex],
                Arrays.copyOfRange(info, phoneNumIndex, info.length));
                maxId++;
            }

            if (fileReader != null) 
            {
            	fileReader.close();
            }	
            if (bufferedReader != null) 
            {
            	bufferedReader.close();
            }

        }
        catch (IOException e)
        {
            System.out.println("File reading failed");
        }
    }

    private static int getFieldIndex(String key)
    {
        for (int i=0; i < fieldNames.length; i++)
        {
            if(fieldNames[i].equals(key)) return i;
        }
        return -1;
    }

    private static void search(String name)
    {
        contactList.searchContact(name);
    }

    private static void add(String f_name, String l_name, String[] numbers)
    {
        contactList.addContact(f_name + " " + l_name, numbers);

        try
        {
            fileWriter = new FileWriter(new File(csvFile), true);
            writer = new BufferedWriter(fileWriter);
            int index = ++maxId;
            writer.append(index + ",");
            writer.append(f_name);
            writer.append(",");
            writer.append(l_name);
            writer.append(",");

            for (int i=0;i<numbers.length;i++)
            {
                writer.append(numbers[i]);
                if(i == numbers.length-1)
                {
                    writer.append("\n");
                }
                else 
                {
                    writer.append(",");
                }
            }
            writer.flush();
        }
        catch(IOException e) 
        {
            System.out.println("Adding failed");
        }
    }

    private static void delete(String name)
    {
        contactList.deleteContact(name);
        
        try
        {
            inputFile = new File(csvFile);
            inputFile.delete();
            newFile = new File(csvFile);
            fileWriter = new FileWriter(newFile);
            writer = new BufferedWriter(fileWriter);
            writer.write("id,first_name,last_name,phone,email,company\n");
            int id = 1;
            
            for (String data : contactList.getCurrentList())
            {
                String row = id + "," + data;
                writer.write(row);
                id++;
            }

            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("Delete failed");
        }
    }
}
