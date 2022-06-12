import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.json.JSONObject;

public class Analyzer {

    private ArrayList states;

    public Analyzer() {
        states = new ArrayList<>();
    }

    public <T> void analyze(Collection<T> collection, String name) {
        StackTraceElement stack = new Throwable().getStackTrace()[1];
        String fileName = stack.getFileName();
        int lineNumber = stack.getLineNumber();
        String content = collection.toString();
        String type = collection.getClass().getName();
    }

    public static <K, V> void analyze(HashMap<K, V> map, String name) {
        StackTraceElement stack = new Throwable().getStackTrace()[1];
        String fileName = stack.getFileName();
        int lineNumber = stack.getLineNumber();
        String content = map.toString();
        String type = map.getClass().toString();
    }

    private void addStateToJSON() {

    }

    private void writeJson() {

    }
}
