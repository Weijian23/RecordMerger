//
//  An object to remember a row in a table.
//  The original data sheet must contains a unique value to identify a row
//  id is the value of that unique identifier
//  data is a map. column names are keys, value in a cell is the value
//

import java.util.Map;

public class DataRecord {
    private String id;
    private Map<String, String> data;

    public DataRecord(String id, Map<String, String> data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return this.id;
    }

    public Map<String,String> getData(){
        return this.data;
    }
}
