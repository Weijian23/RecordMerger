import au.com.bytecode.opencsv.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.select.Elements;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVHelper implements FileHelper{
    private String fileName = "";

    public CSVHelper(String filename) {
        this.fileName = filename;
    }

    public List<String> getHeader() throws IOException {
        String path = this.getPath();
        CSVParser parser = this.getParser(path);
        return  this.getHeader(parser);
    }

    public Map<String, DataRecord> prepareRecord() throws IOException {
        String path = this.getPath();
        CSVParser parser = this.getParser(path);
        return  this.prepareRecord(parser);
    }

    private String getPath(){
        return DEFAULT_PATH + this.fileName;
    }

    /**
     * Get a CSVReader object
     * @param filepath location of the input file
     * @return CSVReader object
     * @throws IOException
     */
    private CSVReader getReader(String filepath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filepath));
        CSVReader csvReader = new CSVReader(reader);
        return  csvReader;
    }

    /**
     * Get a CSVParser object
     * @param filePath location of the input file
     * @return CSVParser object
     * @throws IOException
     */
    private CSVParser getParser (String filePath) throws IOException {
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
        CSVParser parser = new CSVParser(new FileReader(filePath), format);
        return parser;
    }

    /**
     * Get a list of string to indicate the name of all columns
     * @param parser
     * @return A list of column names
     */
    private List<String> getHeader(CSVParser parser) {
        return parser.getHeaderNames();
    }

    /**
     * Turn the input table into a map of ID and DataRecord object.
     * The key is ID, value is DataRecord which is an object contains all the information associated in a row in the
     * original table
     * @param parser
     * @return A map of id and DataRecord
     */
    private Map<String, DataRecord> prepareRecord(CSVParser parser) {
        Map<String, DataRecord> records = new HashMap<>();
        List<String> headerNames = this.getHeader(parser);
        for (CSVRecord record : parser) {
            Map<String, String> dataMap = new HashMap<>();
            String id = "";
            for (String name : headerNames) {
                String value = record.get(name);
                dataMap.put(name, value);
                if(name.equalsIgnoreCase("ID")) {
                    id = value;
                }
                DataRecord dataRecord = new DataRecord(id, dataMap);
                if(!id.isEmpty()) {
                    records.put(id, dataRecord);
                }
            }
        }
        return records;
    }

}
