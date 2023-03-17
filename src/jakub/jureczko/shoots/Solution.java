package jakub.jureczko.shoots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    private static final int MARKER = 5;
    public static final int WHITE = 1;
    private final List<int[][]> boxes;

    public Solution(List<int[][]> boxes){
        this.boxes = boxes;
    }
    public List<String> calculate(){
        List<String> results = new ArrayList<>(this.boxes.size());
        this.boxes.forEach(data -> {
            try{
                int[] chosenCols = new int[data[0].length];
                Arrays.fill(chosenCols, -1);
                boolean returnValue = this.solveBox(data, chosenCols);
                if (returnValue){
                    results.add(Arrays.stream(chosenCols).map(i -> i + 1).boxed().map(String::valueOf).collect(Collectors.joining(" ")));
                }else{
                    results.add("NO");
                }
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        });
        return results;
    }
    private boolean checkBox(int[][] box){
        return Arrays.stream(box).allMatch(f -> {
            for (int i : f) {
                if (i == MARKER){
                    return true;
                }
            }
            return false;
        });
    }
    private boolean solveBox(int[][]box, int[] colsChecked){
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[i].length; j++) {
                if (box[i][j] == WHITE) {
                    if (colsChecked[j] == -1) {
                        box[i][j] = MARKER;
                        colsChecked[j] = i;
                        if (solveBox(box, colsChecked)) {
                            return true;
                        } else {
                            box[i][j] = WHITE;
                            colsChecked[j] = -1;
                        }
                    }
                }
            }
        }
        return checkBox(box);
    }
}
