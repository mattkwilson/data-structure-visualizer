import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

// Add the analyzer class to the project under analysis

// Now:
// public members: look at the entire code base
// private members: instrument only the class where the data structure

// Later:
// static: for static data structures make the analyzer instance static
// local variables:

public class Instrumentor {
    private List<CompilationUnit> compilationUnits;
    private String className;
    private String fieldName;

    public Instrumentor(String className, String fieldName, List<CompilationUnit> cus) {
        compilationUnits = cus;
        this.className = className;
        this.fieldName = fieldName;
    }

    private void instrumentPublicField() {
        // TODO: Matt
    }

    private void instrumentPrivateField() {
        // TODO: Tarek
    }

    /**
     * Throws if the field is not found and if the field is of an unsupported type
     * Throw for static fields
     * Determine which case and call the proper function
     * */
    public void instrument() {
        // TODO: Tarek
    }

    /**
     *  Run the instrumented code
     * */
    public void runDynamicAnalysis() {
        // TODO: TBD
    }

}
