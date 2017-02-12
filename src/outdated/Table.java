package outdated;

/**
 * @author ykalapusha
 */
public class Table {

    private String shortId;
    private String date;
    private String email;
    private String result;
    private String dateStart;
    private String street;
    private String zip;
    private String city;
    private String name;
    private String surname;
    private String birthday;
    private String usage;
    private String txnType;

    Table(String shortId, String date, String email, String result, String dateStart, String street, String zip, String city, String name, String surname, String birthday, String usage, String txnType) {
        this.shortId = shortId;
        this.date = date;
        this.email = email;
        this.result = result;
        this.dateStart = dateStart;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.usage = usage;
        this.txnType = txnType;
    }

    String getShortId() {
        return shortId;
    }

    void setShortId(String shortId) {
        this.shortId = shortId;
    }

    String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getResult() {
        return result;
    }

    void setResult(String result) {
        this.result = result;
    }

    String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    String getZip() {
        return zip;
    }

    void setZip(String zip) {
        this.zip = zip;
    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    String getUsage() {
        return usage;
    }

    void setUsage(String usage) {
        this.usage = usage;
    }

    String getStreet() {
        return street;
    }

    void setStreet(String street) {
        this.street = street;
    }

    String getTxnType() {
        return txnType;
    }

    void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    String getBirthday() {
        return birthday;
    }

    void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
