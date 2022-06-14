import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class Analyzer {

    private class Structure {
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
    private final Map<Integer, Structure> instanceMap;

    public Analyzer() {
        instanceMap = new HashMap<>();
    }

    public void createInstance(Object instance, String name) {
        instanceMap.put(System.identityHashCode(instance), new Structure(name, nextInstanceNumber));
        System.out.println(System.identityHashCode(instance));
        nextInstanceNumber++;
    }

    public void setInstance(Object instance, String name) {

    }

    public void analyze(Object object) throws RuntimeException {
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
        String structType = object.getClass().toString();

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

    public void writeJSON() {
        try {
            for (Structure struct: instanceMap.values()) {
                FileWriter writer = new FileWriter(struct.name + "_" + struct.instanceNumber + ".json");
                writer.write(struct.states.toString());
                writer.flush();
                writer.close();
                System.out.println("saved JSON at: " + struct.name + "_" + struct.instanceNumber + ".json");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}