package outdated;

import java.io.*;
import java.util.ArrayList;

/**
 * Combines data from 3 tables.
 *
 * @author ykalapusha
 */
public class WorkWithTables {

    /**
     * {@link BufferedReader} instance for writing the result of association (combine) three tables into one
     */
    private static BufferedWriter writer;

    /**
     * Main method (Enter point in program).
     *
     * @param args
     *      file names, which containing table
     *
     * @throws IOException
     *      if it happens problem with reading/writing files
     */
    public static void main(String[] args) throws IOException {

        /**
         * {@code ags[0]} - file name, where is a table with main info.
         * {@code ags[1]} - file name, where is a table with  email and date only.
         * {@code ags[2]} - file name, where is a table with email and birthday only.
         */

        if (args[0].equals("") || args[1].equals("") || args[2].equals("")) {
            System.out.println("Start again. You don`t write all files");
        }


        //Open file for reading info from table where email and date only.
        BufferedReader readerEmailAndDate = new BufferedReader(new FileReader(args[1]));

         // file of result of joining three tables
        writer = new BufferedWriter(new FileWriter("/home/hellnyk/HELLnyk/fromcms/second/result.csv"));
//        writer.write("Date,Email,Birthday,Short-ID,Usage,Date Start,Result,Txn type\n");
        writer.write("Short-ID,Date,Email,Result,Date Start,Street,Zip,City,Name,Surname,Birthday,Usage,Txn type\n");

        //read info from email and date table
        try  {
            String rowEmailAndDate = readerEmailAndDate.readLine();
            while ((rowEmailAndDate = readerEmailAndDate.readLine()) != null) {
                String[] strings = rowEmailAndDate.split(",");
                ArrayList<String> emailDateBirtday = new ArrayList<>();
                emailDateBirtday.add(strings[0]);
                emailDateBirtday.add(strings[1]);
                emailDateBirtday.add(getBirthday(strings[0], args[2]));

                createNewTable(emailDateBirtday, args[0]);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        //closing result file
        readerEmailAndDate.close();
        writer.flush();
        writer.close();
    }


    /**
     * receive user`s birthday according to his email.
     *
     * @param email
     *      {@code String } email of user
     * @param fileName
     *      {@code String} file name of the table where birthday`s consists
     * @return
     *      {@code String} birthday
     */
    private static String getBirthday (String email, String fileName) {
        String result = "undefined";
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(",");
                String emailFromFile = strings[0];
                if (emailFromFile.equals(email)) {
                    result =  strings[1];
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file email and data: " + e.getMessage());
        }

        return result;
    }

    /**
     * Create rows with info of main table.
     *
     * @param emailDateBirtday
     *      user`s email and birthday
     * @param fileName
     *      file name of the table, where are main info
     * @throws IOException
     *      if it happens problem with reading/writing files
     */
    private static void createNewTable(ArrayList<String> emailDateBirtday, String fileName) throws IOException {
        ArrayList<Table> result = new ArrayList<>();
        ArrayList<Boolean> dateEqual = new ArrayList<>();
        boolean isEmailExists = false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String lineTableNames = bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineArray = line.split(",");
                if(lineArray[2].equals(emailDateBirtday.get(0))) {
                    isEmailExists = true;
                    //dateEqual.add(isSameDate(lineArray[1], emailDateBirtday.get(1)));
                    result.add(new Table(lineArray[0],
                            lineArray[1],
                            lineArray[2],
                            lineArray[3],
                            lineArray[4],
                            lineArray[5],
                            lineArray[6],
                            lineArray[7],
                            getName(lineArray[8]),
                            getSurname(lineArray[8]),
                            emailDateBirtday.get(2),
                            lineArray[9],
                            lineArray[10]));
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with getting info from main table: " + e.getMessage());
        }

        if (!isEmailExists) {
            writeToFile(new Table("**undefined**", "**undefined**", emailDateBirtday.get(0), "**undefined**", "**undefined**", "**undefined**", "**undefined**",
                    "**undefined**", "**undefined**", "**undefined**", "**undefined**", "**undefined**", "**undefined**"));
        }

        for(int i = 0; i < dateEqual.size(); i++) {
            if(dateEqual.get(i)) {
                writeToFile(result.get(i));
                return;
            }
        }

        for(Table bean: result) {
            writeToFile(bean);
        }
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

    /**
     * Check whether the same date.
     *
     * @param dateFromMainTable
     *      {@code String} date 1
     * @param mainDate
     *      {@code String} date 2
     * @return
     *      {@code true}, if dates equal, {@code false} - otherwise
     */
//    private static Boolean isSameDate(String dateFromMainTable, String mainDate) {
//        DateFormat dateFormatMainTable = new SimpleDateFormat("MM/dd/yyyy");
//        DateFormat mainDateFormat = new SimpleDateFormat("dd.MM.yyyy");
//
//        try {
//            Date dateTable = dateFormatMainTable.parse(dateFromMainTable);
//            Date mainDate1 = mainDateFormat.parse(mainDate);
//
//            return dateTable.equals(mainDate1);
//        } catch (ParseException e) {
//            System.out.println("i dont now wath happend: " + e.getMessage());
//            return false;
//        }
//    }

    /**
     * Write one unit of main info about user with birthday to result table.
     *
     * @param bean
     *      user`s info
     * @throws IOException
     *      if it happens problem with reading/writing files
     */
    private static void writeToFile(Table bean) throws IOException {
        StringBuilder sBuilder = new StringBuilder().append(bean.getShortId()).append(",").
                append(bean.getDate()).append(",").
                append(bean.getEmail()).append(",").
                append(bean.getResult()).append(",").
                append(bean.getDateStart()).append(",").
                append(bean.getStreet()).append(",").
                append(bean.getZip()).append(",").
                append(bean.getCity()).append(",").
                append(bean.getName()).append(",").
                append(bean.getSurname()).append(",").
                append(bean.getBirthday()).append(",").
                append(bean.getUsage()).append(",").
                append(bean.getTxnType()).append("\n");
        writer.write(sBuilder.toString());
    }
}
