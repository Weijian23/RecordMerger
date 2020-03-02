import au.com.bytecode.opencsv.*;
/**
 * Helper class to handle merge files
 */

import java.util.*;

public class MergeHelper {

    /**
     *Create a list of string to indicate the header of the table after merging
     * @param headerLists An array of string list. Each list contains the names of columns from one table
     * @return A list of string
     */
    public List<String> mergeHeader(List<String>[] headerLists) {
        List<String> header = new ArrayList<>();
        for (List<String> headerList: headerLists) {
            for (String name : headerList) {
                if(!header.contains(name)) {
                    header.add(name);
                }
            }
        }
        return header;
    }

    /**
     *Merge all given tables into one table
     * @param dataMaps an array of data need to be merged.
     * @param header the list of string to indicate the header in the merged table
     * @return A list of string arrays, each array is a merged row in the final table
     */
    public List<String[]> mergeTable(List<String> header, Map<String, DataRecord>[] dataMaps) {
        Map<String, Map<String,String>> recordBook = new TreeMap<>();
        List<String[]> mergedData = new ArrayList<>();

        for (Map<String, DataRecord> dataMap:dataMaps) {
            for (String key : dataMap.keySet()) {
                DataRecord recordObj = dataMap.get(key);
                Map<String, String> record = recordObj.getData();
                Map<String, String> temRecord = new HashMap<>();
                if(recordBook.containsKey(key)) {
                    temRecord = recordBook.get(key);
                }
                for(String column: record.keySet()) {
                    String value = "" + record.get(column).trim();;
                    temRecord.put(column, value);
                }
                recordBook.put(key, temRecord);
            }
        }
        for (String id : recordBook.keySet()) {
            Map<String, String> valueMap = recordBook.get(id);
            String[] cells = new String[header.size()];
            for(int i = 0; i < header.size(); i++) {
                String column = header.get(i).trim();
                String value = "" + valueMap.get(column);
                if(value.equals("null")) {
                    value = "";
                }
                cells[i] = value;
            }
            mergedData.add(cells);
        }
        return mergedData;
    }
}
