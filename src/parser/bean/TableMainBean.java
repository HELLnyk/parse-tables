package parser.bean;

import parser.bean.TableOneBean;
import parser.bean.TableTwoBean;

/**
 * @author ykalapusha
 */
public class TableMainBean {

    public TableOneBean tableOneBean;
    public TableTwoBean tableTwoBean;
    private String registrationDate;
    private String birthday;

    public TableMainBean(TableOneBean tableOneBean, TableTwoBean tableTwoBean, String registrationDate, String birthday) {
        this.tableOneBean = tableOneBean;
        this.tableTwoBean = tableTwoBean;
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
