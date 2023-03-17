package jakub.jureczko.caves;


public class Main {
    public static void main(String[] args) {
        CaveFileManager fileManager = new CaveFileManager();
        Graph graph = fileManager.readGraphFromFile();

        Solution solution = new Solution(graph);
        int[] result = solution.calculate(1);

        fileManager.saveResultToFile(result);
    }
}