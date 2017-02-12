package parser.bean;

/**
 * @author ykalapusha
 */
public class TableFourBean {

    private String email;
    private String zip;
    private String street;
    private String city;
    private String duration;

    public TableFourBean(String email, String zip, String street, String city, String duration) {
        this.email = email;
        this.zip = zip;
        this.street = street;
        this.city = city;
        this.duration = duration;
    }

    public String getEmail() {
        return email;
    }

    public String getZip() {
        return zip;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getDuration() {
        return duration;
    }
}
