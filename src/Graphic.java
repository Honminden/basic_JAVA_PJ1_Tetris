/**
 This class reads graphs from .txt files in /graphic.
 */
import java.io.*;

public class Graphic implements Printable
{
    MasterPrinter mp;
    private String graph;
    private String path;
    
    Graphic()
    {
    
    }
    
    Graphic(String path)
    {
        this.path = path;
        readGraph();
    }
    
    private void readGraph()
    {
        File f = new File(path);
        try
        {
            InputStream input = new FileInputStream(f);
            byte[] b = new byte[(int)f.length()];
            input.read(b);
            graph = new String(b,"GBK");
        }
        catch (IOException ex)
        {
            System.out.println(path + " NOT FOUND");
        }
    }
    
    @Override
    public char[][] print()
    {
        String[] token = graph.split("\r\n");
        
        char[][] print = new char[token.length][];
        for (int i = 0; i < token.length; i++)
        {
            print[i] = new char[token[i].length()];
        }
        
        for (int i = 0; i < token.length; i++)
        {
            for (int j = 0; j < token[i].length(); j++)
            {
                print[i][j] = token[i].charAt(j);
            }
        }
        return print;
    }
    
    @Override
    public boolean canBeHidden()
    {
        return true;
    }
}
