import java.util.Random;

class CompareSorting 
{
	static void bubble_sort(int[] data) 
	{
		int length = data.length;
        for (int i = 0; i < length-1; i++)
        {
            for (int j = 0; j < length-1; j++)
            {	
                if (data[j] > data[j+1])
                {                  
                    int temp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = temp;
                }
            }
        }    
	}

	static void selection_sort(int[] data) 
	{
		int len = data.length;
		
		for(int i=0;i<len;i++)
		{
			for(int j=0;j<len;j++)
			{
				if(data[i]<data[j])
				{
					int temp = data[i];
					data[i] = data[j];
					data[j] = temp;
				}
			}
		}
	}

	static void insertion_sort(int[] data) 
	{
		int len = data.length;		
		int j=0;
		int node=0;
		for(int i=0;i<len;i++)
		{	
			node = data[i];
            j = i-1;
            while (j>=0 && data[j] > node)
            {
                data[j+1] = data[j];
                j-- ;
            }
            data[j+1] = node;
		}
	}

	// Helper functions

	static int[] generate_data(int sizeOfData) 
	{
		/*
		 * create an array of sizeOfData and populate with random integers betweem
		 * 0-1000
		 */
		int[] tmp = new int[sizeOfData];
		Random rand = new Random();
		for (int i = 0; i < sizeOfData; i++)
			tmp[i] = rand.nextInt(2 * sizeOfData);
		return tmp;
	}

	static int[] duplicate_array(int[] data) {
		/*
		 * create a duplicate array of the given useful when sending the same array to
		 * different algorithms.
		 */
		int[] tmp = new int[data.length];
		for (int i = 0; i < data.length; i++)
			tmp[i] = data[i];

		return tmp;
	}

	static void show(int[] data) 
	{
		System.out.printf("\n");
		for (int i = 0; i < data.length; i++)
			System.out.printf("%d %c", data[i], i == (data.length - 1) ? ' ' : ',');
		System.out.printf("\n");
	}

	static void postCondition(int[] data) 
	{
		/*
		 * if sorted, for any i data[i] > data[i-1] Need to run this with java -ea
		 * CompareSorting
		 */
		int i;
		for (i = 1; i < data.length; i++)
			if (data[i] < data[i - 1])
				break;

		assert i == data.length : "Sorting algorithm used is broken";
	}

	public static void main(String[] ar) 
	{
		int[] t = generate_data(30);
		//int[] s = {34,23,35,24,7,9,6,2,21,45,54,26,67,89,98};
		show(t);
		bubble_sort(t);
		//selection_sort(t);
		//insertion_sort(t);
		show(t);
		postCondition(t);
	}
}
