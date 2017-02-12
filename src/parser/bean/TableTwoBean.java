package parser.bean;

/**
 * @author ykalapusha
 */
public class TableTwoBean {

    private static final String UNDEFINED_STRING = "**undefined**";

    private String shortId;
    private String date;
    private String email;
    private String result;
    private String dateStart;
    private String street;
    private String zip;
    private String city;
    private String holder;
    private String usage;
    private String txnType;

    public TableTwoBean(String shortId, String date, String email, String result, String dateStart, String street, String zip, String city, String holder, String usage, String txnType) {
        this.shortId = shortId;
        this.date = date;
        this.email = email;
        this.result = result;
        this.dateStart = dateStart;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.holder = holder;
        this.usage = usage;
        this.txnType = txnType;
    }

    public String getHolder() {
        return holder;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getShortId() {
        return shortId;
    }

    public String getUsage() {
        return usage;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getEmail() {
        return email;
    }

    public String getResult() {
        return result;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public static TableTwoBean createEmptyTableBeanTwo() {
        return new TableTwoBean(UNDEFINED_STRING, UNDEFINED_STRING, UNDEFINED_STRING, UNDEFINED_STRING, UNDEFINED_STRING, UNDEFINED_STRING,
            UNDEFINED_STRING, UNDEFINED_STRING, UNDEFINED_STRING, UNDEFINED_STRING, UNDEFINED_STRING);
    }
}
