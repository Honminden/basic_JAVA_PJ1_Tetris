import java.util.Scanner;

public class Welcome extends Window
{
    Welcome()
    {
        super("resources/graphic/Welcome.txt");
    }
    
    Welcome(MasterPrinter mp)
    {
        super("resources/graphic/Welcome.txt");
        this.mp = mp;
    }
    
    @Override
    public void run()
    {
        InformationBar.setInformation("......", "good");
        index = mp.add(this);
        
        while (true)
        {
            mp.printToConsole();
            try
            {
                Scanner input = new Scanner(System.in);
                switch (input.nextInt())
                {
                    case 0://new game
                        while (true)//constantly launch new game when last one ends
                        {
                            Thread game = new Thread(new Game(mp));
                            game.start();
                            try
                            {
                                game.join();
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                                break;
                            }
                        }
                        break;
                    case 1://load game
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
                    case 2://config
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
                    case 3://help
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
                    case 4://exit
                        System.exit(0);
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
    }
}
