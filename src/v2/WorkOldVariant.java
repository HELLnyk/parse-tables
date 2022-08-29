package v2;

import v2.bean.Data3ParametersBean;
import v2.bean.HipDataBean;
import v2.bean.InputUserDataBean;
import v2.bean.PaymentUserDataBean;
import v2.bean.ResultBean;
import v2.util.FileReaderUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author ykalapusha
 */
public class WorkOldVariant {

    /**
     * Parameters for changing START.
     */
    public static final String INPUT_USERS_DATA_FILE = "/Users/HELLnyk/work/romap-data/old_variant/86/awt_list_14-06-2021.csv";
    public static final String HIP_FILE = "/Users/HELLnyk/work/romap-data/old_variant/86/200721.csv";
    public static final String RESULT_FILE = "/Users/HELLnyk/work/romap-data/old_variant/86/awt_result.csv";
    public static final String DATA_3_PARAMETERS_FILE = "/Users/HELLnyk/work/romap-data/old_variant/86/data3parameters.csv";
    public static final String PAYMENT_DATA_FILE =  "/Users/HELLnyk/work/romap-data/old_variant/86/paymentaccountsdata.csv";
    /**
     * Parameters for changing END.
     */

    public static final DateFormat HIP_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
    public static final DateFormat INPUT_USERS_DATA_FILE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final DateFormat SOME_DATA_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public static final DateFormat SOME_DATA_FORMAT_1 = new SimpleDateFormat("MM.dd.yyyy hh:mm");

    public static final String CHARSET_NAME = "UTF-8";         //base encoding

    private static BufferedWriter resultFileWriter;

    private static int skipped = 0;

    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();
        List<InputUserDataBean> inputUserDataBeanList = FileReaderUtil.createInputUserDataList();
        Map<String, Data3ParametersBean> data3ParametersBeanMap = FileReaderUtil.createData3ParametersMap();
        Map<String, PaymentUserDataBean> paymentUserDataBeanMap = FileReaderUtil.createPaymentUserDataMap();
        Map<String, List<HipDataBean>> hipDataMap = FileReaderUtil.createHipDataMap();

        resultFileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(RESULT_FILE)), Charset.forName(CHARSET_NAME)));
        processWork(inputUserDataBeanList, data3ParametersBeanMap, paymentUserDataBeanMap, hipDataMap);
        resultFileWriter.flush();
        resultFileWriter.close();

        long end = System.currentTimeMillis();
        System.out.println("Process time: " + (end - start) / 1000 + " seconds.");
        System.out.println("Skipped: " + skipped);
        System.out.println("DONE");

    }

    private static void processWork(List<InputUserDataBean> inputUserDataBeanList,
                                    Map<String, Data3ParametersBean> data3ParametersBeanMap,
                                    Map<String, PaymentUserDataBean> paymentUserDataBeanMap,
                                    Map<String, List<HipDataBean>> hipDataMap) throws IOException {


        writeHeaders(inputUserDataBeanList.get(0), hipDataMap.get("header").get(0));
        inputUserDataBeanList.remove(0);
        hipDataMap.remove("header");

        List<String> existingIds = ExistingIdsReader.getExistingIds();

        for (InputUserDataBean inputUserDataBean: inputUserDataBeanList) {
            Data3ParametersBean data3ParametersBean = data3ParametersBeanMap.get(inputUserDataBean.getEmail());
            if (data3ParametersBean != null && existingIds.contains(data3ParametersBean.getAccountId())) {
                skipped++;
                continue;
            }


            PaymentUserDataBean paymentUserDataBean = paymentUserDataBeanMap.get(inputUserDataBean.getEmail());
            List<HipDataBean> hipDataBeans = hipDataMap.get(inputUserDataBean.getEmail());
            List<Boolean> dateEqual = new ArrayList<>();
            List<ResultBean> resultBeans = new ArrayList<>();
            boolean isEmailExists = false;

            if (hipDataBeans != null) {
                isEmailExists = true;
                String regDate = data3ParametersBean != null ? data3ParametersBean.getRegistrationDate() : "**undefined**";
                String birthday = data3ParametersBean != null ? data3ParametersBean.getBirthday() : "**undefined**";
                for (HipDataBean hipDataBean: hipDataBeans) {
                    dateEqual.add(isSameDate(hipDataBean.getDate(), inputUserDataBean.getDatum()));
                    resultBeans.add(new ResultBean(inputUserDataBean, hipDataBean, regDate, birthday));
                }
            }

            initRegType(resultBeans);
            initUsage(resultBeans);
            workWithPayment(resultBeans, paymentUserDataBean);

            if (!isEmailExists) {
                writeToFile(new ResultBean(inputUserDataBean, HipDataBean.createEmptyHipDataBean(), "**undefined**", "**undefined**"));
            }

            boolean append =false;
            for (int i = 0; i < dateEqual.size(); i++) {
                if (dateEqual.get(i)) {
                    writeToFile(resultBeans.get(i));
                    append = true;
                    break;
                }
            }

            if (!append) {
                for (ResultBean bean: resultBeans) {
                    writeToFile(bean);
                }
            }
        }
    }

    private static Boolean isSameDate(String hipDateStr, String userDateStr) {
        try {

            Calendar hiDate = Calendar.getInstance();
            Calendar userDate = Calendar.getInstance();

            try {
                hiDate.setTime(HIP_DATE_FORMAT.parse(hipDateStr));
            } catch (ParseException e) {
                hiDate.setTime(SOME_DATA_FORMAT_1.parse(hipDateStr));
            }

            try {
                userDate.setTime(INPUT_USERS_DATA_FILE_DATE_FORMAT.parse(userDateStr));
            } catch (ParseException e) {
                userDate.setTime(SOME_DATA_FORMAT.parse(userDateStr));
            }

            return hiDate.get(Calendar.DAY_OF_YEAR) == userDate.get(Calendar.DAY_OF_YEAR) &&
                    hiDate.get(Calendar.YEAR) == userDate.get(Calendar.YEAR);

        } catch (ParseException e) {
            System.out.println("i dont now wath happend: " + e.getMessage());
            return false;
        }
    }

    private static void initRegType(List<ResultBean> result) {
        List<String> regStrings = new ArrayList<>();
        for (ResultBean bean: result) {
            if (bean.hipDataBean.getTxnType().equals("REG")) {
                regStrings.add(bean.hipDataBean.getDate());
            }
        }

        if(regStrings.isEmpty())
            return;

        Collections.sort(regStrings);
        String maxDate = regStrings.get(0);

        for(ResultBean resultBean: result)
            resultBean.setRegistrationDate(maxDate);
    }

    private static void initUsage(List<ResultBean> result) {
        for (ResultBean resultBean: result) {
            String usage = resultBean.hipDataBean.getUsage();
            if (usage.equals("") || usage.length() < 35) {
                resultBean.hipDataBean.setUsage("0");
            }
            else {
                String[] elements = usage.split(" ");
                int k = -1;
                for(int i = 0; i < elements.length; i++) {
                    if (elements[i].equals("VIP")) {
                        k = i + 1;
                        break;
                    }
                }
                if( k != -1) {
                    resultBean.hipDataBean.setUsage(elements[k]);
                }
                else
                    resultBean.hipDataBean.setUsage("0");
            }
        }
    }

    private static void workWithPayment(List<ResultBean> result, PaymentUserDataBean paymentUserDataBean) {
        if (paymentUserDataBean == null)
            return;

        for (ResultBean resultBean: result) {
            if(!paymentUserDataBean.getCity().equals("null"))
                resultBean.hipDataBean.setCity(paymentUserDataBean.getCity());

            if(!paymentUserDataBean.getZip().equals("null"))
                resultBean.hipDataBean.setZip(paymentUserDataBean.getZip());

            if(!paymentUserDataBean.getStreet().equals("null"))
                resultBean.hipDataBean.setStreet(paymentUserDataBean.getStreet());

            if(!paymentUserDataBean.getDuration().equals("null"))
                resultBean.hipDataBean.setUsage(paymentUserDataBean.getDuration());
        }
    }

    private static void writeHeaders(InputUserDataBean userDataHeader, HipDataBean hipDataHeader) throws IOException {
        ResultBean resultBean = new ResultBean(userDataHeader, hipDataHeader, "Registration Date", "Birthday");
        resultBean.hipDataBean.setHolder("Name Surname");
        writeToFile(resultBean);
    }

    private static void writeToFile (ResultBean bean) throws IOException {
        StringBuilder sBuilder = new StringBuilder().append(bean.inputUserDataBean.getBetrag()).append(";").
                append(bean.inputUserDataBean.getDecrebit()).append(";").
                append(bean.inputUserDataBean.getEmail()).append(";").
                append(getSurname(bean.hipDataBean.getHolder())).append(";").
                append(getName(bean.hipDataBean.getHolder())).append(";").
                append(bean.getBirthday()).append(";").
                append(bean.getRegistrationDate()).append(";").
                append(bean.inputUserDataBean.getErg()).append(";").
                append(bean.inputUserDataBean.getLand()).append(";").
                append(bean.inputUserDataBean.getMeth()).append(";").
                append(bean.inputUserDataBean.getDatum()).append(";").
                append(bean.inputUserDataBean.getTxnType()).append(";").
                append(bean.inputUserDataBean.getWahr()).append(";").
                append(bean.inputUserDataBean.getCurrentStatus()).append(";").
                append(bean.inputUserDataBean.getValue()).append(";").
                append(bean.hipDataBean.getShortId()).append(";").
                append(bean.hipDataBean.getResult()).append(";").
                append(bean.hipDataBean.getDateStart()).append(";").
                append(bean.hipDataBean.getStreet()).append(";").
                append(bean.hipDataBean.getZip()).append(";").
                append(bean.hipDataBean.getCity()).append(";").
                append(bean.hipDataBean.getUsage()).append(";").
                append(bean.hipDataBean.getTxnType()).append("\n");

        resultFileWriter.write(sBuilder.toString());
    }

    private static String getSurname(String holder) {
        String[] data = holder.split(" ", 2);
        if(data.length == 1) {
            return "**undefined**";
        }
        return data[1];
    }

    private static String getName(String holder) {
        String[] data = holder.split(" ", 2);
        return data[0];
    }
}
