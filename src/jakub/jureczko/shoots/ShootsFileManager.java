package jakub.jureczko.shoots;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static jakub.jureczko.shoots.Solution.WHITE;

public class ShootsFileManager {
    private final String INPUT_FILE = "SHO.IN";
    private final String OUTPUT_FILE = "SHO.OUT";
    private final File file;

    public ShootsFileManager(){
        this.file = new File(INPUT_FILE);
    }
    public List<int[][]> readBoxesFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(this.file))){
            String line = reader.readLine();
            int boxCount = Integer.parseInt(line);
            List<int[][]> boxes = new ArrayList<>(boxCount);
            for (int i = 0; i < boxCount; i++) {
                line = reader.readLine();
                String[] split = line.split(" ");
                int rows = Integer.parseInt(split[0]);
                int cols = Integer.parseInt(split[1]);
                int[][] box = new int[rows][cols];
                for (int j = 0; j < cols; j++) {
                    line = reader.readLine();
                    split = line.split(" ");
                    int first = Integer.parseInt(split[0]);
                    int second = Integer.parseInt(split[1]);
                    box[first - 1][j] = WHITE;
                    box[second - 1][j] = WHITE;
                }
                boxes.add(box);
            }
            return boxes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean saveResultsToFile(List<String> results){
        try(PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_FILE))){
            results.forEach(writer::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
