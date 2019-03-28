import java.io.File;
import java.util.Scanner;

public class Save extends Window
{
    Save()
    {
        super("resources/graphic/Save.txt");
    }
    
    Save(MasterPrinter mp)
    {
        super("resources/graphic/Save.txt");
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
                    case 0://return;
                    case 1://save in save01
                        save(1);
                        break in;
                    case 2://save in save02
                        save(2);
                        break;
                    case 3://save in save03
                        save(3);
                        break;
                    case 4://save in save04
                        save(4);
                        break;
                    case 5://save in save05
                        save(5);
                        break;
                    case 6://save in save06
                        save(6);
                        break;
                    case 7://save in save07
                        save(7);
                        break;
                    case 8://save in save08
                        save(8);
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
    
    private void save(int index)
    {
        File f = new File("resources/save/save0" + index);
        //get current game
        Game game;
    }
}
