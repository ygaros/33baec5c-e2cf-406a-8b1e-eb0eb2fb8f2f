package jakub.jureczko.caves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Solution {
    private final Graph graph;

    public Solution(Graph graph){
        this.graph = graph;
    }

    public int[] calculate(int startingPoint){
        List<Path> paths = new ArrayList<>();
        List<Integer> alreadyVisited = new ArrayList<>();
        alreadyVisited.add(startingPoint);
        int hardPathsCounter = 0;
        nextSteps(startingPoint, startingPoint, alreadyVisited, hardPathsCounter, paths);
        return selectTheBestPath(paths, startingPoint);

    }
    private int[] selectTheBestPath(List<Path> paths, int startingPoint){
        int[] availableFinishes = this.graph.getNodesConnectedTo(startingPoint);
        return paths.stream()
                .sorted(Comparator.comparingInt(p -> p.hardPathsCounter))
                .filter(p -> Arrays.stream(availableFinishes).anyMatch(a -> a == p.path.get(p.path.size() - 1)))
                .findFirst().orElseThrow(RuntimeException::new)
                .path.stream()
                .mapToInt(a -> a).toArray();
    }
    private Path nextSteps(int startingPoint, int current, List<Integer> alreadyVisited, int hardPaths, List<Path> paths){
        int[] connectedNodesIndexes = graph.getNodesConnectedTo(current);
        for (int connectedNodesIndex : connectedNodesIndexes) {
            if (alreadyVisited.contains(connectedNodesIndex) || connectedNodesIndex == startingPoint){
                continue;
            }
            int newHardPaths = hardPaths + graph.getEdgeWeight(current, connectedNodesIndex);
            List<Integer> tempList = new ArrayList<>(alreadyVisited);
            tempList.add(connectedNodesIndex);
            Path calculatedPath = nextSteps(startingPoint, connectedNodesIndex, tempList, newHardPaths, paths);
            if (calculatedPath.path.size() > graph.getNodeCount() - 1){
                paths.add(calculatedPath);
            }
        }
        return new Path(alreadyVisited, hardPaths);
    }
    static class Path{
        public List<Integer> path;
        public Integer hardPathsCounter;
        Path(List<Integer> path, Integer hardPathsCounter){
            this.path = path;
            this.hardPathsCounter = hardPathsCounter;
        }

        @Override
        public String toString() {
            return "Path{" +
                    "path=" + path +
                    ", hardPathsCounter=" + hardPathsCounter +
                    '}';
        }
    }
}
