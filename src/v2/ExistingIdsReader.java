package v2;

import atch.bean.InputUserBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import static atch.WorkNewVariant.CHARSET_NAME;

/**
 * @author ykalapusha
 */
public class ExistingIdsReader {

    public static List<String> getExistingIds() throws IOException {
        List<String> list = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/hellnyk/work/romap-data/existing_ids.txt")), Charset.forName(CHARSET_NAME)));
        String line = reader.readLine();
        while ((line = reader.readLine()) != null)
            list.add(line);

        reader.close();
        return list;
    }
}
