
/* my array list: an array that behaves like a list 
 * E/14/317
 */

public class MySet<T extends Comparable<T>>{ 

	T item;
    int nextItem; 
    int currentCapacity; 
    T[] data; // DO NOT CHANGE

    private static int defaultSizeToCreate = 10; // how many elements to create 

	@SuppressWarnings("unchecked")
	public MySet(int elements) 
	{ 
	this.nextItem = 0; 
	defaultSizeToCreate = elements; 
	this.currentCapacity = elements; 
	this.data = (T[]) new Comparable[defaultSizeToCreate]; 
    }

    public MySet() { 
	this(defaultSizeToCreate); 
    }

    public boolean isEmpty() { return this.nextItem == 0; } 
    public boolean isFull() { return false; /* never get filled */} 

    @SuppressWarnings("unchecked")
	public boolean add(T item) 
    { 	
    	/* if there is any element delete it 
		 * then add the new element. 
		 * How do you handle when the array is full:
		 * crate a new array with currentCapacity+defaultSizeToCreate, 
		 * copy the old contents into that
		 * Accessing array when full might be a problem
		 * Add the item to the array if the item is not there 
		 */
    	this.item=item;
    	int i=0;
    	while(i<currentCapacity && data[i]!=null)
    	{
    		if(item.compareTo(data[i])==0)
    		{
    			return false;
    		}	
    		i++;
    	}
        if(nextItem<currentCapacity)
        {
    		data[i]=item;
    		nextItem++;
    		try 
    		{    	
    			data[nextItem] = null;// DO NOT CHANGE
    		}
    		catch(ArrayIndexOutOfBoundsException e){};
    		return true;
    	}
    	else
    	{
			T[] newdata = (T[])new Comparable[currentCapacity+defaultSizeToCreate];
    		int j;
    		for(j=0;j<currentCapacity;j++)
    		{
    			newdata[j]=data[j];
    		}
    		data=newdata;
    		data[currentCapacity]=item;
    		currentCapacity=currentCapacity+defaultSizeToCreate;
    		nextItem++;
    		return true;
    	}	
    }
    public T remove() 
    { 
	/* remove the first element in the array 
	 * and copy the rest front. 
	 * FIFO structure. 
	 * If the ArrayList is empty return null
	 */
	T str;
    /* Option 1: */
	if(isEmpty()) 
	{	// IMPLEMENT THE REST 
		return null;
	}	
	else
	{
		str=data[0];
		for(int i=0;i<currentCapacity-1;i++)
		{
			data[i]=data[i+1];
		}
		data[currentCapacity-1]=null;
		return str;
	}
	
	/* Option 2: 
	if(!isEmpty()) 
	{	// IMPLEMENT THE REST
		str=data[0];
		for(int i=0;i<currentCapacity-1;i++)
		{
			data[i]=data[i+1];
		}
		data[currentCapacity-1]=null;
		return str;
	}
	return null;
	// which option is better? why? 
	 */    
	}
}