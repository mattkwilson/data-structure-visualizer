import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import exceptions.InvalidClassNameException;
import exceptions.InvalidFieldName;

import java.util.LinkedList;
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
        System.out.println("Instrumenting public field...");
        // TODO: Matt
    }

    private void instrumentPrivateField() {
        System.out.println("Instrumenting private field...");
        // TODO: Tarek
    }

    /**
     * Throws if the field is not found and if the field is of an unsupported type
     * Throw for static fields
     * Determine which case and call the proper function
     * */
    public void instrument() {
        System.out.println("Stared instrumenting the code...");
        // Find the class
        List<ClassOrInterfaceDeclaration> classes = getNodes(compilationUnits, ClassOrInterfaceDeclaration.class);
        ClassOrInterfaceDeclaration classUnderAnalysis = null;
        for (ClassOrInterfaceDeclaration c : classes) {
            if (c.getName().toString().equals(this.className)) {
                classUnderAnalysis = c;
            }
        }
        if (classUnderAnalysis == null) {
            throw new InvalidClassNameException();
        }
        // Find the member
        List<VariableDeclarator> variables = classUnderAnalysis.findAll(VariableDeclarator.class);
        variables = variables.stream().filter(v -> v.getName().toString().equals(fieldName)).toList();
        if (variables.size() != 1) {
            throw new InvalidFieldName();
        }
        VariableDeclarator variable = variables.get(0);
        if (!(variable.getParentNode().get() instanceof FieldDeclaration)) {
            throw new InvalidFieldName();
        }
        FieldDeclaration field = (FieldDeclaration) variable.getParentNode().get();
        // Call the right function based on modifier
        List<Modifier.Keyword> modifiers = field.getModifiers().stream().map(Modifier::getKeyword).toList();
        switch (modifiers.size()) {
            case 2:
                if (!modifiers.contains(Modifier.Keyword.FINAL)) {
                    throw new InvalidFieldName();
                }
            case 1:
                if (modifiers.contains(Modifier.Keyword.PUBLIC)) {
                    instrumentPublicField();
                } else if (modifiers.contains(Modifier.Keyword.PRIVATE)) {
                    instrumentPrivateField();
                } else {
                    throw new InvalidFieldName();
                }
                break;
            default:
                throw new InvalidFieldName();
        }
        System.out.println("Finished instrumenting the code!");
    }

    private static List getNodes(List<CompilationUnit> ast, Class nodeClass) {
        List res = new LinkedList<>();
        ast.forEach(cu -> res.addAll(cu.findAll(nodeClass)));
        return res;
    }

    /**
     *  Add the analyzer class to the project under analysis
     *  Run the instrumented code or save it to disk, so it can be run manually
     * */
    public void runDynamicAnalysis() {
        // TODO: TBD
    }
}
