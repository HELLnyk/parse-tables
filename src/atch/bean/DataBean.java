package atch.bean;

/**
 * record for data_for_hip_script.csv.
 *
 * @author ykalapusha
 */
public class DataBean {

    private String accountId = "";
    private String username = "";
    private String name = "";
    private String locale = "";
    private String regDate = "";
    private String regTS = "";
    private String currEmail = "";
    private String regEmail = "";
    private String birthday = "";
    private String firstBoughtDate = "";
    private String firsBoughtTS = "";
    private String street = "";
    private String zip = "";
    private String city = "";
    private String familyStatus = "";
    private String lastLoginDate = "";
    private String lastLoginTS = "";
    private String locationToMeet = "";
    private String streetPD = "";
    private String zipPD = "";
    private String cityPD = "";

    public DataBean() {
    }

    public DataBean(String accountId, String username, String name, String locale, String regDate, String regTS, String currEmail, String regEmail, String birthday, String firstBoughtDate, String firsBoughtTS, String street, String zip, String city, String familyStatus, String lastLoginDate, String lastLoginTS, String locationToMeet, String streetPD, String zipPD, String cityPD) {
        this.accountId = trimSome(accountId);
        this.username = trimSome(username);
        this.name = trimSome(name);
        this.locale = trimSome(locale);
        this.regDate = trimSome(regDate);
        this.regTS = trimSome(regTS);
        this.currEmail = trimSome(currEmail);
        this.regEmail = trimSome(regEmail);
        this.birthday = trimSome(birthday);
        this.firstBoughtDate = trimSome(firstBoughtDate);
        this.firsBoughtTS = trimSome(firsBoughtTS);
        this.street = trimSome(street);
        this.zip = trimSome(zip);
        this.city = trimSome(city);
        this.familyStatus = trimSome(familyStatus);
        this.lastLoginDate = trimSome(lastLoginDate);
        this.lastLoginTS = trimSome(lastLoginTS);
        this.locationToMeet = trimSome(locationToMeet);
        this.streetPD = trimSome(streetPD);
        this.zipPD = trimSome(zipPD);
        this.cityPD = trimSome(cityPD);

    }

    public String getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLocale() {
        return locale;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getRegTS() {
        return regTS;
    }

    public String getCurrEmail() {
        return currEmail;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getFirstBoughtDate() {
        return firstBoughtDate;
    }

    public String getFirsBoughtTS() {
        return firsBoughtTS;
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

    public String getFamilyStatus() {
        return familyStatus;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public String getLastLoginTS() {
        return lastLoginTS;
    }

    public String getLocationToMeet() {
        return locationToMeet;
    }

    public String getStreetPD() {
        return streetPD;
    }

    public String getZipPD() {
        return zipPD;
    }

    public String getCityPD() {
        return cityPD;
    }



    private static String trimSome(String str) {
        if (str == null || str.trim().isEmpty())
            return "";

       return str.replaceAll("\"", "");
    }
}
