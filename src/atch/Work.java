package atch;

import atch.bean.DataBean;
import atch.bean.HipBean;
import atch.bean.InputUserBean;
import atch.util.ATCHFileReaderUtil;
import atch.util.StoreMaps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author ykalapusha
 */
public class Work {

    public static final String INPUT_USERS_DATA_FILE = "/Users/hellnyk/work/romap-data/atch/3/59-1.csv";
    public static final String HIP_FILE = "/Users/hellnyk/work/romap-data/atch/3/chat070120.csv";
    public static final String DATA_FILE = "/Users/hellnyk/work/romap-data/atch/3/data_for_hip_script.csv";
    public static final String RESULT_FILE = "/Users/hellnyk/work/romap-data/atch/3/result_59-1_withRegEmail.csv";

    public static final String CHARSET_NAME = "UTF-8";
    public static final DateFormat HIP_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
    public static final DateFormat INPUT_USERS_DATA_FILE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final DateFormat SOME_DATA_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public static final DateFormat BIRTHDAY_DATA_FILE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
    public static final DateFormat DATA_FILE_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");

    public static final DateFormat EXCEL_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    private static int tryToFindByRegEmailCounter = 0;
    private static int byRegEmailCounter = 0;

    private static BufferedWriter resultFileWriter;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        List<InputUserBean> inputUserBeans = ATCHFileReaderUtil.readInputUserBeans();
        Map<String, List<HipBean>> hipMap = ATCHFileReaderUtil.readHipMap();
        StoreMaps storeMaps = ATCHFileReaderUtil.readUserDataStoredMap();

        resultFileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(RESULT_FILE)), Charset.forName(CHARSET_NAME)));

        resultFileWriter.write(getHeaders());

        processWork(inputUserBeans, hipMap, storeMaps);
        resultFileWriter.flush();
        resultFileWriter.close();

        long end = System.currentTimeMillis();
        System.out.println("Process time: " + (end - start) / 1000 + " seconds.");
        System.out.println("There is no data for current email: " + tryToFindByRegEmailCounter + " , found by reg email: " + byRegEmailCounter);
        System.out.println("DONE");
    }

    private static String getHeaders() {
        return "\"AccountId\";\"Username\";\"Name\";\"Locale\";\"Registration date (MM-dd-yyyy hh:mm:ss)\";\"Txn-Id\";\"Amount\";\"Currency\";\"Email\";\"Registration Email\";" +
                "\"Birthdate\";\"Failed transaction date\";\"First bought date\";\"Street\";\"Zip\";\"City\";\"Family status\";\"Last login Date\";\"Locations to meet\"\n";

    }

    private static void processWork(List<InputUserBean> inputUserBeans, Map<String, List<HipBean>> hipMap, StoreMaps storeMaps) throws IOException {
        for (InputUserBean inputUserBean: inputUserBeans) {
            HipBean hipBean = getHipBeanForUser(hipMap, inputUserBean.getEmail(), inputUserBean.getDatum());
//            DataBean dataBean = userDataMap.get(inputUserBean.getEmail());
//            if (dataBean == null)
//                dataBean = new DataBean();

            DataBean dataBean = getDataBean(inputUserBean.getEmail(), storeMaps);
            writeToFileElements(inputUserBean, hipBean, dataBean);
        }
    }

    private static DataBean getDataBean(String email, StoreMaps storeMaps) {
        DataBean dataBean = storeMaps.getUserDataMapByCurrentEmail().get(email);
        if (dataBean != null)
            return dataBean;

        tryToFindByRegEmailCounter++;
        dataBean = storeMaps.getUserDataMapByRegEmail().get(email);
        if (dataBean != null) {
            byRegEmailCounter++;
            return dataBean;
        }

        return new DataBean();
    }

    private static void writeToFileElements(InputUserBean inputUserBean, HipBean hipBean, DataBean dataBean) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("\"").append(dataBean.getAccountId()).append("\";");
        sb.append("\"").append(dataBean.getUsername()).append("\";");
        sb.append("\"").append(dataBean.getName()).append("\";");
        sb.append("\"").append(dataBean.getLocale()).append("\";");
        sb.append("\"").append(getDateForCorrectExcelView(dataBean.getRegDate())).append("\";");
        sb.append("\"").append(hipBean.getTxnId()).append("\";");
        sb.append("\"").append(hipBean.getAmount()).append("\";");
        sb.append("\"").append(hipBean.getCurrency()).append("\";");
        sb.append("\"").append(inputUserBean.getEmail()).append("\";");
        sb.append("\"").append(dataBean.getRegEmail()).append("\";");
        sb.append("\"").append(getBirthDay(dataBean.getBirthday())).append("\";");
        sb.append("\"").append(inputUserBean.getDatum()).append("\";");
        sb.append("\"").append(getDateForCorrectExcelView(dataBean.getFirstBoughtDate())).append("\";");
        sb.append("\"").append(getStreet(dataBean)).append("\";");
        sb.append("\"").append(getZip(dataBean)).append("\";");
        sb.append("\"").append(getCity(dataBean)).append("\";");
        sb.append("\"").append(dataBean.getFamilyStatus()).append("\";");
        sb.append("\"").append(dataBean.getLastLoginDate()).append("\";");
        sb.append("\"").append(dataBean.getLocationToMeet()).append("\"\n");

        resultFileWriter.write(sb.toString());
    }

    private static String getCity(DataBean dataBean) {
        return dataBean.getCity().equals("*** unknown ***") ? cleanTextContent(dataBean.getCityPD()) : dataBean.getCity();
    }

    private static String getZip(DataBean dataBean) {
        return dataBean.getZip().equals("*** unknown ***") ? cleanTextContent(dataBean.getCityPD()) : dataBean.getZip();
    }

    private static String getStreet(DataBean dataBean) {
        return dataBean.getStreet().equals("*** unknown ***") ? cleanTextContent(dataBean.getStreetPD()) : dataBean.getStreet();
    }

    private static String getBirthDay(String birthday) {
        try {
            return INPUT_USERS_DATA_FILE_DATE_FORMAT.format(BIRTHDAY_DATA_FILE_FORMAT.parse(birthday));
        } catch (Exception e) {
            return "none";
        }
    }

    private static String getDateForCorrectExcelView(String dateStr) {
        try {
            return EXCEL_DATE_FORMAT.format(DATA_FILE_FORMAT.parse(dateStr));
        } catch (Exception e) {
            return "none";
        }
    }

    private static HipBean getHipBeanForUser(Map<String, List<HipBean>> hipMap, String email, String datum) {
        List<HipBean> hipBeans = hipMap.get(email);

        if (hipBeans != null)
            for (HipBean hipBean: hipBeans)
                if (!isDatumEmpty(datum) && isSameDate(hipBean.getDate(), datum))
                    return hipBean;

        return new HipBean();
    }

    private static boolean isDatumEmpty(String datum) {
        return datum == null || datum.trim().isEmpty();
    }

    private static boolean isSameDate(String hipDate, String inputDate) {
        try {
            Calendar hiDate = Calendar.getInstance();
            Calendar userDate = Calendar.getInstance();

            hiDate.setTime(HIP_DATE_FORMAT.parse(hipDate));
            try {
                userDate.setTime(INPUT_USERS_DATA_FILE_DATE_FORMAT.parse(inputDate));
            } catch (ParseException e) {
                userDate.setTime(SOME_DATA_FORMAT.parse(inputDate));
            }

            return hiDate.get(Calendar.DAY_OF_YEAR) == userDate.get(Calendar.DAY_OF_YEAR) &&
                    hiDate.get(Calendar.YEAR) == userDate.get(Calendar.YEAR);

        } catch (ParseException e) {
            System.out.println("i dont now wath happend: " + e.getMessage());
            return false;
        }
    }

    private static String cleanTextContent(String text) {
        // strips off all non-ASCII characters
//        text = text.replaceAll("[^\\x00-\\x7F]", "");
        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");
        return text.trim();
    }
}
