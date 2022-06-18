import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LineParser {
    BufferedReader bufferedReader;
    List<String> lines;

    public LineParser(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
        lines = bufferedReader.lines().toList();
        bufferedReader.close();
    }

    public int getNumberOfLines() {
        if(lines == null) {
            return 0;
        }
        return lines.size();
    }

    public String getLine(int lineNumber) {
        return lines.get(lineNumber - 1);
    }

    // gets a range of lines from the file from start (inclusive) -> end (inclusive)
    public List<String> getLines(int start, int end) {
        start = Math.max(start, 1);
        end = Math.min(end, getNumberOfLines());

        List<String> rangeOfLines = new ArrayList<>();
        for(int i = start; i <= end; i++) {
            rangeOfLines.add(getLine(i));
        }
        return rangeOfLines;
    }

}
