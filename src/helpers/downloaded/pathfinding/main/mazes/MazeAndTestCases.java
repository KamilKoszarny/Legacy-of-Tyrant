package helpers.downloaded.pathfinding.main.mazes;

import helpers.downloaded.pathfinding.grid.GridAndGoals;
import helpers.downloaded.pathfinding.grid.GridGraph;
import helpers.downloaded.pathfinding.main.testgen.StartEndPointData;

import java.util.ArrayList;

public class MazeAndTestCases {
    public final String mazeName;
    public final GridGraph gridGraph;
    public final ArrayList<StartEndPointData> problems;
    
    public MazeAndTestCases(String mazeName, GridGraph gridGraph, ArrayList<StartEndPointData> problems) {
        this.mazeName = mazeName;
        this.gridGraph = gridGraph;
        this.problems = problems;
    }
    
    public GridAndGoals gridAndGoals(int problemIndex) {
        StartEndPointData problem = problems.get(problemIndex);
        return new GridAndGoals(gridGraph, problem.start.x, problem.start.y, problem.end.x, problem.end.y);
    }
}
