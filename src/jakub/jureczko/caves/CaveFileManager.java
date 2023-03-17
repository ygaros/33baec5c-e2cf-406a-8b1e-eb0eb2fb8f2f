package jakub.jureczko.caves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CaveFileManager {

    private final String INPUT_FILE = "CAV.IN";
    private final String OUTPUT_FILE = "CAV.OUT";
    private final File file;

    public CaveFileManager(){
        this.file = new File(INPUT_FILE);
    }
    public Graph readGraphFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(this.file))){
            Graph graph = new GraphImpl();
            String line;
            while((line = reader.readLine()) != null){
                String[] split = line.split(" ");
                if (split.length == 2){
                    int roomCount = Integer.parseInt(split[0]);
                    int outsideRoomCount = Integer.parseInt(split[1]);
                    for (int i = 0; i < roomCount; i++) {
                        graph.addNode(String.valueOf(i + 1), Integer.MAX_VALUE, i + 1 <= outsideRoomCount);
                    }
                }else{
                    graph.addEdge(
                            Integer.parseInt(split[0]) - 1,
                            Integer.parseInt(split[1]) - 1,
                            Integer.parseInt(split[2])
                    );

                }
            }
            return graph;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean saveResultToFile(int[] results){
        try(PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_FILE))){
            writer.println(
                    Arrays.stream(results)
                            .boxed()
                            .map(String::valueOf)
                            .collect(Collectors.joining(" "))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
