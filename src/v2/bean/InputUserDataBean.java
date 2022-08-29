package v2.bean;

/**
 * @author ykalapusha
 */
public class InputUserDataBean {
    private static final String EMPTY_STRING = "**undefined**";

    private String betrag;
    private String decrebit;
    private String email;
    private String erg;
    private String land;
    private String wahr;
    private String meth;
    private String datum;
    private String txnType;
    private String currentStatus;
    private String cardType;
    private String updateDate;
    private int value;

    public InputUserDataBean(String betrag, String decrebit, String email, String erg, String land, String wahr, String meth, String datum, String txnType, String currentStatus, int value) {
        this.betrag = betrag;
        this.decrebit = decrebit;
        this.email = email;
        this.erg = erg;
        this.land = land;
        this.wahr = wahr;
        this.meth = meth;
        this.datum = datum;
        this.txnType = txnType;
        this.currentStatus = currentStatus;
        this.value = value;
    }

    public InputUserDataBean(String betrag, String decrebit, String email, String erg, String land, String wahr, String meth, String datum, String txnType, String currentStatus, String cardType, String updateDate, int value) {
        this.betrag = betrag;
        this.decrebit = decrebit;
        this.email = email;
        this.erg = erg;
        this.land = land;
        this.wahr = wahr;
        this.meth = meth;
        this.datum = datum;
        this.txnType = txnType;
        this.currentStatus = currentStatus;
        this.cardType = cardType;
        this.updateDate = updateDate;
        this.value = value;
    }

    public String getBetrag() {
        return betrag;
    }

    public String getDecrebit() {
        return decrebit;
    }

    public String getEmail() {
        return email;
    }

    public String getErg() {
        return erg;
    }

    public String getLand() {
        return land;
    }

    public String getWahr() {
        return wahr;
    }

    public String getMeth() {
        return meth;
    }

    public String getDatum() {
        return datum;
    }

    public String getTxnType() {
        return txnType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public int getValue() {
        return value;
    }



    public static InputUserDataBean createEmptyTableOne (String email) {
        return new InputUserDataBean(EMPTY_STRING, EMPTY_STRING, email, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
                EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, 0);
    }
}
