public class Code 
{
	public static void main(String[] args) 
	{
		MySet<Integer> words = new MySet<Integer>(3);
		
		words.add(2);
		words.add(3);
		words.add(4);
		words.add(3);
		words.add(5);
		words.add(4);
		words.add(6);
		
		Integer st; 
		while((st = words.remove())!= null
				)
		    System.out.println(st); 
	}
}
