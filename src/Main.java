public class Main
{
    public static void main(String[] args)
    {
        MasterPrinter mp = new MasterPrinter();
        Thread welcome = new Thread(new Welcome(mp));
        welcome.start();
    }
}
