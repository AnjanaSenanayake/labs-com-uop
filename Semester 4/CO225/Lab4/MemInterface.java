import java.io.IOException;// IOExceptins

public interface MemInterface 
{
    int lw(String address) throws IOException; 
    void sw(String address, int val) throws IOException; 
    int cacheHits();
    int cacheMisses();
};


