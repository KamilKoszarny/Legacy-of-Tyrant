package helpers.downloaded.pathfinding.main;

import helpers.downloaded.pathfinding.algorithms.PathFindingAlgorithm;
import helpers.downloaded.pathfinding.grid.GridGraph;

public interface AlgoFunction {
    public abstract PathFindingAlgorithm getAlgo(GridGraph gridGraph, int sx, int sy, int ex, int ey);
}