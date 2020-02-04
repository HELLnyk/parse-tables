package atch.util;

import atch.bean.DataBean;

import java.util.Map;

/**
 * @author ykalapusha
 */
public class StoreMaps {

    Map<String, DataBean> userDataMapByCurrentEmail;
    Map<String, DataBean> userDataMapByRegEmail;

    public Map<String, DataBean> getUserDataMapByCurrentEmail() {
        return userDataMapByCurrentEmail;
    }

    public void setUserDataMapByCurrentEmail(Map<String, DataBean> userDataMapByCurrentEmail) {
        this.userDataMapByCurrentEmail = userDataMapByCurrentEmail;
    }

    public Map<String, DataBean> getUserDataMapByRegEmail() {
        return userDataMapByRegEmail;
    }

    public void setUserDataMapByRegEmail(Map<String, DataBean> userDataMapByRegEmail) {
        this.userDataMapByRegEmail = userDataMapByRegEmail;
    }
}
