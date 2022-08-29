package onlytest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ykalapusha
 */
public class CompareRegistrationDiff {

    public static final String CHARSET_NAME = "UTF-8";


    public static void main(String[] args) throws IOException {
        Map<String, String> oldMap = getFileAsMap("/Users/hellnyk/Downloads/compare_registrations_16_01.csv");
        Map<String, String> newMap = getFileAsMap("/Users/hellnyk/Downloads/compare_registrations.csv");

        for (String key: oldMap.keySet()) {
            String newVal = newMap.get(key);
            if (newVal == null) {
                System.out.println("Not found in new: " + key + ". Data: " + oldMap.get(key));
            }
        }
        System.out.println("************ NEW ENTRIES ************");
        for (String key: newMap.keySet()) {
            String val = oldMap.get(key);
            if (val == null) {
                System.out.println("NEW: " + newMap.get(key));
            }
        }
    }


    private static Map<String, String> getFileAsMap(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), Charset.forName(CHARSET_NAME)));
        String line = reader.readLine(); //remove headers
        Map<String, String> result = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] elems = line.split(";");
            result.put(elems[0], line);
        }
        return result;
    }
}
