import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Pile implements Printable
{
    private boolean[][] hitbox;
    private char blockStyle = '■';
    
    Pile()
    {
        this(new boolean[][]{{}});
        hitbox();
    }
    
    Pile(boolean[][] hitbox)
    {
        this.hitbox = hitbox;
    }
    
    private void hitbox()
    {
        hitbox = new boolean[][]{
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},//show from Line3
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},
                {true,false,false,false,false,false,false,false,false,false,false,true,true,true},//show till Line22
                {true,true,true,true,true,true,true,true,true,true,true,true,true,true},
        };
    }
    
    public boolean[][] getHitbox()
    {
        return hitbox;
    }
    
    public void add(Tetromino ttm)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                this.hitbox[ttm.getLine() + i][ttm.getColumn() + j] = this.hitbox[ttm.getLine() + i][ttm.getColumn() + j] || ttm.getHitbox()[i][j];
            }
        }
    }
    
    public ArrayList<Integer> detectLine()
    {
        ArrayList<Integer> lineindex = new ArrayList<>(20);
        for (int i = 3; i <= 22; i++)
        {
            boolean isThisLine = true;
            for (int j = 1; j <= 10; j++)
            {
                isThisLine = isThisLine && hitbox[i][j];
            }
            if (isThisLine)
            {
                lineindex.add(i);
            }
        }
        return lineindex;
    }
    
    public void removeLine(ArrayList<Integer> lineIndexs)
    {
        while (!lineIndexs.isEmpty())
        {
            removeLine(lineIndexs.get(0));
            lineIndexs.remove(0);
            Game.score += 10;
        }
    }
    
    public void removeLine(int lineIndex)
    {
        for (int i = lineIndex; i >= 4; i--)
        {
            for (int j = 1; j <= 10; j++)
            {
                hitbox[i][j] = hitbox[i - 1][j];
            }
        }
        hitbox[3] = new boolean[] {true,false,false,false,false,false,false,false,false,false,false,true,true,true};
    }
    
    private boolean[][] isPartOfHole = new boolean[24][13];
    private boolean isAHole;
    
    public int countHole()
    {
        int countHole = 0;
        
        for (int i = 4; i <= 23; i++)
        {
            for (int j = 1; j <= 10; j++)
            {
                if ((!hitbox[i][j])&&(!isPartOfHole[i][j]))//may be a hole
                {
                    isPartOfHole[i][j] = true;
                    isAHole = true;
                    detectHole(i,j);
                    if (isAHole)
                    {
                        countHole++;
                    }
                }
            }
        }
        return countHole;
    }
    
    private void detectHole(int i, int j)
    {
        if ((i - 1 == 3) && (!hitbox[i - 1][j]))//this is not a hole
        {
            isAHole = false;
        }
        if ((!hitbox[i - 1][j])&&(!isPartOfHole[i - 1][j])&&(i - 1 != 3))//go up
        {
            isPartOfHole[i - 1][j] = true;
            detectHole(i - 1,j);
        }
        if ((!hitbox[i][j - 1])&&(!isPartOfHole[i][j - 1]))//go left
        {
            isPartOfHole[i][j - 1] = true;
            detectHole(i,j - 1);
        }
        if ((!hitbox[i + 1][j])&&(!isPartOfHole[i + 1][j]))//go down
        {
            isPartOfHole[i + 1][j] = true;
            detectHole(i + 1,j);
        }
        if ((!hitbox[i][j + 1])&&(!isPartOfHole[i][j + 1]))//go right
        {
            isPartOfHole[i][j + 1] = true;
            detectHole(i,j + 1);
        }
    }
    
    @Override
    public char[][] print()
    {
        char[][] print = new char[24][24];
        for (int i = 0; i < print.length; i++)
        {
            for (int j = 0; j < print[i].length; j++)
            {
                print[i][j] = ' ';
            }
        }
        
        //print score
        String scoreString = Game.score + "";
        for (int j = 10; j > 10 - scoreString.length(); j--)
        {
            print[1][j] = scoreString.charAt(scoreString.length() - 1 + j - 10);
        }
        
        //print items
        String itemNString = Game.itemN + "";
        for (int j = 20; j > 20 - itemNString.length(); j--)
        {
            print[9][j] = itemNString.charAt(itemNString.length() - 1 + j - 20);
        }
        
        String itemMString = Game.itemM + "";
        for (int j = 20; j > 20 - itemMString.length(); j--)
        {
            print[10][j] = itemMString.charAt(itemMString.length() - 1 + j - 20);
        }
        
        /*
        //print rank
        if (Game.showRank)
        {
            String[] rankStrings;
            int playerNumber;
            String[] playerNames;
            long[] scores;
    
            File f = new File("resources/save/rank.dat");
            if (!f.exists())
            {
                print[13][12] = 'N';
                print[13][13] = 'O';
                print[13][14] = ' ';
                print[13][15] = 'R';
                print[13][16] = 'E';
                print[13][17] = 'C';
                print[13][18] = 'O';
                print[13][19] = 'R';
                print[13][20] = 'D';
                
            }
            else
            {
                RandomAccessFile raf;
                try
                {
                    //read rank
                    raf = new RandomAccessFile(f,"rw");
                    raf.setLength(raf.length() + 4);
                    raf.seek(0);
    
                    playerNumber = raf.readInt();
                    playerNames = new String[playerNumber];
                    scores = new long[playerNumber];
    
                    for (int i = 0; i < playerNumber; i++)
                    {
                        playerNames[i] = raf.readUTF();
                        scores[i] = raf.readLong();
                        //System.out.println("pN" + i + " " + playerNames[i] +" "+ scores[i]);
                    }
                    
                    raf.setLength(raf.length() - 4);
    
                    //transfer rank into string
                    rankStrings= new String[playerNumber];
                    for (int i = 0; i < playerNumber; i++)
                    {
                        rankStrings[i] = playerNames[i] + " ";
                        if (scores[i] > 99999)
                        {
                            //show scientific number
                            rankStrings[i] += ((scores[i] + "").charAt(0) + "." + (scores[i] + "").charAt(1) + "E" + ((scores[i] + "").length() - 1));
                        }
                        else
                        {
                            rankStrings[i] += scores[i];
                            while (rankStrings[i].length() < 9)
                            {
                                rankStrings[i] = rankStrings[i].substring(0,4) + " " + rankStrings[i].substring(4);
                            }
                        }
                    }
                    
                    //print rankString
                    for (int i = 13; i <= 22 && i - 13 < playerNumber; i++)
                    {
                        for (int j = 12; j <= 20; j++)
                        {
                            //System.out.print(i +" "+ j + "\n");
                            print[i][j] = rankStrings[i - 13].charAt(j - 12);
                        }
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                
            }
            
        }
        */
        
        //print pile
        for (int i = 3; i <= 22; i++)
        {
            for (int j = 1; j <= 10; j++)
            {
                if (this.hitbox[i][j])
                {
                    print[i][j] = this.blockStyle;
                }
            }
        }
        return print;
    }
    
    @Override
    public boolean canBeHidden()
    {
        return false;
    }
}
