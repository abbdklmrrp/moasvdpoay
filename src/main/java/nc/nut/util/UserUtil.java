package nc.nut.util;


/**
 * @author Sergiy Dyrda
 */
public class UserUtil {

    public static boolean parseEnable(int number) {
        if (number < 0 || number > 1) throw new IllegalArgumentException("Number must be 0 or 1");
        return number == 1;
    }

    public static int transferEnable(boolean enable) {
        return enable ? 1 : 0;
    }
}
