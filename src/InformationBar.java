/**
This class presents a bottom InformationBar with the most recent feedback.
*/
public class InformationBar
{
    private static String information = "......";
    private static String face = "(๑*◡*๑)";
    
    InformationBar()
    {
    }
    
    public static void setInformation(String information, String goodOrBad)
    {
        InformationBar.information = information;
        if (goodOrBad.equals("good"))
        {
            switch ((int)(Math.random() * 4))
            {
                case 1:face = "ヾ(✿ﾟ▽ﾟ)ノ";break;
                case 2:face = "(ﾉ´▽｀)ﾉ♪";break;
                case 3:face = "(oﾟ▽ﾟ)o  ";break;
                default:face = "(๑*◡*๑)";
            }
        }
        else if (goodOrBad.equals("bad"))
        {
            switch ((int)(Math.random() * 4))
            {
                case 1:face = "(；´д｀)ゞ";break;
                case 2:face = "(´-ι_-｀|||)";break;
                case 3:face = "ﾍ(;´Д｀ﾍ)";break;
                default:face = "(ಥ_ಥ)";
            }
        }
        else//custom face
        {
            face = goodOrBad;
        }
    }
    
    public static void print()
    {
        System.out.println();
        System.out.println("GameMaster:  " + face + "  ");
        System.out.println("█▄ ☛  \"" + information + "\"  ▄█");
    }
}
