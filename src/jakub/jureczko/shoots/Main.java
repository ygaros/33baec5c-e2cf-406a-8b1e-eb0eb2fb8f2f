package jakub.jureczko.shoots;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ShootsFileManager manager = new ShootsFileManager();
        List<int[][]> data = manager.readBoxesFromFile();

        Solution solution = new Solution(data);
        List<String> results = solution.calculate();

        manager.saveResultsToFile(results);
    }
}
