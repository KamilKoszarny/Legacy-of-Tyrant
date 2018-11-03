package helpers.downloaded.pathfinding.myTests;

import helpers.downloaded.pathfinding.grid.GridGraph;
import helpers.downloaded.pathfinding.main.utility.Utility;

public class Example {
    public static void main(String[] args) {
//        GridGraph gridGraph = new GridGraph(5, 5);
        GridGraph gridGraph = new GridGraph(5, 5);
        gridGraph.setBlocked(2,1,true);
        gridGraph.setBlocked(2,2,true);
        gridGraph.setBlocked(2,3,true);

        int[][] path = Utility.computeOptimalPathOnline(gridGraph, 0, 2, 4, 2);
        for (int i = 0; i < path.length; i++) {
            System.out.println(path[i][0] + " " + path[i][1]);
        }


        Utility.cleanUpPreallocatedMemory();
        System.out.println();
//        gridGraph = new GridGraph(5, 5);
        gridGraph.setBlocked(2,1,false);
        gridGraph.setBlocked(2,2,false);
        gridGraph.setBlocked(2,3,false);

        path = Utility.computeOptimalPathOnline(gridGraph, 0, 2, 4, 2);
        for (int i = 0; i < path.length; i++) {
            System.out.println(path[i][0] + " " + path[i][1]);
        }
    }
}
