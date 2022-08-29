package v2.bean;

/**
 * @author ykalapusha
 */
public class Data3ParametersBean {
    private String accountId;
    private String email;
    private String registrationDate;
    private String birthday;

    public Data3ParametersBean(String accountId, String email, String registrationDate, String birthday) {
        this.accountId = accountId;
        this.email = email;
        this.registrationDate = registrationDate;
        this.birthday = birthday;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
