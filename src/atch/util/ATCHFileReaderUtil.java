package atch.util;

import atch.bean.DataBean;
import atch.bean.HipBean;
import atch.bean.InputUserBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static atch.Work.*;

/**
 * @author ykalapusha
 */
public class ATCHFileReaderUtil {

    public static List<InputUserBean> readInputUserBeans() throws IOException {

        List<InputUserBean> list = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(INPUT_USERS_DATA_FILE)), Charset.forName(CHARSET_NAME)));
        String line = reader.readLine();
        while ((line = reader.readLine()) != null)
            list.add(createInputUserBean(line));

        reader.close();
        return list;
    }

    private static InputUserBean createInputUserBean(String line) {
        String[] els = line.split(";");
        return new InputUserBean(els[0], els[1], els[2].toLowerCase(), els[3], els[4], els[5], els[6], els[7], els[8], els[9], els[10], els[11], els[12], els[13], els[14], els[15]);
    }

    public static Map<String, List<HipBean>> readHipMap() throws IOException {
        Map<String, List<HipBean>> map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(HIP_FILE)), Charset.forName(CHARSET_NAME)));
        String line = reader.readLine();
        createHipBean(line, "header", map);
        while ((line = reader.readLine()) != null)
            createHipBean(line, null, map);

        reader.close();
        return map;
    }

    private static void createHipBean(String line, String keyParam,  Map<String, List<HipBean>> map) {
        String[] els = line.split(";");
        HipBean bean = new HipBean(els[0], els[1], els[2], els[3].toLowerCase(), els[4]);
        String key = keyParam == null ? bean.getEmail() : keyParam;
        List<HipBean> value = map.computeIfAbsent(key, k -> new ArrayList<>());
        value.add(bean);
    }

//    public static Map<String, DataBean> readUserDataMap() throws IOException {
//        Map<String, DataBean> map = new HashMap<>();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(DATA_FILE)), Charset.forName(CHARSET_NAME)));
//        String line = reader.readLine();
//        createUserDataBean(line, "header", map);
//        while ((line = reader.readLine()) != null) {
//            createUserDataBean(line, null, map);
//        }
//        reader.close();
//        return map;
//    }

    private static void createUserDataBean(String line, String keyParam, Map<String, DataBean> currentEmailMap, Map<String, DataBean> regEmailMap) {
        String[] els = line.split(";");
        try {

            DataBean bean = new DataBean(els[0], els[1], els[2], els[3], els[4].toLowerCase(), els[5], els[6], els[7], els[8], els[9], els[10], els[11], els[12], els[13], els[14], els[15], els[16], els[17], els[18], els[19], els[20]);
            if (keyParam == null) {
                currentEmailMap.put(bean.getCurrEmail(), bean);
                regEmailMap.put(bean.getRegEmail(), bean);
            } else {
                currentEmailMap.put(keyParam, bean);
                regEmailMap.put(keyParam, bean);
            }
        } catch (Throwable e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    public static StoreMaps readUserDataStoredMap() throws IOException {
        Map<String, DataBean> mapByCurrentEmail = new HashMap<>();
        Map<String, DataBean> mapByRegEmail = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(DATA_FILE)), Charset.forName(CHARSET_NAME)));
        String line = reader.readLine();
        createUserDataBean(line, "header", mapByCurrentEmail, mapByRegEmail);
        while ((line = reader.readLine()) != null) {
            createUserDataBean(line, null, mapByCurrentEmail, mapByRegEmail);
        }
        reader.close();
        StoreMaps  storeMaps = new StoreMaps();
        storeMaps.setUserDataMapByCurrentEmail(mapByCurrentEmail);
        storeMaps.setUserDataMapByRegEmail(mapByRegEmail);
        return storeMaps;
    }
}
