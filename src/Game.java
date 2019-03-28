import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Game extends Window
{
    public static boolean showRank;
    public static boolean gameEnd;
    public static String playerName;
    public static long score;
    public static int itemN;
    public static int itemM;
    private Tetromino ttm;
    
    Game()
    {
        super("resources/graphic/Load.txt");
        score = 0;
        itemN = 5;
        itemM = 10;
        ttm = new Tetromino(mp);
    }
    
    Game(MasterPrinter mp)
    {
        super("resources/graphic/Game.txt");
        this.mp = mp;
        ttm = new Tetromino(mp);
        score = 0;
        itemN = 5;
        itemM = 10;
    }
    
    public Tetromino getTtm()
    {
        return ttm;
    }
    
    @Override
    public void run()
    {
        gameEnd = false;
        Thread ttmThread = new Thread(ttm);
        ttmThread.start();
        
        index = mp.add(this);
        
        while (true)
        {
            if (gameEnd)
            {
                ttm.setStatus("end");
                try
                {
                    Thread.sleep(Config.frequency + 200);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                break;
            }
            
            mp.printToConsole();
            try
            {
                Scanner input = new Scanner(System.in);
                String inputValue = input.next();
                if (gameEnd)
                {
                    //gameover, receive tasks from Tetromino.descend()
    
                    inputValue += "    ";
                    playerName = inputValue.substring(0,4);
    
                    /*
                    //set rank
                    int playerNumber = 0;
                    String[] playerNames;
                    long[] scores;
    
                    File f = new File("resources/save/rank.dat");
                    if (!f.exists())
                    {
                        //create new rank
                        f.createNewFile();
                        RandomAccessFile raf = new RandomAccessFile(f,"rw");
        
                        playerNumber = 1;
        
                        raf.writeInt(playerNumber);
                        raf.writeUTF(playerName);
                        raf.writeLong(score);
                    }
                    else
                    {
                        //read rank
                        RandomAccessFile raf = new RandomAccessFile(f,"rw");
                        raf.setLength(raf.length() + 4);
                        raf.seek(0);
        
                        playerNumber = raf.readInt();
                        playerNames = new String[playerNumber + 1];
                        scores = new long[playerNumber + 1];
        
                        for (int i = 0; i < playerNumber; i++)
                        {
                            playerNames[i] = raf.readUTF();
                            scores[i] = raf.readLong();
                        }
        
                        raf.setLength(raf.length() - 4);
        
                        //try to fill in this playerName and score
                        for (int i = 0; i < playerNumber; i++)
                        {
                            if (score >= scores[i])
                            {
                                //insert
                                for (int j = playerNumber; j > i; j--)
                                {
                                    playerNames[j] = playerNames[j - 1];
                                    scores[j] = scores[j - 1];
                                }
                                scores[i] = score;
                                playerNames[i] = playerName;
                                break;
                            }
                            else if (i == playerNumber - 1)
                            {
                                scores[i + 1] = score;
                                playerNames[i + 1] = playerName;
                            }
                        }
        
                        //write new rank to file
                        raf.setLength(0);
                        raf.seek(0);
                        System.out.println(raf.length());
                        for (int i = 0; i < playerNumber + 1; i++)
                        {
                            raf.writeInt(playerNumber + 1);
                            raf.writeUTF(playerNames[i]);
                            raf.writeLong(scores[i]);
                        }
                        System.out.println(raf.length());
                    }
                    */
    
                    //call Gameover
    
                    Thread gameover = new Thread(new Gameover(mp));
                    gameover.start();
                    try
                    {
                        gameover.join();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
    
                    ttm.setStatus("end");
                    try
                    {
                        Thread.sleep(Config.frequency + 200);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                }
                else if (inputValue.equals("10") || inputValue.equals("0"))
                {
                    ttm.move(10);
                }
                else if (inputValue.charAt(0) >= '1' && inputValue.charAt(0) <= '9')
                {
                    ttm.move(inputValue.charAt(0) - '0');
                }
                else if (inputValue.charAt(0) == 'w')
                {
                    ttm.rotate();
                }
                else if (inputValue.charAt(0) == 'n')
                {
                    if (itemN > 0)
                    {
                        itemN--;
                        ttm.getP().removeLine(22);
                    }
                    else
                    {
                        InformationBar.setInformation("You have no item N now", "bad");
                    }
                }
                else if (inputValue.charAt(0) == 'm')
                {
                    if (itemM > 0)
                    {
                        itemM--;
                        ttm.randomAnotherType();
                    }
                    else
                    {
                        InformationBar.setInformation("You have no item M now", "bad");
                    }
                }
                else if (inputValue.charAt(0) == 'r')
                {
                    //restart
                    gameEnd =true;
                }
                else if (inputValue.charAt(0) == 'q')
                {
                    //quit
                    System.exit(0);
                }
                else if (inputValue.charAt(0) == 'i')
                {
                    //rank
                    showRank = !showRank;
                }
                else if (inputValue.charAt(0) == 'x')
                {
                    //pause
                    ttm.setStatus("pause");
                    Thread pause = new Thread(new Pause(mp));
                    pause.start();
                    try
                    {
                        pause.join();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    ttm.setStatus("running");
                    
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
        
        }
        mp.remove(index);
        
    }
    
    @Override
    public boolean canBeHidden()
    {
        return false;
    }
}
