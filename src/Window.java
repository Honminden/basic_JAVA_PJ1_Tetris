public abstract class Window extends Graphic implements Runnable
{
    
    int index = 0;
    
    protected Window()
    {
    }
    
    protected Window(String path)
    {
        super(path);
    }
    
    @Override
    public abstract void run();
}
