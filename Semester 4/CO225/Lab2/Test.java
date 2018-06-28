class Test { 

    public static void main(String [] args) { 

	MySet<String> words = new MySet<String>(3); 
	
	for(int i=0; i < args.length; i++) 
	    words.add(args[i]); 

	String st; 
	while((st = words.remove()) != null)
	    System.out.println(st); 
    }
}