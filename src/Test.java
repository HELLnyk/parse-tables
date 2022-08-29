import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import static atch.WorkNewVariant.CHARSET_NAME;
import static atch.WorkNewVariant.INPUT_USERS_DATA_FILE;

/**
 * @author ykalapusha
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String dir = "/Users/hellnyk/Downloads/scorecontrol/";
        String fileName = "score_control_SC_CH_weekly_08062021.csv";

        Set<String> baseIds = readAccountIds("/Users/hellnyk/Downloads/score_control_new_format_ret.csv", 8);
        Set<String> forTest = readAccountIds(dir + fileName,  8);

        int duplicates = 0;
        for (String baseId: baseIds) {
            if (forTest.contains(baseId)) {
                System.out.println(baseId + " in " + fileName);
                duplicates++;
            }
        }
        System.out.println("Duplicates: " + duplicates + " in " + fileName);
    }

    private static Set<String> readAccountIds(String fileName, int position) throws IOException {
        Set<String> ret = new HashSet<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), StandardCharsets.UTF_8));
        String line = reader.readLine();        //remove headers.
        while ((line = reader.readLine()) != null) {
            String []elems = line.split(";");
            ret.add(elems[position]);
        }
        reader.close();
        return ret;
    }


}
