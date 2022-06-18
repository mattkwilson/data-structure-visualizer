import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Analyzer {

    private enum Type {
        ARRAY,
        HASHMAP,
    }

    private static class Structure {
        public String name;
        public JSONArray states;
        public final int instanceNumber;

        public Type type;

        public Structure(String name, int instanceNumber, Type type) {
            this.name = name;
            this.states = new JSONArray();
            this.instanceNumber = instanceNumber;
            this.type = type;
        }
    }

    private static int nextInstanceNumber = 0;
    private static final Map<Integer, Structure> instanceMap = new HashMap<>();

    public static <T> T assign(T o, T n, String name, int lineNumber, String... linesOfCode) {
        Type type = n.getClass().getTypeName().contains("List") ? Type.ARRAY : Type.HASHMAP;
        if (o == null) {
            instanceMap.put(System.identityHashCode(n), new Structure(name, nextInstanceNumber, type));
            nextInstanceNumber++;
        } else  {
            Structure temp = instanceMap.get(System.identityHashCode(o));
            instanceMap.remove(System.identityHashCode(o));
            instanceMap.put(System.identityHashCode(n), temp);
        }
        analyze_(n, lineNumber, linesOfCode);
        return n;
    }

    public static void analyze(Object object, int lineNumber, String... linesOfCode) {
        analyze_(object, lineNumber, linesOfCode);
    }

    private static void analyze_(Object object, int lineNumber, String... linesOfCode) throws RuntimeException {
        StackTraceElement stack = new Throwable().getStackTrace()[2];
        // Check if the object is one of the ones getting tracked
        if (!instanceMap.containsKey(System.identityHashCode(object))) {
            return;
        }

        // Fetch the required data for json
        Structure structure = instanceMap.get(System.identityHashCode(object));
        String fileName = stack.getFileName();
        String contents = object.toString();
        String structType = structure.type == Type.ARRAY ? "Array" : "Hashmap";

        // Create the JSON object called state and assign variables
        JSONObject state = new JSONObject();
        state.put("fileName", fileName);
        state.put("lineNumber", lineNumber);
        state.put("name", structure.name);
        state.put("structType", structType);
        state.put("contents", contents);
        state.put("code", linesOfCode);

        // Add the recent state to the states
        structure.states.put(state);
    }

    public static void writeJSON(String frontendJsonPath) {
        JSONObject toWrite = new JSONObject();
        JSONArray arr = new JSONArray();
        for (Structure struct: instanceMap.values()) {
            String structType = struct.type == Type.ARRAY ? "array" : "map";
            JSONObject temp = new JSONObject();
            temp.put("array", struct.states);
            arr.put(temp);
        }
        toWrite.put("jsonFiles", arr);
        try {
            System.out.println("Writing output to " + frontendJsonPath + "tracked.json");
            new File(frontendJsonPath).mkdirs();
            FileWriter writer = new FileWriter(frontendJsonPath + "tracked.json");
            writer.write(toWrite.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}