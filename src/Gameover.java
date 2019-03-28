import java.util.Scanner;

public class Gameover extends Window
{
    Gameover()
    {
        super("resources/graphic/Gameover.txt");
    }
    
    Gameover(MasterPrinter mp)
    {
        super("resources/graphic/Gameover.txt");
        this.mp = mp;
    }
    
    @Override
    public void run()
    {
        InformationBar.setInformation("Name: " + Game.playerName + "  Score: " + Game.score, "good");
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
                    case 0://restart
                        //back to welcome to start new game
                        break in;
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
                    case 2://return to welcome
                        System.exit(0);
                    default://wrong input
                        InformationBar.setInformation("Wrong input.", "bad");
                        continue in;
                }
                break;
            }
            catch (Exception ex)
            {
                InformationBar.setInformation("Wrong input.", "bad");
            }
            
        }
        
        mp.remove(index);
    }
}
