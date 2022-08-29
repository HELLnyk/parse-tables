package v2.util;

import v2.bean.Data3ParametersBean;
import v2.bean.HipDataBean;
import v2.bean.InputUserDataBean;
import v2.bean.PaymentUserDataBean;

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

import static v2.WorkOldVariant.*;

/**
 * @author ykalapusha
 */
public class FileReaderUtil {

    public static List<InputUserDataBean> createInputUserDataList() throws IOException {
        String line = "";
        List<InputUserDataBean> list = new LinkedList<>();
        BufferedReader inputUserDataReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(INPUT_USERS_DATA_FILE)), Charset.forName(CHARSET_NAME)));
        while ((line = inputUserDataReader.readLine()) != null) {
            list.add(createAndPutToMapInputUserData(line));
        }
        inputUserDataReader.close();
        return list;
    }

    private static InputUserDataBean createAndPutToMapInputUserData(String line) {
        String[] elements = line.split(";");
        return new InputUserDataBean(elements[0], elements[1], elements[2], elements[3], elements[4], elements[5], elements[6], elements[7], elements[8], elements[9], ValueHelper.getCorrectValue(elements[5], elements[9]));
    }

    public static Map<String, Data3ParametersBean> createData3ParametersMap() throws IOException {
        Map<String, Data3ParametersBean> map = new HashMap<>();
        BufferedReader data3ParametersReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(DATA_3_PARAMETERS_FILE)), Charset.forName(CHARSET_NAME)));
        String line = data3ParametersReader.readLine();
        createAndPutToMapData3Parameters(line, "header", map);
        while ((line = data3ParametersReader.readLine()) != null) {
            createAndPutToMapData3Parameters(line, null, map);
        }
        data3ParametersReader.close();
        return map;
    }

    private static void createAndPutToMapData3Parameters(String line, String key, Map<String, Data3ParametersBean> map) {
        String[] elements = line.split(";");
        Data3ParametersBean bean = new Data3ParametersBean(elements[0], elements[1], elements[3], elements[2]);
        if (key == null)
            map.put(bean.getEmail(), bean);
        else
            map.put(key, bean);
    }

    public static Map<String, PaymentUserDataBean> createPaymentUserDataMap() throws IOException {
        Map<String, PaymentUserDataBean> map = new HashMap<>();
        BufferedReader paymentUserDataReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(PAYMENT_DATA_FILE)), Charset.forName(CHARSET_NAME)));
        String line = paymentUserDataReader.readLine();
        createAndPutToMapPaymentUserData(line, "header", map);
        while ((line = paymentUserDataReader.readLine()) != null) {
            createAndPutToMapPaymentUserData(line, null, map);
        }
        paymentUserDataReader.close();
        return map;
    }

    private static void createAndPutToMapPaymentUserData(String line, String key, Map<String, PaymentUserDataBean> map) {
        String[] elements = line.split(";");
        PaymentUserDataBean bean = new PaymentUserDataBean(elements[0], elements[1], elements[2], elements[3], elements[4]);
        if (key == null)
            map.put(bean.getEmail(), bean);
        else
            map.put(key, bean);
    }

    public static Map<String, List<HipDataBean>> createHipDataMap() throws IOException {
        Map<String, List<HipDataBean>> map = new HashMap<>();
        BufferedReader hipDataReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(HIP_FILE)), Charset.forName(CHARSET_NAME)));
        String line = hipDataReader.readLine();
        createAndPutToMapHipData(line, "header", map);
        while ((line = hipDataReader.readLine()) != null) {
            createAndPutToMapHipData(line, null, map);
        }
        hipDataReader.close();
        return map;
    }

    private static void createAndPutToMapHipData(String line, String keyParam, Map<String, List<HipDataBean>> map) {
        String[] elements = line.split(";");
        HipDataBean hipDataBean =  new HipDataBean(elements[0], elements[1], elements[2], elements[3], elements[4], elements[5], elements[6], elements[7], elements[8], elements[9], elements[10]);
        String key = keyParam == null ? hipDataBean.getEmail() : keyParam;
        List<HipDataBean> value = map.computeIfAbsent(key, k -> new ArrayList<>());
        value.add(hipDataBean);
    }
}
