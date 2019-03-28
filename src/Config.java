import java.util.Scanner;

public class Config extends Window
{
    public static int frequency = 1000;
    public static boolean enableAutosave = true;
    
    Config()
    {
        super("resources/graphic/Config.txt");
    }
    
    Config(MasterPrinter mp)
    {
        super("resources/graphic/Config.txt");
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
                    case 1://change frequency
                        InformationBar.setInformation("Enter a num between 300 ~ 2000 as the flashing frequency.", "good");
                        mp.printToConsole();
                        try
                        {
                            Scanner input2 = new Scanner(System.in);
                            int fre = input2.nextInt();
                            if (fre >= 300 && fre <= 2000)
                            {
                                frequency = fre;
                            }
                            else
                            {
                                InformationBar.setInformation("Wrong input.", "bad");
                            }
                        }
                        catch (Exception ex)
                        {
                            InformationBar.setInformation("Wrong input.", "bad");
                        }
                        break;
                    case 2://enable autosave
                        InformationBar.setInformation("Enter 0 to disable autosave and 1 to enable.", "good");
                        mp.printToConsole();
                        try
                        {
                            Scanner input3 = new Scanner(System.in);
                            int enAuto = input3.nextInt();
                            if (enAuto == 0)
                            {
                                enableAutosave = false;
                            }
                            else if (enAuto == 1)
                            {
                                enableAutosave = true;
                            }
                            else
                            {
                                InformationBar.setInformation("Wrong input.", "bad");
                            }
                        }
                        catch (Exception ex)
                        {
                            InformationBar.setInformation("Wrong input.", "bad");
                        }
                        break;
                    default://wrong input
                        InformationBar.setInformation("Wrong input.", "bad");
                }
            }
            catch (Exception ex)
            {
                InformationBar.setInformation("Wrong input.", "bad");
            }
            InformationBar.setInformation("......", "good");
        }
        
        mp.remove(index);
    }
}
