package outdated;

/**
 * Bean. Represents user info from main table.
 *
 * @author ykalapusha
 */
public class TableResultBean {

    private String date;
    private String email;
    private String birthday;
    private String sortId;
    private String usage;
    private String dateStart;
    private String result;
    private String txnType;

    public TableResultBean(String date, String email, String birthday, String sortId, String usage, String dateStart, String result, String txnType) {
        this.date = date;
        this.email = email;
        this.birthday = birthday;
        this.sortId = sortId;
        this.usage = usage;
        this.dateStart = dateStart;
        this.result = result;
        this.txnType = txnType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
}
