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
        this.betrag = betrag.trim();
        this.debitCredit = debitCredit.trim();
        this.email = email.trim();
        this.ipLand = ipLand.trim();
        this.wahr = wahr.trim();
        this.meth = meth.trim();
        this.datum = datum.trim();
        this.txnType = txnType.trim();
        this.quelle = quelle.trim();
        this.inhaber = inhaber.trim();
        this.kartenTyp = kartenTyp.trim();
        this.kartenNr = kartenNr.trim();
        this.bin = bin.trim();
        this.shortId = shortId.trim();
        this.fehlerText = fehlerText.trim();
        this.comment = comment.trim();
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
