package parser.bean;

/**
 * @author ykalapusha
 */
public class TableThreeBean {

    private String registrationDate;
    private String birthday;

    public TableThreeBean(String registrationDate, String birthday) {
        this.registrationDate = registrationDate;
        this.birthday = birthday;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
