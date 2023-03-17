package jakub.jureczko.caves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GraphImpl implements Graph{

    private final List<GraphNode> nodes;
    private GraphEdge[][] edges;

    public GraphImpl(){
        this.nodes = new ArrayList<>();
        this.edges = new GraphEdge[0][0];
    }
    @Override
    public void addNode(String name, int weight, boolean inside) {
        if (validateNodeName(name)){
            throw new IllegalArgumentException("name must be unique");
        }
        GraphNode newNode = new GraphNode(name, weight, inside);
        this.nodes.add(newNode);
        resizeGraphEdges();
    }

    private void resizeGraphEdges() {
        GraphEdge[][] newEdges = new GraphEdge[this.nodes.size()][this.nodes.size()];
        for (int i = 0; i < this.nodes.size() - 1; i++) {
            System.arraycopy(this.edges[i], 0, newEdges[i], 0, this.nodes.size() - 1);
        }
        this.edges = newEdges;
    }

    private boolean validateNodeName(String name){
        return this.nodes.stream().anyMatch(n -> n.name().equalsIgnoreCase(name));
    }
    @Override
    public void addEdge(int from, int to, int weight) {
        if (from >= this.nodes.size() || to >= this.nodes.size()){
            throw new IllegalArgumentException("invalid node indexes");
        }
        GraphEdge edge = new GraphEdge(weight);
        this.edges[from][to] = edge;
        this.edges[to][from] = edge;
    }

    @Override
    public int getNodeCount() {
        return this.nodes.size();
    }

    @Override
    public int getNodeIndex(String name) {
        for (int i = 0; i < this.nodes.size(); i++) {
            if (this.nodes.get(i).name().equalsIgnoreCase(name)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getEdgeWeight(int from, int to) {
        return this.edges[this.getNodeIndex(String.valueOf(from))][this.getNodeIndex(String.valueOf(to))].weight();
    }

    @Override
    public int[] getNodesConnectedTo(int nodeIndex) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < this.edges[nodeIndex - 1].length; i++) {
            GraphEdge edge = this.edges[nodeIndex - 1][i];
            if(edge != null){
                result.add(Integer.parseInt(nodes.get(i).name()));
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }
    private record GraphEdge(int weight) {
            @Override
            public String toString() {
                return "GraphEdge{" +
                        "weight=" + weight +
                        '}';
            }
    }
    private record GraphNode(String name, int weight, boolean inside) {
            @Override
            public String toString() {
                return "GraphNode{" +
                        "name='" + name + '\'' +
                        ", weight=" + weight +
                        ", inside=" + inside +
                        '}';
            }
    }
    @Override
    public String toString() {
        return "GraphImpl{" +
                "nodes=" + nodes +
                ", edges=" + Arrays.stream(edges).flatMap(Arrays::stream).filter(Objects::nonNull).map(GraphEdge::toString).count() +
                '}';
    }
}
