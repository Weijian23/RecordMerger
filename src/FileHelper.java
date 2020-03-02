import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileHelper {
    static final String DEFAULT_PATH = "../data/";
    public List<String> getHeader() throws IOException;
    public Map<String, DataRecord> prepareRecord() throws IOException;
}
