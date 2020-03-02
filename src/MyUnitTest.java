import org.apache.commons.csv.CSVParser;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MyUnitTest {

    static final String HTML_PATH = "HTML_TEST_FILE";
    static final String CSV_PATH = "CSV_TEST_FILE";

    @Test
    public void testHTMLGetHeader() throws IOException {
        HTMLHelper htmlHelper = new HTMLHelper(HTML_PATH);
        //to be implemented
//        Elements rows = htmlHelper.getTable(HTML_PATH);
        List<String> headers = htmlHelper.getHeader();

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("column1");
        expectedResult.add("column2");
        expectedResult.add("column3");
        expectedResult.add("column4");

        assertEquals(expectedResult, headers);
    }

    @Test
    public void testHTMLPrepareRecord() throws IOException {
        HTMLHelper htmlHelper = new HTMLHelper(HTML_PATH);
        //to be implemented
//        Elements rows = htmlHelper.getTable(HTML_PATH);
        Map<String, DataRecord> result = htmlHelper.prepareRecord();

        String expectedId = "2020";
        Map<String,String> expectedDataMap = new HashMap<>();
        expectedDataMap.put("key1", "value1");
        expectedDataMap.put("key2", "value2");
        expectedDataMap.put("key3", "value3");
        expectedDataMap.put("key4", "value4");
        DataRecord expectedDataRecord = new DataRecord(expectedId, expectedDataMap);
        Map<String,DataRecord> expectedResult = new HashMap<>();
        expectedResult.put(expectedId, expectedDataRecord);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCSVGetHeader() throws IOException {
        CSVHelper csvHelper = new CSVHelper(CSV_PATH);
        //to be implemented
//        CSVParser parser = csvHelper.getParser(CSV_PATH);
        List<String> headers = csvHelper.getHeader();

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("column1");
        expectedResult.add("column2");
        expectedResult.add("column3");
        expectedResult.add("column4");

        assertEquals(expectedResult, headers);
    }

    @Test
    public void testCSVPrepareRecord() throws IOException {
        CSVHelper csvHelper = new CSVHelper(CSV_PATH);
        //to be implemented
//        CSVParser parser = csvHelper.getParser(CSV_PATH);
        Map<String, DataRecord> result = csvHelper.prepareRecord();

        String expectedId = "2020";
        Map<String,String> expectedDataMap = new HashMap<>();
        expectedDataMap.put("key1", "value1");
        expectedDataMap.put("key2", "value2");
        expectedDataMap.put("key3", "value3");
        expectedDataMap.put("key4", "value4");
        DataRecord expectedDataRecord = new DataRecord(expectedId, expectedDataMap);
        Map<String,DataRecord> expectedResult = new HashMap<>();
        expectedResult.put(expectedId, expectedDataRecord);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testMergeHeader() {
        MergeHelper mergeHelper = new MergeHelper();
        //to be implemented
        List<String> inputHearders1 = new ArrayList<>();
        inputHearders1.add("column1");
        inputHearders1.add("column2");
        inputHearders1.add("column3");
        List<String> inputHearders2 = new ArrayList<>();
        inputHearders2.add("column2");
        inputHearders2.add("column3");
        inputHearders2.add("column4");
        List<String>[] input = new List[2];
        input[0] = inputHearders1;
        input[1] = inputHearders2;

        List<String> result = mergeHelper.mergeHeader(input);

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("column1");
        expectedResult.add("column2");
        expectedResult.add("column3");
        expectedResult.add("column4");

        assertEquals(expectedResult, result);
    }

    @Test
    public void testMergeTable() throws IOException {
        MergeHelper mergeHelper = new MergeHelper();
        //to be implemented
        List<String> inputHearders = new ArrayList<>();
        inputHearders.add("column1");
        inputHearders.add("column2");
        inputHearders.add("column3");
        inputHearders.add("column4");

        CSVHelper csvHelper = new CSVHelper(CSV_PATH);
//        CSVParser parser = csvHelper.getParser(CSV_PATH);
        Map<String, DataRecord> resultCSV = csvHelper.prepareRecord();
        HTMLHelper htmlHelper = new HTMLHelper(HTML_PATH);
//        Elements rows = htmlHelper.getTable(HTML_PATH);
        Map<String, DataRecord> resultHTML = htmlHelper.prepareRecord();

        Map<String, DataRecord>[] inputDataMaps = new Map[2];
        inputDataMaps[0] = resultHTML;
        inputDataMaps[1] = resultCSV;

        List<String[]> result = mergeHelper.mergeTable(inputHearders, inputDataMaps);

        String[] expectedRow1 = new String[4];
        expectedRow1[0] = "value1";
        expectedRow1[1] = "value2";
        expectedRow1[2] = "value3";
        expectedRow1[3] = "value4";
        String[] expectedRow2 = new String[4];
        expectedRow2[0] = "value1";
        expectedRow2[1] = "value2";
        expectedRow2[2] = "value3";
        expectedRow2[3] = "value4";
        List<String[]> expectedResult = new ArrayList<>();
        expectedResult.add(expectedRow1);
        expectedResult.add(expectedRow2);

        assertEquals(expectedResult, result);
    }
}
