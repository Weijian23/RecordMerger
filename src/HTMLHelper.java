import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTMLHelper implements FileHelper {
    private String filename = "";

    public HTMLHelper(String filename) {
        this.filename = filename;
    }

    public List<String> getHeader() throws IOException {
        String path = this.getPath();
        Elements rows = this.getTable(path);
        return  this.getHeader(rows);
    }

    public Map<String, DataRecord> prepareRecord() throws IOException {
        String path = this.getPath();
        Elements rows = this.getTable(path);
        return  this.prepareRecord(rows);
    }

    private String getPath(){
        return DEFAULT_PATH + this.filename;
    }


    /**
     * Get a org.jsoup.nodes.Document object
     * @param filePath location of the input file
     * @return org.jsoup.nodes.Document object
     * @throws IOException
     */
    public Document getDoc(String filePath) throws IOException {
        File input = new File(filePath);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        return doc;
    }

    /**
     * Get a org.jsoup.nodes.Element object
     * @param filePath location of the input file
     * @return org.jsoup.nodes.Element object
     * @throws IOException
     */
    private Elements getTable(String filePath) throws IOException {
        File input = new File(filePath);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        return doc.getElementsByTag("tr");
    }

    /**
     * Get a list of string to indicate the name of all columns
     * @param rows content of the table
     * @return A list of column names
     */
    private List<String> getHeader(Elements rows) {
        List<String> headerNames = new ArrayList<>();
        Elements header = rows.select("th");
        for (Element name : header) {
            headerNames.add(name.text());
        }
        return  headerNames;
    }

    /**
     * Turn the input table into a map of ID and DataRecord object.
     * The key is ID, value is DataRecord which is an object contains all the information associated in a row in the
     * original table
     * @param  rows content of the table
     * @return A map of id and DataRecord
     */
    private Map<String, DataRecord> prepareRecord(Elements rows) {
        Map<String, DataRecord> records = new HashMap<>();
        Elements header = rows.select("th");
        String[] headerArray = new String[header.size()];
        int idIndex = Integer.MIN_VALUE;
        for(int i = 0; i < header.size(); i++) {
            Element column = header.get(i);
            headerArray[i] = column.text();
            if(column.text().equalsIgnoreCase("ID")) {
                idIndex = i;
            }
        }
        for (Element row: rows) {
            Elements cells = row.select("td");
            Map<String,String> valueMap = new HashMap<>();
            String id = "";
            for(int i = 0; i < cells.size(); i++){
                Element cell = cells.get(i);
                String value = cell.text();
                value = value.replace("\u00a0","");
                if(i == idIndex) {
                    id = value;
                }
                valueMap.put(headerArray[i], value);
            }
            if(!id.isEmpty()) {
                DataRecord record = new DataRecord(id, valueMap);
                records.put(id, record);
            }
        }
        return  records;
    }
}
