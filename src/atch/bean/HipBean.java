package atch.bean;

/**
 * Record for hip table.
 *
 * @author ykalapusha
 */
public class HipBean {

    private String txnId;
    private String amount;
    private String currency;
    private String email;
    private String date;

    public HipBean() {
    }

    public HipBean(String txnId, String amount, String currency, String email, String date) {
        this.txnId = txnId;
        this.amount = amount;
        this.currency = currency;
        this.email = email;
        this.date = date;
    }

    public String getTxnId() {
        return txnId;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }
}
