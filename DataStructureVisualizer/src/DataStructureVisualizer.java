import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import exceptions.InvaildDataStructureException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataStructureVisualizer {
    public static void main(String[] args) throws IOException {
        String sourceDirectoryPath;
        String className;
        String fieldName;

        try {
            sourceDirectoryPath = args[0];
            className = args[1];
            fieldName = args[2];
        } catch (RuntimeException e) {
            throw new InvaildDataStructureException("Missing or incorrect input", e);
        }

        File sourceDirectoryFile = new File(sourceDirectoryPath);
        SourceRoot sourceRoot = new SourceRoot(sourceDirectoryFile.toPath());
        List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();

        List<CompilationUnit> allCus = new ArrayList<>();
        for(ParseResult<CompilationUnit> parseResult : parseResults) {
            if(!parseResult.isSuccessful()) {
                throw new RuntimeException("Error generating AST");
            }
            if(parseResult.getResult().isPresent()) {
                allCus.add(parseResult.getResult().get());
            }
        }
        
        Instrumentor instrumentor = new Instrumentor(className, fieldName, allCus);
        instrumentor.instrument();
        instrumentor.runDynamicAnalysis();
    }
}
