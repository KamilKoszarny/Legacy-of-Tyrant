package helpers.downloaded.pathfinding.algorithms.visibilitygraph;

import helpers.downloaded.pathfinding.algorithms.VisibilityGraphAlgorithm;
import helpers.downloaded.pathfinding.algorithms.datatypes.Point;
import helpers.downloaded.pathfinding.grid.GridGraph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BFSVisibilityGraph extends VisibilityGraphAlgorithm {

    public LinkedList<Point> path;

    public BFSVisibilityGraph(GridGraph graph, int sx, int sy, int ex, int ey) {
        super(graph, sx, sy, ex, ey);
    }

    public static BFSVisibilityGraph graphReuse(GridGraph graph, int sx, int sy, int ex, int ey) {
        BFSVisibilityGraph algo = new BFSVisibilityGraph(graph, sx, sy, ex, ey);
        algo.reuseGraph = true;
        return algo;
    }

    @Override
    public void computePath() {
        setupVisibilityGraph();
        
        int start = visibilityGraph.startNode();
        int finish = visibilityGraph.endNode();
        Queue<Integer> queue = new LinkedList<>();
        parent = new int[visibilityGraph.size()];
        visited = new boolean[visibilityGraph.size()];
        for (int i=0; i<parent.length; i++) {
            parent[i] = -1;
        }
        
        queue.offer(start);
        visited[start] = true;
        
        while (queue != null && !queue.isEmpty()) {
            int current = queue.poll();
            
            Iterator<Edge> itr = visibilityGraph.edgeIterator(current);
            while (itr.hasNext()) {
                Edge edge = itr.next();
                if (!visited[edge.dest]) {
                    visited[edge.dest] = true;
                    parent[edge.dest] = current;
                    if (edge.dest == finish) {
                        queue = null;
                        break;
                    }
                    queue.offer(edge.dest);
                }
            }
            maybeSaveSearchSnapshot();
        }
    }
}
