package jakub.jureczko.caves;

public interface Graph {
    void addNode(String name, int weight, boolean inside);
    void addEdge(int from, int to, int weight);
    int getNodeCount();
    int getNodeIndex(String name);
    int getEdgeWeight(int from, int to);
    int[] getNodesConnectedTo(int nodeIndex);
}
