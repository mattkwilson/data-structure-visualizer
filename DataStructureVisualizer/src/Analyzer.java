import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Analyzer {

    private final JSONArray states;
    private final String name;
    private final int instanceNumber;

    private Map<Object, JSONArray> instanceMap;

    public Analyzer(String name, int instanceNumber) {
        this.states = new JSONArray();
        this.name = name;
        this.instanceNumber = instanceNumber;
    }

    public void createInstance(Object instance, String name) {
        // instanceMap.put(instance, new JSONArray());
        // add instance to map
        // TODO: implement
    }

    public void setInstance(Object instance, String name) {

    }

    public void analyze(Object object) {
        StackTraceElement stack = new Throwable().getStackTrace()[1];
        String fileName = stack.getFileName();
        int lineNumber = stack.getLineNumber();
        String contents = object.toString();
        String structType = object.getClass().toString();

        addState(fileName, lineNumber, structType, contents);
    }

    private void addState(String fileName, int lineNumber, String structType, String contents) {
        JSONObject state = new JSONObject();
        state.put("fileName", fileName);
        state.put("lineNumber", lineNumber);
        state.put("name", name);
        state.put("structType", structType);
        state.put("contents", contents);
        states.put(state);
    }

    public void writeJSON() {
        try {
            FileWriter writer = new FileWriter(name + "_" + instanceNumber + ".json");
            writer.write(states.toString());
            writer.flush();
            writer.close();
            System.out.println("saved JSON at: " + name + "_" + instanceNumber + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
