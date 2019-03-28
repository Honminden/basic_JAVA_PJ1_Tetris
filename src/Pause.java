import java.util.Scanner;

public class Pause extends Window
{
    Pause()
    {
        super("resources/graphic/Pause.txt");
    }
    
    Pause(MasterPrinter mp)
    {
        super("resources/graphic/Pause.txt");
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
                    case 0://resume
                        break in;
                    case 1://save game
                        Thread save = new Thread(new Save(mp));
                        save.start();
                        try
                        {
                            save.join();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 2://load game
                        Thread load = new Thread(new Load(mp));
                        load.start();
                        try
                        {
                            load.join();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 3://config
                        Thread config = new Thread(new Config(mp));
                        config.start();
                        try
                        {
                            config.join();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 4://help
                        Thread help = new Thread(new Help(mp));
                        help.start();
                        try
                        {
                            help.join();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 5://restart
                        Game.gameEnd = true;
                        break in;
                    case 6://return to welcome
                        Thread welcome = new Thread(new Welcome(mp));
                        welcome.start();
                        try
                        {
                            welcome.join();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
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
            
        }
        mp.remove(index);
    }
}
