import java.util.Scanner;

public class Load extends Window
{
    Load()
    {
        super("resources/graphic/Load.txt");
    }
    
    Load(MasterPrinter mp)
    {
        super("resources/graphic/Load.txt");
        this.mp = mp;
    }
    
    @Override
    public void run()
    {
        InformationBar.setInformation("......", "good");
        index = mp.add(this);
        
        in:
        while (true)
        {
            mp.printToConsole();
            try
            {
                Scanner input = new Scanner(System.in);
                switch (input.nextInt())
                {
                    case 0://return
                        break in;
                    case 1://load from save01
                        load(1);
                        break;
                    case 2://load from save02
                        load(2);
                        break;
                    case 3://load from save03
                        load(3);
                        break;
                    case 4://load from save04
                        load(4);
                        break;
                    case 5://load from save05
                        load(5);
                        break;
                    case 6://load from save06
                        load(6);
                        break;
                    case 7://load from save07
                        load(7);
                        break;
                    case 8://load from save08
                        load(8);
                        break;
                    default://wrong input
                        InformationBar.setInformation("Wrong input.", "bad");
                }
            }
            catch (Exception ex)
            {
                InformationBar.setInformation("Wrong input.", "bad");
            }
            
        }
        mp.remove(index);
    }
    
    private static void load(int index)
    {
    
    }
}
