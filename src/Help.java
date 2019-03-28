import java.util.Scanner;

public class Help extends Window
{
    Help()
    {
        super("resources/graphic/Help.txt");
    }
    
    Help(MasterPrinter mp)
    {
        super("resources/graphic/Help.txt");
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
}
