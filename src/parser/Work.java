package parser;

import parser.bean.*;
import parser.util.ValueHelper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Parser for tables.
 *
 * @author ykalapusha
 */
public class Work {

    /**
     * Parameters for changing START.
     */
    private static final String TABLE_ONE = "/Users/HELLnyk/work/romap-data/alex56/54.csv";
    private static final String TABLE_TWO = "/Users/HELLnyk/work/romap-data/alex56/040919_2.csv";
    private static final String RESULT_FILENAME = "/Users/HELLnyk/work/romap-data/alex56/result_54.csv";
    /**
     * Parameters for changing END.
     */

    private static final String TABLE_THREE = "/Users/HELLnyk/work/romap-data/data3parameters.csv";
    private static final String TABLE_FOUR =  "/Users/HELLnyk/work/romap-data/paymentaccountsdata.csv";
    private static final String CHARSET_NAME = "UTF-8";         //base encoding

    private static boolean writeOnce = true;
    private static BufferedWriter writer;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(RESULT_FILENAME)), Charset.forName(CHARSET_NAME)));
//        writer = Files.newBufferedWriter(Paths.get(RESULT_FILENAME), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        BufferedReader bReaderFirstTable = new BufferedReader(new InputStreamReader(new FileInputStream(new File(TABLE_ONE)), Charset.forName(CHARSET_NAME)));
//        BufferedReader bReaderFirstTable = Files.newBufferedReader(Paths.get(TABLE_ONE), StandardCharsets.UTF_8);

        String rowTableOne = bReaderFirstTable.readLine();
        TableOneBean header = createTableOneBean(rowTableOne);
        while ((rowTableOne = bReaderFirstTable.readLine()) != null) {
            TableOneBean row = createTableOneBean(rowTableOne);
            createTable(header, row);
        }

        bReaderFirstTable.close();
        writer.flush();
        writer.close();

        long end = System.currentTimeMillis();
        System.out.println("Process time: " + (end - start) / 1000 + " seconds.");
        System.out.println("DONE");
    }

    private static void createTable(TableOneBean headerOne, TableOneBean rowOne) throws IOException {

        ArrayList<TableMainBean> result = new ArrayList<>();
        ArrayList<Boolean> dateEqual = new ArrayList<>();
        boolean isEmailExists = false;


        try(BufferedReader bReaderSecondTable = new BufferedReader(new InputStreamReader(new FileInputStream(new File(TABLE_TWO)), Charset.forName(CHARSET_NAME)))) {
//        try (BufferedReader bReaderSecondTable = Files.newBufferedReader(Paths.get(TABLE_TWO), StandardCharsets.UTF_8)) {
           String rowTableTwo = bReaderSecondTable.readLine();
           TableTwoBean headerTwo = createTableTwoBean(rowTableTwo);

           if(writeOnce) {
               writeHeaders (headerOne, headerTwo);
           }

           while ((rowTableTwo = bReaderSecondTable.readLine()) != null) {
               TableTwoBean rowTwo = createTableTwoBean(rowTableTwo);

               if(rowOne.getEmail().equals(rowTwo.getEmail())) {
                   isEmailExists = true;
                   dateEqual.add(isSameDate(rowTwo.getDate(), rowOne.getDatum()));
                   TableThreeBean birthdayAndRegister = getRegistrationDateAndBirtday(rowTwo.getEmail());
                   result.add(new TableMainBean(rowOne, rowTwo, birthdayAndRegister.getRegistrationDate(), birthdayAndRegister.getBirthday()));
               }
           }
        }catch (IOException e) {
           System.out.println("ERROR, ERROR, ERROR: " + e.getMessage());
        }

        initRegType(result);
        initUsage(result);
        workWithPayment(result);

        if(!isEmailExists) {
            writeToFile(new TableMainBean(
                    rowOne,
                    TableTwoBean.createEmptyTableBeanTwo(),
                    "**undefined**",
                    "**undefined**"
            ));
        }

        for (int i = 0; i < dateEqual.size(); i++) {
            if (dateEqual.get(i)) {
                writeToFile(result.get(i));
                return;
            }
        }

        for (TableMainBean bean: result) {
            writeToFile(bean);
        }
    }

    private static void initUsage(ArrayList<TableMainBean> result) {
        for (TableMainBean table: result) {
            String usage = table.tableTwoBean.getUsage();
            if (usage.equals("") || usage.length() < 35) {
                table.tableTwoBean.setUsage("0");
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
                    table.tableTwoBean.setUsage(elements[k]);
                }
                else
                    table.tableTwoBean.setUsage("0");
            }
        }
    }

    private static void initRegType(ArrayList<TableMainBean> result) {
        ArrayList<String> regStrings = new ArrayList<>();
        for (TableMainBean table: result) {
            if (table.tableTwoBean.getTxnType().equals("REG")) {
                regStrings.add(table.tableTwoBean.getDate());
            }
        }

        if(regStrings.isEmpty())
            return;

        Collections.sort(regStrings);
        String maxDate = regStrings.get(0);

        for(TableMainBean table: result)
            table.setRegistrationDate(maxDate);
    }

    private static void workWithPayment(ArrayList<TableMainBean> result) {
        for (TableMainBean tableMainBean : result) {
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(TABLE_FOUR)), Charset.forName(CHARSET_NAME)))
            try (BufferedReader bReader = Files.newBufferedReader(Paths.get(TABLE_FOUR), StandardCharsets.UTF_8)) {
                bReader.readLine();
                String line;
                while ((line = bReader.readLine()) != null) {
//                    line = getSomeString(line);
                    String[] parameters = line.split(";");
                    TableFourBean payment = new TableFourBean(parameters[0], parameters[1], parameters[2], parameters[3], parameters[4]);
                    if (tableMainBean.tableOneBean.getEmail().equals(payment.getEmail())) {
                        setPayment(tableMainBean, payment);
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file email and data: " + e.getMessage());
            }
        }
    }

    private static void setPayment(TableMainBean main, TableFourBean payment) {
        if(!payment.getCity().equals("null"))
            main.tableTwoBean.setCity(payment.getCity());

        if(!payment.getZip().equals("null"))
            main.tableTwoBean.setZip(payment.getZip());

        if(!payment.getStreet().equals("null"))
            main.tableTwoBean.setStreet(payment.getStreet());

        if(!payment.getDuration().equals("null"))
            main.tableTwoBean.setUsage(payment.getDuration());
    }

    private static void writeHeaders(TableOneBean oneBean, TableTwoBean twoBean) throws IOException {
        TableMainBean tableMainBean = new TableMainBean(oneBean, twoBean, "Registration Date", "Birthday");
        tableMainBean.tableTwoBean.setHolder("Name Surname");
        writeToFile(tableMainBean);
        writeOnce = false;
    }

    private static Boolean isSameDate(String dateFromMainTable, String mainDate) {
        DateFormat dateFormatMainTable = new SimpleDateFormat("dd.MM.yy");
        DateFormat mainDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date dateTable = dateFormatMainTable.parse(dateFromMainTable);
            Date mainDate1 = mainDateFormat.parse(mainDate);

            return dateTable.equals(mainDate1);
        } catch (ParseException e) {
            System.out.println("i dont now wath happend: " + e.getMessage());
            return false;
        }
    }

    private static TableThreeBean getRegistrationDateAndBirtday(String email) {
        TableThreeBean row = new TableThreeBean("**undefined**" , "**undefined**");
//      BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(TABLE_THREE)), Charset.forName(CHARSET_NAME)))
        try (BufferedReader bReader = Files.newBufferedReader(Paths.get(TABLE_THREE), StandardCharsets.UTF_8)) {
            bReader.readLine();
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] elements = line.split(";");
                if(email.equals(elements[0])) {
                    row.setBirthday(elements[1]);
                    row.setRegistrationDate(elements[2]);
                    return row;
                }
            }
        }catch (IOException e) {
            System.out.println("Error reading file email and data: " + e.getMessage());
        }
        return row;
    }


    private static TableOneBean createTableOneBean(String line) {
        String[] elements = line.split(",");
        return new TableOneBean(elements[0], elements[1], elements[2], elements[3], elements[4], elements[5], elements[6], elements[7], elements[8], elements[9], ValueHelper.getCorrectValue(elements[5], elements[9]));
    }

    private static TableTwoBean createTableTwoBean(String line) {
        String[] elements = line.split(",");
        return new TableTwoBean(elements[0], elements[1], elements[2], elements[3], elements[4], elements[5], elements[6], elements[7], elements[8], elements[9], elements[10]);
    }

    private static void writeToFile (TableMainBean bean) throws IOException {
        StringBuilder sBuilder = new StringBuilder().append(bean.tableOneBean.getBetrag()).append(",").
                append(bean.tableOneBean.getDecrebit()).append(",").
                append(bean.tableOneBean.getEmail()).append(",").
                append(getSurname(bean.tableTwoBean.getHolder())).append(",").
                append(getName(bean.tableTwoBean.getHolder())).append(",").
                append(bean.getBirthday()).append(",").
                append(bean.getRegistrationDate()).append(",").
                append(bean.tableOneBean.getErg()).append(",").
                append(bean.tableOneBean.getLand()).append(",").
                append(bean.tableOneBean.getMeth()).append(",").
                append(bean.tableOneBean.getDatum()).append(",").
                append(bean.tableOneBean.getTxnType()).append(",").
                append(bean.tableOneBean.getWahr()).append(",").
                append(bean.tableOneBean.getCurrentStatus()).append(",").
                append(bean.tableOneBean.getValue()).append(",").
                append(bean.tableTwoBean.getShortId()).append(",").
                append(bean.tableTwoBean.getResult()).append(",").
                append(bean.tableTwoBean.getDateStart()).append(",").
                append(bean.tableTwoBean.getStreet()).append(",").
                append(bean.tableTwoBean.getZip()).append(",").
                append(bean.tableTwoBean.getCity()).append(",").
                append(bean.tableTwoBean.getUsage()).append(",").
                append(bean.tableTwoBean.getTxnType()).append("\n");

        writer.write(sBuilder.toString());
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
