import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.nodeTypes.NodeWithOptionalScope;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.utils.SourceRoot;
import exceptions.InvalidClassNameException;
import exceptions.InvalidFieldName;
import exceptions.UnsupportedFieldType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// Add the analyzer class to the project under analysis

// Now:
// public members: look at the entire code base
// private members: instrument only the class where the data structure

// Later:
// static: for static data structures make the analyzer instance static
// local variables:

public class Instrumentor {

    private final String ANALYZER_PATH = "src/Analyzer.java";
    private List<CompilationUnit> compilationUnits;
    private String className;
    private String fieldName;
    private JavaParser parser;

    private MethodDeclaration mainMethod;

    public Instrumentor(String className, String fieldName, List<CompilationUnit> cus) {
        compilationUnits = cus;
        this.className = className;
        this.fieldName = fieldName;
        parser = new JavaParser();
    }

    private void instrumentProject(List<ClassOrInterfaceDeclaration> classes, FieldDeclaration field) {
        List<VariableDeclarator> declarators = field.findAll(VariableDeclarator.class);
        declarators.forEach(this::injectInitializerExpression);
        classes.forEach(classDec -> instrumentClass(classDec, field));
        addCompilationUnitAnalyzer();
    }

    private void addCompilationUnitAnalyzer() {
        File analyzerJavaFile = new File(ANALYZER_PATH);

        try {
            ParseResult<CompilationUnit> parseResult = parser.parse(analyzerJavaFile);
            if(parseResult.isSuccessful() && parseResult.getResult().isPresent()) {
                CompilationUnit analyzerCU = parseResult.getResult().get();
                compilationUnits.add(analyzerCU);
            }
        } catch (FileNotFoundException ignored) {

        }
        throw new RuntimeException("Error adding compilation unit for the Analyzer.java file");
    }

    private void instrumentClass(ClassOrInterfaceDeclaration classDec, FieldDeclaration field) {
        System.out.println("Instrumenting class: " + classDec.getNameAsString());
        List<CallableDeclaration> methods = classDec.findAll(CallableDeclaration.class);
        boolean isClassUnderAnalysis = classDec.getNameAsString().equals(className);
        methods.forEach(method -> instrumentMethod(method, field, isClassUnderAnalysis));
    }

    private void instrumentMethod(CallableDeclaration method, FieldDeclaration field, boolean isClassUnderAnalysis) {
        System.out.println("\tInstrumenting method: " + method.getNameAsString());
        // find field modifying statements
        List<String> modifyingMethods = getModifyingMethods(field.getVariable(0));
        List<MethodCallExpr> methodCalls = method.findAll(MethodCallExpr.class);
        methodCalls.removeIf(m -> !modifyingMethods.contains(m.getNameAsString()));
        methodCalls.forEach(this::injectAnalyzer);
        // find field assignment statements
        if (isClassUnderAnalysis) {
        List<AssignExpr> assignments = method.findAll(AssignExpr.class);
        assignments.removeIf(a -> !a.getTarget().toString().matches("(this.)?" + fieldName));
        assignments.forEach(this::injectAssignExpression);
        } // else { this is more complicated because we have to check the type of the scope accessing the field } TODO TBD later

        if(isMainMethod(method)) {
            if(mainMethod != null) {
                throw new RuntimeException("Multiple main methods in project is not allowed");
            }
            mainMethod = (MethodDeclaration) method;
            injectWriteJson(mainMethod);
        }
    }

    private boolean isMainMethod(CallableDeclaration callableDeclaration) {
        if(callableDeclaration instanceof MethodDeclaration) {
            MethodDeclaration methodDec = (MethodDeclaration) callableDeclaration;
            try {
                return methodDec.getNameAsString().equals("main") &&
                        methodDec.getTypeAsString().equals("void") &&
                        methodDec.getModifiers().size() == 2 &&
                        methodDec.getModifiers().get(0).getKeyword().toString().equals("PUBLIC") &&
                        methodDec.getModifiers().get(1).getKeyword().toString().equals("STATIC") &&
                        methodDec.getParameters().size() == 1 &&
                        methodDec.getParameters().get(0).getNameAsString().equals("args") &&
                        methodDec.getParameters().get(0).getTypeAsString().equals("String[]");
            } catch (RuntimeException e) {
                return false;
            }
        }
        return false;
    }

    private void injectWriteJson(MethodDeclaration mainMethod) {
        if(mainMethod.getBody().isPresent()) {
            Statement writeJSONStatment = createWriteJsonStatement();
            BlockStmt body = mainMethod.getBody().get();
            body.addStatement(writeJSONStatment);
        } else {
            throw new RuntimeException("Error injecting WriteJson call");
        }
    }

    private Statement createWriteJsonStatement() {
        Expression scope = new NameExpr(new SimpleName("Analyzer"));
        Expression writeJSONExpression = new MethodCallExpr(scope, new SimpleName("writeJSON"));
        return new ExpressionStmt(writeJSONExpression);
    }

    private void injectAnalyzer(MethodCallExpr expression) {
        System.out.print("\t\tInjecting '" + expression + "'" + " <- ");
        // assuming an expression has to be within a block statement
        BlockStmt blockStmt = findParentBlockStmt(expression);
        // get the Expression statement
        ExpressionStmt expressionStmt = getExpressionStatement(expression);
        // find the index of that statement within the block
        int indexOfExpression = indexOfStatement(blockStmt, expressionStmt);
        // create the analyze statement
        Statement analyzeStmt = createAnalyzeStatement(expression);
        // insert the new analyze statement to the block immediately after the expression
        blockStmt.addStatement(indexOfExpression + 1, analyzeStmt);
        System.out.println("'" + analyzeStmt + "'");
    }

    private Statement createAnalyzeStatement(MethodCallExpr expression) {
        Expression scope = new NameExpr(new SimpleName("Analyzer"));
        NodeList<Expression> args = new NodeList<>();
        args.add(getObjectForAnalyzer(expression));
        Expression analyzeExpression = new MethodCallExpr(scope, new SimpleName("analyze"), args);
        return new ExpressionStmt(analyzeExpression);
    }

    private Expression getObjectForAnalyzer(MethodCallExpr expression) {
        if(expression.getScope().isPresent()) {
            return expression.getScope().get();
        }
        throw new RuntimeException("Expression is missing scope: " + expression);
    }

    private BlockStmt findParentBlockStmt(Expression expression) {
        Optional<BlockStmt> blockStmt = expression.findAncestor(BlockStmt.class);
        if(blockStmt.isPresent()) {
            return blockStmt.get();
        } else {
            throw new RuntimeException("Could not find parent block statement for expression: " + expression);
        }
    }

    private ExpressionStmt getExpressionStatement(Expression expression) {
        Optional<Node> parentNode = expression.getParentNode();
        if(parentNode.isPresent()) {
            return (ExpressionStmt) parentNode.get();
        } else {
            throw new RuntimeException("Could not get expression statement for expression: " + expression);
        }
    }

    private int indexOfStatement(BlockStmt block, Statement statement) {
        return block.getStatements().indexOf(statement);
    }

    private void injectInitializerExpression(VariableDeclarator declarator) {
        if (declarator.getInitializer().isEmpty()) {
            return;
        }
        System.out.print("\t\tChanging '" + declarator + "' to ");
        MethodCallExpr assign = createAnalyzeAssignExpression();
        assign.addArgument("null");
        assign.addArgument(declarator.getInitializer().get());
        declarator.setInitializer(assign);
        System.out.println("'" + declarator + "'");
    }

    private void injectAssignExpression(AssignExpr expression) {
        System.out.print("\t\tChanging '" + expression + "' to ");
        MethodCallExpr assign = createAnalyzeAssignExpression();
        assign.addArgument(expression.getTarget());
        assign.addArgument(expression.getValue());
        expression.setValue(assign);
        System.out.println("'" + expression + "'");
    }

    private MethodCallExpr createAnalyzeAssignExpression() {
        MethodCallExpr assign = new MethodCallExpr("assign");
        assign.setScope(new NameExpr("Analyzer"));
        return assign;
    }

    private void addImportStatements() {
        // TODO: implement Tarik
    }

    /**
     * Supports Lists and Map for now.
     * */
    private List<String> getModifyingMethods(VariableDeclarator variable) {
        String type = variable.getTypeAsString();
        if (type.matches("List<.*>")) {
            return Arrays.asList("add", "addAll", "remove", "removeAll", "removeIf",
                                 "retainAll", "replaceAll", "set", "clear", "sort");
        } else if (type.matches("Map<.*>")) {
            return Arrays.asList("put", "putAll", "replace", "replaceAll", "clear",
                                 "remove", "compute", "computeIfAbsent", "computeIfPresent",
                                 "putIfAbsent", "merge");
        } else {
            throw new UnsupportedFieldType();
        }
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

        instrumentProject(classes, field);
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
        for (CompilationUnit c : compilationUnits) {
            String filePath = "instrumented";
            if (c.getPackageDeclaration().isPresent()) {
                filePath += "/" + c.getPackageDeclaration().get().getNameAsString().replace('.', '/');
            }
            new File(filePath).mkdirs();
            filePath += "/" + c.getType(0).getNameAsString() + ".java";
            FileWriter output = null;
            try {
                new File(filePath).createNewFile();
                System.out.println(filePath);
                output = new FileWriter(filePath);
                output.write(c.toString());
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
