package helpers.downloaded.pathfinding.main;

import algorithms.PathFindingAlgorithm;
import grid.GridGraph;

public interface AlgoFunction {
    public abstract PathFindingAlgorithm getAlgo(GridGraph gridGraph, int sx, int sy, int ex, int ey);
}