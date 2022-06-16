import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class Analyzer {

    private static class Structure {
        public String name;
        public JSONArray states;
        public final int instanceNumber;

        public Structure(String name, int instanceNumber) {
            this.name = name;
            this.states = new JSONArray();
            this.instanceNumber = instanceNumber;
        }
    }

    private static int nextInstanceNumber = 0;
    private static final Map<Integer, Structure> instanceMap = new HashMap<>();

    public static <T> T assign(T o, T n, String name) {
        if (o == null) {
            instanceMap.put(System.identityHashCode(n), new Structure(name, nextInstanceNumber));
            nextInstanceNumber++;
        } else  {
            Structure temp = instanceMap.get(System.identityHashCode(o));
            instanceMap.remove(System.identityHashCode(o));
            instanceMap.put(System.identityHashCode(n), temp);
        }
        return n;
    }

    public static void analyze(Object object) throws RuntimeException {
        StackTraceElement stack = new Throwable().getStackTrace()[1];
        // Check if the object is one of the ones getting tracked
        if (!instanceMap.containsKey(System.identityHashCode(object))) {
            return;
        }

        // Fetch the required data for json
        Structure structure = instanceMap.get(System.identityHashCode(object));
        String fileName = stack.getFileName();
        int lineNumber = stack.getLineNumber();
        String contents = object.toString();
        String structType = object.getClass().getTypeName();

        // Create the JSON object called state and assign variables
        JSONObject state = new JSONObject();
        state.put("fileName", fileName);
        state.put("lineNumber", lineNumber);
        state.put("name", structure.name);
        state.put("structType", structType);
        state.put("contents", contents);

        // Add the recent state to the states
        structure.states.put(state);
    }

    public static void writeJSON() {
        JSONObject toWrite = new JSONObject();
        JSONArray arr = new JSONArray();
        for (Structure struct: instanceMap.values()) {
            JSONObject temp = new JSONObject();
            temp.put(struct.name, struct.states);
            arr.put(temp);
        }
        toWrite.put("jsonFiles", arr);
        try {
            FileWriter writer = new FileWriter("tracked.json");
            writer.write(toWrite.toString());
            writer.flush();
            writer.close();
            System.out.println("tracked.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}