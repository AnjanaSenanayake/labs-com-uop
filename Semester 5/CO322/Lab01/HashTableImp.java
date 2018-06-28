public class HashTableImp implements HashTable
{
   private static class ListNode 
   {
      String key;
      int value=0;
      ListNode next;  //Pointer to next node in the list;
   }

   public ListNode[] table;  //The hash table                            
   private int count;  //The number of (key,value) pairs in the hash table                   
   
   public HashTableImp(int initialSize) 
   {
      if(initialSize <= 0) throw new IllegalArgumentException("Illegal table size");
      	table = new ListNode[initialSize];
   }
   
   public void insert(String key) 
   {   
      assert key != null : "The key must be non-null";
      
      int bucket = hash(key); //Calculating Hash code for the given key and get bucket number
      //System.out.println(bucket);
      ListNode list=null;
      try{list = table[bucket]; //For traversing the linked list}
      
      }catch(ArrayIndexOutOfBoundsException e) {
    	  System.out.println(bucket);
      }
                              
      while (list != null) 
      {
         //Search the nodes in the list, to see if the key already exists.
         if (list.key.equals(key))
            break;
         list = list.next;
      }
      
      //At this point, either list is null, or list.key.equals(key).
      if (list != null) 
      {
         //Since list is not null, we have found the key.
         //Update the value
         list.value++;
      }
      else 
      {
         //Since list == null, the key is not already in the list.
         //Add a new node at the head of the list to contain the new key             
        /* if (count >= 0.90*table.length) 
         {
            //The table is becoming too full.  Increase its size
            //before adding the new node.
            resize();
            bucket = hash(key);  //Recompute hash code, since it depends on the table size.                                 
         }*/
         
         ListNode newNode = new ListNode();
         newNode.key = key;
         newNode.value++;
         newNode.next = table[bucket];
         table[bucket] = newNode;
         count++;  //Count the newly added key.
      }
   }

   //Retrieve the value associated with the specified key in the table, 
   //if there is any.  If not, the value null will be returned.
   public int search(String key) 
   {   
      int bucket = hash(key);
      ListNode list = table[bucket];
      while (list != null) 
      {                 
         if (list.key.equals(key))
            return list.value;
         list = list.next;
      }
      //If it get to this point, then it have looked at every node in the list without finding the key.
      //Hence return 0 to indicate that the key does not exists.
      return 0;  
   }
   
   //Test whether the specified key is already in the table.
   public boolean containsKey(String key) 
   {   
      int bucket = hash(key);
      ListNode list = table[bucket];
      
      while (list != null) 
      {
         //If we find the key in this node, return true.
         if (list.key.equals(key))
            return true;
         list = list.next;
      }
      
      //If we get to this point, we know that the key does not exists.
      return false;
   }
   
   //Return the number of key/value pairs in the table.
   public int getSize() 
   {
      return count;
   }

   //Compute a hash code for the key; key cannot be null.
   //The hash code depends on the size of the table.
   private int hash(String key) 
   {
	   int h = 0;
	   for (int i = 0; i < key.length(); i++)
	   {
		   //multiplied with 31,an odd number
		   h = (523* h + key.charAt(i))%table.length;		   
	   }	   
	   return (h%table.length);   
   }
   
  

   /**
    * Double the size of the table, and redistribute the
    * key/value pairs to their proper locations in the
    * new table.
    */
//   private void resize() 
//   {
//      ListNode[] newtable = new ListNode[table.length*2];
//      for (int i = 0; i < table.length; i++) 
//      {
//         ListNode list = table[i];
//         while (list != null) 
//         {
//            //Move the node pointed to by list to the new table.
//            ListNode next = list.next;  //The is the next node in the list.
//            int hash = (Math.abs(list.key.hashCode())) % newtable.length;            
//            list.next = newtable[hash];
//            newtable[hash] = list;
//            list = next;  //Move on to the next node in the OLD table.
//         }
//      }
//      table = newtable;  //Replace the table with the new table.
//   }  
   
   //Returns the number of nodes in the specified bucket
   public void getBucketSize(int length)  
   {
	   int x2=0;
	   int sum = 0;
	   for(int i=0;i<length;i++)
	   { 		
		   int counter=0;
		   ListNode list = table[i];
		   while(list != null)
		   {		 
			   list = list.next;
			   counter++;
		   }
		   sum+=counter;
		   x2=x2+(int) Math.pow(counter,2);
		   System.out.println((i+1)+":"+counter);
	   }	   
	   float avg = (float)(sum/(float)length);
	   float var = (float)(x2/length) - (float)(Math.pow(avg, 2));
	   System.out.println("Total:"+sum);
	   System.out.println("Avg:"+avg);
	   System.out.println("Deviation:"+Math.sqrt(var));
   }
}
