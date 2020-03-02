import org.apache.commons.csv.CSVParser;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import au.com.bytecode.opencsv.*;

public class RecordMerger {

	public static final String FILENAME_COMBINED = "combined.csv";

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}

		// your code starts here.
		List<String>[] headerList = new List[args.length];
		Map<String, DataRecord>[] dataMaps = new Map[args.length];

		//loop through all the files in arg[], files must be located in the 'data' folder
		//Parse each file. Add header to headerList. Add data rows to dataMaps
		for(int i = 0; i < args.length; i++) {
			String filename = args[i];
			//Handle html file
			if(filename.contains(".html")) {
				HTMLHelper htmlHelper = new HTMLHelper(filename);
				Map<String, DataRecord> htmlDataMap = htmlHelper.prepareRecord();
				List<String> htmlHeaderNames = htmlHelper.getHeader();
				dataMaps[i] = htmlDataMap;
				headerList[i] = htmlHeaderNames;
			}
			//Handle csv file
			else if(filename.contains(".csv")) {
				CSVHelper csvHelper = new CSVHelper(filename);
				Map<String, DataRecord> csvDataMap = csvHelper.prepareRecord();
				List<String> csvHeaderNames = csvHelper.getHeader();
				dataMaps[i] = csvDataMap;
				headerList[i] = csvHeaderNames;
			}
			//If need to handle more file types, add more cases here
			else {
				System.err.println("This file type is not supported: " + filename);
				System.exit(1);
			}
		}

		//Merge tables by using MergeHelper object
		MergeHelper mergeHelper = new MergeHelper();
		List<String> header = mergeHelper.mergeHeader(headerList);
		List<String[]> dataRows = mergeHelper.mergeTable(header, dataMaps);

		//Prepare table header and rows to feed to CSVWriter
		List<String[]> tableRows = new LinkedList<String[]>();
		String[] hearderArray = new String[header.size()];
		for (int i = 0; i < header.size(); i++) {
			hearderArray[i] = header.get(i);
		}
		tableRows.add(hearderArray);

		for (String[] row: dataRows) {
			tableRows.add(row);
		}

		//Out put final file
		CSVWriter csvWriter = new CSVWriter(new FileWriter("../" + FILENAME_COMBINED));
		csvWriter.writeAll(tableRows);
		csvWriter.close();
	}
}
