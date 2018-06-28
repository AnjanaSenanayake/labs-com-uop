import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class PanCakeSort 
{
	// Sorting algorithm
	static int panCakeSort(int[] panCakes)
	{
		int flipCount=0;
		int maxIndex;
		for(int stackheight=panCakes.length,i=1;stackheight>0;stackheight--)
		{
			maxIndex = findMaxIndex(panCakes,stackheight);
			if(maxIndex+1!=stackheight)
			{
				if(maxIndex!=0)
				{
					flip(panCakes,maxIndex+1);
					flipCount++;
					System.out.println(+(i++)+")Flip Top "+(maxIndex+1)+" Pancakes");
					printPanCakesOrder(panCakes);
					System.out.println();
				}				
				
				flip(panCakes,stackheight);
				flipCount++;
				System.out.println(+(i++)+")Flip Top All "+stackheight);
				printPanCakesOrder(panCakes);
				System.out.println();
			}
		}
		return flipCount;
	}
	
	// Flip pancakes at a given point i
	static void flip(int[] panCakes,int i)
	{
		Queue<Integer> queue = new ArrayDeque<Integer>();
		int j;
		for(j=0;j<i;j++)
		{
			queue.add(panCakes[j]);
		}
		j--;
		for(;j>=0;)
		{
			panCakes[j--] = queue.remove();
		}
	}
	
	// Finds the largest pancake of the considering stack.
	static int findMaxIndex(int[] panCakes,int bound)
	{
		int max=-1;
		int index=0;
		for(int i=0;i<bound;i++)
		{
			if(panCakes[i]>max)
			{
				max = panCakes[i];
				index = i;
			}
		}
		return index;
	}
	
	static void generatePanCakes(int[] panCakes)
	{
		 List<Integer> list = new ArrayList<>();
		 for (int i=1;i<=panCakes.length;i++) 
		 {
		    list.add(i);
		 }
		 
		 Collections.shuffle(list);

		 for (int i = 0; i < list.size(); i++) 
		 {
		    panCakes[i] = list.get(i);
		 }    
	}
	
	static void printPanCakesOrder(int[] panCakes)
	{
		for(int i : panCakes)
		{
			System.out.print(i+", ");
		}
		System.out.println();
	}
	
	public static void main(String[]args)
	{
		// CLI input
		int numofCakes = Integer.valueOf(args[0]);
		int[] panCakes = new int[numofCakes];
		generatePanCakes(panCakes);
		
		// Worst Cases For N= 4, 5, 6, 7, 8, 9, 11
		//int[] panCakes = {3,2,4,1};
		//int[] panCakes = {1,5,3,4,2};
		//int[] panCakes = {3,6,2,4,5,1};
		//int[] panCakes = {4,2,7,1,6,3,5};		
		//int[] panCakes = {1,8,5,2,3,6,7,4};		
		//int[] panCakes = {2,8,7,1,5,3,9,6,4};
		//int[] panCakes = {6,2,10,5,9,4,8,1,11,3,7};
		
		System.out.println("Unsorted PanCake Stack:");
		printPanCakesOrder(panCakes);
		
		System.out.println();
		int flips = panCakeSort(panCakes);
		
		System.out.println("Number of Flips:"+flips);
	}
}
