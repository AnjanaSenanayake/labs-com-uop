class Bucket
{
    public int tokens, maxsize;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";

    int congestionFreq=0;
    int nonCongestion=0;
    int congestionRate = 2;
    int defaultmax;
    boolean congestion = false;
 
    Bucket(int max)
    {
        tokens = 20480;
        maxsize = max;
        defaultmax=max;
    }
 
    synchronized void addToken(int n)
    {
        if(tokens >= maxsize) return;
        tokens += n;
        System.out.println("Added a token. Total:" + tokens);
    }
 
    synchronized void sendPacket(int n)
    {
    	System.out.println("Packet of size "+n+" arrived");
        if(congestionFreq>congestionRate)
        {
        	nonCongestion=0;
        	maxsize=(int) (maxsize*0.8);
        	congestion = true;
        	congestionRate = (int) (congestionRate*(1.5));
        	System.out.println(ANSI_YELLOW+"High congestion frequency, Bucket capacity reduced to 80% "+maxsize+""+ANSI_RESET);
        }
        if(n > tokens)
        {
        	congestionFreq++;
            System.out.println(ANSI_RED+"Packet is non comformant, discarded"+ANSI_RESET);
        }
        else if(n>(0.90*tokens))
        {
        	nonCongestion=0;
        	congestionFreq++;
        	tokens -= n;
            System.out.println("Forwarding packet, "+tokens+" tokens left");
        }
        else
        {
        	if(congestion)
        	{
        		congestionFreq--;
        		if(congestionFreq==0)
            	{
        			congestion = false;
            		maxsize=defaultmax;
            		System.out.println(ANSI_GREEN+"Bucket capacity is reset to default "+maxsize+ANSI_RESET);            	
            	}
        	}
        	else if(nonCongestion>6*congestionFreq)
        	{
        		congestionFreq=0;
        		maxsize=defaultmax;    
        	}
        	nonCongestion++;
            tokens -= n;
            System.out.println("Forwarding packet, "+tokens+" tokens left");
        }
    }
}
 
class AddTokenThread extends Thread
{
    Bucket b;
    AddTokenThread(Bucket b)
    {
        this.b = b;
    }
    public void run()
    {
        while(true)
        {
            b.addToken(20480);
            try
            {
                Thread.sleep(300);
            }
            catch(Exception e){}
        }
    }
}
 
class AddPacketThread extends Thread
{
    Bucket b;
    AddPacketThread(Bucket b)
    {
        this.b = b;
    }
 
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(500 + (int) (Math.random()*2000));
            }
            catch(Exception e){}
            b.sendPacket(2048 + (int) (Math.random()*50000));
        }
    }
}
 
public class TokenBucket
{
    public static void main(String args[])
    {
        Bucket b = new Bucket(40960);
        Thread tokens = new AddTokenThread(b);
        Thread packets = new AddPacketThread(b);
        tokens.start();
        packets.start();
    }
}