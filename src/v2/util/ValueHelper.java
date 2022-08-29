package v2.util;

/**
 * @author ykalapusha
 */
public class ValueHelper {

    private static final int ZERO = 0;

    public static int getCorrectValue(String cash, String status) {
        switch (cash) {
            case "CHF":
                return getFromStatusCommon(status);
            case "EUR":
                return getFromStatusCommon(status);
            case "CZK":
                return getFromStatusCZK(status);
            default:
                return ZERO;
        }
    }

    private static int getFromStatusCZK(String status) {
        switch (status) {
            case "Maria schicken!":
                return 300;
            case "Maria schicken!CB!":
                return 1000;
            default:
                return ZERO;
        }
    }

    private static int getFromStatusCommon(String status) {
        switch (status) {
            case "Maria schicken!":
                return 10;
            case "Maria schicken!CB!":
                return 50;
            default:
                return ZERO;
        }
    }


}
