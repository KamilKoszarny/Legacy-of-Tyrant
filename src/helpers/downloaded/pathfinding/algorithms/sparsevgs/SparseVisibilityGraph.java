package helpers.downloaded.pathfinding.algorithms.sparsevgs;

import helpers.downloaded.pathfinding.grid.GridGraph;

import java.util.Arrays;

public class SparseVisibilityGraph {

    private static SparseVisibilityGraph storedVisibilityGraph;
    private static GridGraph storedGridGraph;
    
    private final GridGraph graph;
    private LineOfSightScannerDouble losScanner;
    private final int sizeXPlusOne;
    private final int sizeYPlusOne;

    private int[] nodeIndex; // Flattened 2D Array
    private int startIndex = -1;
    private int endIndex = -1;
    
    private Runnable saveSnapshot;

    private int originalSize;
    private int maxSize;
    private SVGNode[] nodes;
    private int nNodes;

    private int[] startStoredNeighbours;
    private float[] startStoredWeights;
    private int startStoredNumNeighbours;
    private int[] endStoredNeighbours;
    private float[] endStoredWeights;
    private int endStoredNumNeighbours;

    public SparseVisibilityGraph(GridGraph graph) {
        this.graph = graph;
        this.sizeXPlusOne = graph.sizeX+1;
        this.sizeYPlusOne = graph.sizeY+1;
    }

    public final void setSaveSnapshotFunction(Runnable saveSnapshot) {
        this.saveSnapshot = saveSnapshot;
    }

    public final void initialise(int sx, int sy, int ex, int ey) {
        // Check if graph already initialised
        if (nodes == null) {
            long _st = System.nanoTime();
            losScanner = new LineOfSightScannerDouble(graph);
            
            nodes = new SVGNode[11];
            nNodes = 0;
            
            addNodes();
            
            originalSize = nNodes;
            maxSize = nNodes + 2;
            nodes = Arrays.copyOf(nodes, maxSize);
            
            addAllEdges();
            
            long _ed = System.nanoTime();
            System.out.println("Construction Time: " + (_ed-_st)/1000000.);
        }

        restoreOriginalGraph();
        addStartAndEnd(sx,sy,ex,ey);
    }

    private final void addNodes() {
        nodeIndex = new int[sizeYPlusOne*sizeXPlusOne];
        for (int y=0; y<sizeYPlusOne; y++) {
            for (int x=0; x<sizeXPlusOne; x++) {
                if (graph.isOuterCorner(x, y)) {
                    nodeIndex[y*sizeXPlusOne + x] = assignNode(x, y);
                } else {
                    nodeIndex[y*sizeXPlusOne + x] = -1;
                }
            }
        }
    }

    private final int assignNode(int x, int y) {
        int index = nNodes;
        if (index >= nodes.length) nodes = Arrays.copyOf(nodes, nodes.length*2);
        nodes[index] = new SVGNode(x,y);
        nNodes++;
        return index;
    }
    
    private final void restoreOriginalGraph() {
        // Safeguard against multiple restores.
        if (startIndex == -1) return;
        
        // Reset hasEdgeToGoal array.
        markHasEdgeToGoal(false);
        // PostCondition: hasEdgeToGoal has all values == false.
        if (endIndex >= originalSize) {
            SVGNode e = nodes[endIndex];
            nodeIndex[e.y*sizeXPlusOne + e.x] = -1;
        } else {
            SVGNode endNode = nodes[endIndex];
            endNode.outgoingEdges = endStoredNeighbours;
            endNode.edgeWeights = endStoredWeights;
            endNode.nEdges = endStoredNumNeighbours;
            endStoredNeighbours = null;
            endStoredWeights = null;
        }
        
        if (startIndex >= originalSize) {
            SVGNode s = nodes[startIndex];
            nodeIndex[s.y*sizeXPlusOne + s.x] = -1;
        } else {
            SVGNode startNode = nodes[startIndex];
            startNode.outgoingEdges = startStoredNeighbours;
            startNode.edgeWeights = startStoredWeights;
            startNode.nEdges = startStoredNumNeighbours;
            startStoredNeighbours = null;
            startStoredWeights = null;
        }

        // Reset size.
        nNodes = originalSize;
        
        startIndex = -1;
        endIndex = -1;
    }

    public final void addStartAndEnd(int sx, int sy, int ex, int ey) {
        
        // START:
        if (nodeIndex[sy*sizeXPlusOne + sx] == -1) {
            startIndex = nNodes;
            ++nNodes;
            
            nodeIndex[sy*sizeXPlusOne + sx] = startIndex;
            nodes[startIndex] = new SVGNode(sx, sy);
        } else {
            startIndex = nodeIndex[sy*sizeXPlusOne + sx];
            SVGNode startNode = nodes[startIndex];
            startStoredNeighbours = startNode.outgoingEdges;
            startStoredWeights = startNode.edgeWeights;
            startStoredNumNeighbours = startNode.nEdges;
            startNode.outgoingEdges = new int[11];
            startNode.edgeWeights = new float[11];
            startNode.nEdges = 0;
        }
        addEdgesToVisibleNeighbours(startIndex, sx, sy);
        
        // END:
        if (nodeIndex[ey*sizeXPlusOne + ex] == -1) {
            endIndex = nNodes;
            ++nNodes;

            nodeIndex[ey*sizeXPlusOne + ex] = endIndex;
            nodes[endIndex] = new SVGNode(ex, ey);
            
        } else {
            endIndex = nodeIndex[ey*sizeXPlusOne + ex];
            SVGNode endNode = nodes[endIndex];
            endStoredNeighbours = endNode.outgoingEdges;
            endStoredWeights = endNode.edgeWeights;
            endStoredNumNeighbours = endNode.nEdges;
            endNode.outgoingEdges = new int[11];
            endNode.edgeWeights = new float[11];
            endNode.nEdges = 0;
        }
        addEdgesToVisibleNeighbours(endIndex, ex, ey);
        
        // Mark all hasEdgeToGoal vertices.
        markHasEdgeToGoal(true);
    }

    private final int addEdgesToVisibleNeighbours(int index, int x, int y) {
        losScanner.computeAllVisibleTautSuccessors(x, y);
        int nSuccessors = losScanner.nSuccessors;
        for (int i=0;i<nSuccessors;++i) {
            int toX = losScanner.successorsX[i];
            int toY = losScanner.successorsY[i];
            int targetIndex = nodeIndex[toY*sizeXPlusOne + toX];

            float weight = graph.distance(x, y, toX, toY);
            addEdge(index, targetIndex, weight);
        }
        
        return index;
    }

    private final void markHasEdgeToGoal(boolean value) {
        int[] endNeighbours = nodes[endIndex].outgoingEdges;
        int n = nodes[endIndex].nEdges;
        for (int i=0;i<n;++i) {
            nodes[endNeighbours[i]].hasEdgeToGoal = value;
        }
    }
    
    private final void addAllEdges() {
        int fromX, fromY, toX, toY;
        for (int i=0;i<nNodes;++i) {
            fromX = xCoordinateOf(i);
            fromY = yCoordinateOf(i);
            
            losScanner.computeAllVisibleTwoWayTautSuccessors(fromX, fromY);
            int nSuccessors = losScanner.nSuccessors;
            for (int j=0;j<nSuccessors;++j) {
                toX = losScanner.successorsX[j];
                toY = losScanner.successorsY[j];
                int toIndex = nodeIndex[toY*sizeXPlusOne + toX];

                float weight = graph.distance(fromX, fromY, toX, toY);
                addEdge(i, toIndex, weight);
            }
        }
    }
    
    private final void addEdge(int fromI, int toI, float weight) {
        nodes[fromI].addEdge(toI, weight);
    }
    
    public final int xCoordinateOf(int index) {
        return nodes[index].x;
    }

    public final int yCoordinateOf(int index) {
        return nodes[index].y;
    }

    public final int size() {
        return nNodes;
    }
    
    public final int maxSize() {
        return maxSize;
    }
    
    public final int computeSumDegrees() {
        int sum = 0;
        for (int i=0;i<nNodes;++i) {
            sum += nodes[i].nEdges;
        }
        return sum;
    }

    public final SVGNode getOutgoingEdges(int source) {
        return nodes[source];
    }

    public final int startNode() {
        return startIndex;
    }

    public final int endNode() {
        return endIndex;
    }
    
    private final void maybeSaveSnapshot() {
        if (saveSnapshot != null) saveSnapshot.run();
    }
    
    
    private static final SparseVisibilityGraph repurpose(SparseVisibilityGraph oldGraph) {
        SparseVisibilityGraph newGraph = new SparseVisibilityGraph(oldGraph.graph);
        newGraph.nodeIndex = oldGraph.nodeIndex;
        newGraph.startIndex = oldGraph.startIndex;
        newGraph.endIndex = oldGraph.endIndex;
        newGraph.nodes = oldGraph.nodes;
        newGraph.nNodes = oldGraph.nNodes;
        newGraph.originalSize = oldGraph.originalSize;
        newGraph.maxSize = oldGraph.maxSize;
        newGraph.losScanner = oldGraph.losScanner;

        newGraph.startStoredNeighbours = oldGraph.startStoredNeighbours;
        newGraph.startStoredWeights = oldGraph.startStoredWeights;
        newGraph.startStoredNumNeighbours = oldGraph.startStoredNumNeighbours;
        newGraph.endStoredNeighbours = oldGraph.endStoredNeighbours;
        newGraph.endStoredWeights = oldGraph.endStoredWeights;
        newGraph.endStoredNumNeighbours = oldGraph.endStoredNumNeighbours;
        
        return newGraph;
    }
    
    public static final SparseVisibilityGraph getStoredGraph(GridGraph graph) {
        SparseVisibilityGraph visibilityGraph = null;
        if (storedGridGraph != graph || storedVisibilityGraph == null) {
            //("Get new graph");
            visibilityGraph = new SparseVisibilityGraph(graph);
            storedVisibilityGraph = visibilityGraph;
            storedGridGraph = graph;
        } else {
            //("Reuse graph");
            visibilityGraph = repurpose(storedVisibilityGraph);
            storedVisibilityGraph = visibilityGraph;
        }
        return visibilityGraph;
    }

    private final void printAllEdges() {
        for (int i=0;i<nNodes;++i) {
            SVGNode node = nodes[i];
            System.out.println("Node " + i + " : " + node.nEdges + " edges");
            for (int j=0;j<node.nEdges;++j) {
                System.out.println(i + " -> " + node.outgoingEdges[j]);
            }
        }
    }

    public static void clearMemory() {
        storedVisibilityGraph = null;
        storedGridGraph = null;
        System.gc();
    }
    
}

final class SVGNode {
    final int x;
    final int y;

    /** Invariant: If any new neighbour is added to outgoingEdges, (i.e. not in the static version)
     *             it will appear after all other outgoing edges
     *             
     * i.e. the possibilities for outgoingEdges[] are:
     *    [x x x x x x d d d]
     *                ^     ^
     *                |     '--- nEdges marker
     *                |
     *                '--- originalNEdges marker.
     *                
     *  Invariant: Within the original neighbours, the outgoing edges are sorted by increasing index.
     *             Within the new neighbours, the outgoing edges are sorted by increasing index.
     */
    int[] outgoingEdges;
    float[] edgeWeights;
    int nEdges = 0;
    
    boolean hasEdgeToGoal = false;
    
    public SVGNode(int x, int y) {
        this.x = x;
        this.y = y;
        outgoingEdges = new int[11];
        edgeWeights = new float[11];
    }

    public final void addEdge(int toIndex, float weight) {
        if (nEdges >= outgoingEdges.length) {
            outgoingEdges = Arrays.copyOf(outgoingEdges, outgoingEdges.length*2);
            edgeWeights = Arrays.copyOf(edgeWeights, edgeWeights.length*2);
        }
        outgoingEdges[nEdges] = toIndex;
        edgeWeights[nEdges] = weight;
        nEdges++;
    }
}