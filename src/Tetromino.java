public class Tetromino extends Window
{
    private String status = "running";
    private char type;
    private char blockStyle;
    private int state;
    private int line;
    private int column;
    private char nextType;
    private Pile p;
    
    Tetromino()
    {
        this(new MasterPrinter(),'I','■',0,0,1);
        type = randomType();
        column = randomColumn();
    
        nextType = randomType();
    }
    
    Tetromino(MasterPrinter mp)//new ttm
    {
        this(mp,'I','■',0,0,1);
        type = randomType();
        column = randomColumn();
        
        nextType = randomType();
    }
    
    Tetromino(MasterPrinter mp,char type, char blockStyle, int state, int line, int column)
    {
        this.mp = mp;
        this.type = type;
        this.blockStyle = blockStyle;
        this.state = state;
        this.line = line;
        this.column = column;
        this.p = new Pile();
    }
    
    Tetromino(MasterPrinter mp,char type, char blockStyle, int state, int line, int column, char nextType)//load
    {
        this(mp,type,blockStyle,state,line,column);
        this.nextType = nextType;
    }
    
    public synchronized void setStatus(String status)
    {
        this.status = status;
    }
    
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(200);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    
        index = mp.add(this);
        mp.add(this.p);
        
        while (true)
        {
            InformationBar.setInformation("......", "good");
            
            if (status.equals("end"))
            {
                break;
            }
            else if (status.equals("pause"))
            {
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            else if (Game.score >= 999999999)
            {
                Game.score = 999999999;
            }
            else
            {
                descend();
                
                mp.printToConsole();
                try
                {
                    Thread.sleep(Config.frequency);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                
                p.removeLine(p.detectLine());
            }
        }
        mp.remove(index + 1);
        mp.remove(index);
    }
    
    public char randomType()
    {
        int random = (int)(Math.random() * 7);
        switch (random)
        {
            case 0: return 'O';
            case 1: return 'I';
            case 2: return 'S';
            case 3: return 'Z';
            case 4: return 'J';
            case 5: return 'L';
            default: return 'T';
        }
    }
    
    public void randomAnotherType()
    {
        char newType = 'L';
        while (newType == type)
        {
            newType = randomType();
        }
        
        type = newType;
        
        while (detectCollision(this))
        {
            line--;
        }
    }
    
    private int randomColumn()
    {
        return (int)(Math.random() * 7) + 1;
    }
    
    public synchronized char getNextType()
    {
        return nextType;
    }
    
    public synchronized int getLine()
    {
        return line;
    }
    
    public synchronized int getColumn()
    {
        return column;
    }
    
    public synchronized char getType()
    {
        return type;
    }
    
    public synchronized Pile getP()
    {
        return p;
    }

    public synchronized void descend()
    {
        Tetromino ttm = new Tetromino(mp,type, blockStyle,state,line + 1,column);
        if (!detectCollision(ttm))
        {
            this.line++;
        }
        else
        {
            //add this tetromino to pile and spawns the next one on the board
            p.add(this);
            if (detectEnd())
            {
                //gameover
                
                status = "pause";
                
                //count score
                Game.score -= p.countHole();
                
                InformationBar.setInformation("You have finished this turn with a score of " + Game.score + ".Please Leave your name here!", "\\(๑´ㅂ`๑)/");
                mp.printToConsole();
                
                //transfer the rest of tasks to Game
                Game.gameEnd = true;
            }
            else
            {
                type = nextType;
                state = 0;
                line = 0;
                column= randomColumn();
                nextType = randomType();
            }
        }
    }
    
    private boolean detectEnd()
    {
        for (int i = 0; i <= 3; i++)
        {
            for (int j = 1; j <= 10; j++)
            {
                if (p.getHitbox()[i][j])
                {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void move(int column)
    {
        Tetromino ttm = new Tetromino(mp,type, blockStyle,state,line,column);
        if (!detectCollision(ttm))
        {
            //ensure no obstacle on the way
            
            int hitboxTop = 0;
            search:
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    if (getHitbox()[i][j])
                    {
                        hitboxTop = i;
                        break search;
                    }
                }
            }
            
            boolean hasObstacle = false;
            for (int i = line + hitboxTop; i < line + 4; i++)
            {
                if (column > this.column)
                {
                    for (int j = this.column; j < column; j++)
                    {
                        hasObstacle = hasObstacle || p.getHitbox()[i][j];
                    }
                }
                else
                {
                    for (int j = this.column; j > column; j--)
                    {
                        hasObstacle = hasObstacle || p.getHitbox()[i][j];
                    }
                }
            }
            
            if (!hasObstacle)
            {
                this.column = column;
            }
            else
            {
                InformationBar.setInformation("Illegal movement.", "bad");
            }
        }
        else
        {
            InformationBar.setInformation("Illegal movement.", "bad");
        }
    }
    
    public synchronized void rotate()
    {
        if (type == 'I' || type == 'S' || type == 'Z')
        {
            Tetromino ttm = new Tetromino(mp,type, blockStyle,1 - state,line,column);
            if (!detectCollision(ttm))
            {
                state = 1 - state;
            }
            else
            {
                InformationBar.setInformation("Illegal rotation.", "bad");
            }
        }
        else if (type == 'J' || type == 'L' || type == 'T')
        {
            Tetromino ttm = new Tetromino(mp,type, blockStyle,(state + 1) % 4,line,column);
            if (!detectCollision(ttm))
            {
                state = (state + 1) % 4;
            }
            else
            {
                InformationBar.setInformation("Illegal rotation.", "bad");
            }
        }
    }
    
    public boolean[][] getHitbox()
    {
        return getHitbox(type,state);
    }
    
    public boolean[][] getHitbox(char type, int state)
    {
        boolean[][] hitbox;
        if(type == 'O')
        {
            //if (state == 0)
            {
                hitbox = new boolean[][]
                        {
                                {false,false,false,false},
                                {false,false,false,false},
                                {true,true,false,false},
                                {true,true,false,false}
                        };
            }
        }
        else if (type == 'I')
        {
            if (state == 0) {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, false, false, false},
                                {false, false, false, false},
                                {true, true, true, true}
                        };
            }
            else //if (state == 1)
            {
                hitbox = new boolean[][]
                        {
                                {true, false, false, false},
                                {true, false, false, false},
                                {true, false, false, false},
                                {true, false, false, false}
                        };
            }
        }
        else if (type == 'S')
        {
            if (state == 0)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, false, false, false},
                                {false, true, true, false},
                                {true, true, false, false}
                        };
            }
            else //if (state == 1)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {true, false, false, false},
                                {true, true, false, false},
                                {false, true, false, false}
                        };
            }
        }
        else if (type == 'Z')
        {
            if (state == 0)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, false, false, false},
                                {true, true, false, false},
                                {false, true, true, false}
                        };
            }
            else //if (state == 1)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, true, false, false},
                                {true, true, false, false},
                                {true, false, false, false}
                        };
            }
        }
        else if (type == 'J')
        {
            if (state == 0)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, true, false, false},
                                {false, true, false, false},
                                {true, true, false, false}
                        };
            }
            else if (state == 1)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, false, false, false},
                                {true, false, false, false},
                                {true, true, true, false}
                        };
            }
            else if (state == 2)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {true, true, false, false},
                                {true, false, false, false},
                                {true, false, false, false}
                        };
            }
            else //if (state == 3)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, false, false, false},
                                {true, true, true, false},
                                {false, false, true, false}
                        };
            }
        }
        else if (type == 'L')
        {
            if (state == 0)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {true, false, false, false},
                                {true, false, false, false},
                                {true, true, false, false}
                        };
            }
            else if (state == 1)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, false, false, false},
                                {true, true, true, false},
                                {true, false, false, false}
                        };
            }
            else if (state == 2)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {true, true, false, false},
                                {false, true, false, false},
                                {false, true, false, false}
                        };
            }
            else //if (state == 3)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, false, false, false},
                                {false, false, true, false},
                                {true, true, true, false}
                        };
            }
        }

        else//type == 'T'
        {
            if (state == 0)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, false, false, false},
                                {false, true, false, false},
                                {true, true, true, false}
                        };
            }
            else if (state == 1)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {true, false, false, false},
                                {true, true, false, false},
                                {true, false, false, false}
                        };
            }
            else if (state == 2)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, false, false, false},
                                {true, true, true, false},
                                {false, true, false, false}
                        };
            }
            else //if (state == 3)
            {
                hitbox = new boolean[][]
                        {
                                {false, false, false, false},
                                {false, true, false, false},
                                {true, true, false, false},
                                {false, true, false, false}
                        };
            }
        }
        return hitbox;
    }
    
    private boolean detectCollision(Tetromino ttm)
    {
        boolean hasCollision = false;
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                hasCollision = hasCollision || (p.getHitbox()[ttm.getLine() + i][ttm.getColumn() + j] && ttm.getHitbox()[i][j]);
            }
        }
        return hasCollision;
    }
    
    @Override
    public synchronized char[][] print()
    {
        char[][] print = new char[24][24];
        for (int i = 0; i < print.length; i++)
        {
            for (int j = 0; j < print[i].length; j++)
            {
                print[i][j] = ' ';
            }
        }
        
        for (int i = line; i < line + 4; i++)
        {
            for (int j = column; j < column + 4; j++)
            {
                if (getHitbox()[i - (line)][j - (column)])
                {
                    print[i][j] = this.blockStyle;
                }
            }
        }
        
        //print Now Tetromino
        for (int i = 3; i < 7; i++)
        {
            for (int j = 12; j < 16; j++)
            {
                if (getHitbox()[i - 3][j - 12])
                {
                    print[i][j] = this.blockStyle;
                }
            }
        }
    
        //print Next Tetromino
        for (int i = 3; i < 7; i++)
        {
            for (int j = 17; j < 21; j++)
            {
                if (getHitbox(nextType,0)[i - 3][j - 17])
                {
                    print[i][j] = blockStyle;
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
