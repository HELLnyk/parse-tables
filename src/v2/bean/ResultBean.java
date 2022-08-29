package v2.bean;

/**
 * @author ykalapusha
 */
public class ResultBean {
    public InputUserDataBean inputUserDataBean;
    public HipDataBean hipDataBean;
    private String registrationDate;
    private String birthday;

    public ResultBean(InputUserDataBean inputUserDataBean, HipDataBean hipDataBean, String registrationDate, String birthday) {
        this.inputUserDataBean = inputUserDataBean;
        this.hipDataBean = hipDataBean;
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
}
