import java.util.Vector;

public class MasterPrinter
{
    Vector<Printable> layers = new Vector<>();
    private String consoleOutput = "";
    
    MasterPrinter()
    {
    
    }
    
    public int add(Printable layer)
    {
        layers.add(layer);
        //System.out.println("add" + (layers.size() - 1));
        return layers.size() - 1;
    }
    
    public void remove(int index)
    {
        //System.out.println("remove" + index);
        layers.remove(index);
    }
    
    public synchronized void printToConsole()
    {
        print();
        for (int i = 0; i < 30; i++)
        {
            System.out.println();
        }
        
        System.out.println(consoleOutput);
        InformationBar.print();
    }
    
    private void print()
    {
        consoleOutput = "";
        for (int i = 0; i < layers.size() - 1; i++)
        {
            if (!layers.get(i).canBeHidden())
            {
                copy(layers.get(i));
            }
        }
        copy(layers.get(layers.size() - 1));
    }
    
    private void copy(Printable layer)
    {
        if (consoleOutput.length() == 0)
        {
            for (int i = 0;i < layer.print().length; i++)
            {
                for (int j = 0;j < layer.print()[i].length; j++)
                {
                    consoleOutput += layer.print()[i][j];
                }
                consoleOutput += "\r\n";
            }
        }
        else
        {
            //create a blank board
            String[] token = consoleOutput.split("\r\n");
            int maxLine = (token.length > layer.print().length) ? token.length : layer.print().length;
            int[] maxColumn = new int[maxLine];
            for (int i = 0;i < maxLine; i++)
            {
                if (i >= token.length)
                {
                    maxColumn[i] = layer.print()[i].length;
                    continue;
                }
                else if (i >= layer.print().length)
                {
                    maxColumn[i] = token[i].length();
                    continue;
                }
                maxColumn[i] = (token[i].length() > layer.print()[i].length) ? token[i].length() : layer.print()[i].length;
            }
          
            char[][] board = new char[maxLine][];
            for (int i = 0;i < maxLine; i++)
            {
                board[i] = new char[maxColumn[i]];
            }
    
            for (int i = 0;i < board.length; i++)
            {
                for (int j = 0;j < board[i].length; j++)
                {
                    board[i][j] = ' ';
                }
            }
            
            //copy consoleOutput
            for (int i = 0;i < token.length; i++)
            {
                for (int j = 0;j < token[i].length(); j++)
                {
                    board[i][j] = token[i].charAt(j);
                }
            }
            
            char[][] ts = layer.print();
            
            //copy layer
            for (int i = 0;i < layer.print().length; i++)
            {
                for (int j = 0;j < layer.print()[i].length; j++)
                {
                    if (layer.print()[i][j] == ' ')
                    {
                        //the space cannot cover char under it
                    }
                    else
                    {
                        board[i][j] = layer.print()[i][j];
                    }
                }
            }
            
            //save change to consoleOutput
            consoleOutput = "";
            for (int i = 0;i < board.length; i++)
            {
                for (int j = 0;j < board[i].length; j++)
                {
                    consoleOutput += board[i][j];
                }
                consoleOutput += "\r\n";
            }
        }
    }
    
}
