package atch.bean;

/**
 * Record for input data.
 *
 * @author ykalapusha
 */
public class InputUserBean {

    private String betrag;
    private String debitCredit;
    private String email;
    private String ipLand;
    private String wahr;
    private String meth;
    private String datum;
    private String txnType;
    private String quelle;
    private String inhaber;
    private String kartenTyp;
    private String kartenNr;
    private String bin;
    private String shortId;
    private String fehlerText;
    private String comment;

    public InputUserBean(String betrag, String debitCredit, String email, String ipLand, String wahr, String meth, String datum, String txnType, String quelle, String inhaber, String kartenTyp, String kartenNr, String bin, String shortId, String fehlerText, String comment) {
        this.betrag = betrag;
        this.debitCredit = debitCredit;
        this.email = email;
        this.ipLand = ipLand;
        this.wahr = wahr;
        this.meth = meth;
        this.datum = datum;
        this.txnType = txnType;
        this.quelle = quelle;
        this.inhaber = inhaber;
        this.kartenTyp = kartenTyp;
        this.kartenNr = kartenNr;
        this.bin = bin;
        this.shortId = shortId;
        this.fehlerText = fehlerText;
        this.comment = comment;
    }


    public String getBetrag() {
        return betrag;
    }

    public String getDebitCredit() {
        return debitCredit;
    }

    public String getEmail() {
        return email;
    }

    public String getIpLand() {
        return ipLand;
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

    public String getQuelle() {
        return quelle;
    }

    public String getInhaber() {
        return inhaber;
    }

    public String getKartenTyp() {
        return kartenTyp;
    }

    public String getKartenNr() {
        return kartenNr;
    }

    public String getBin() {
        return bin;
    }

    public String getShortId() {
        return shortId;
    }

    public String getFehlerText() {
        return fehlerText;
    }

    public String getComment() {
        return comment;
    }
}
