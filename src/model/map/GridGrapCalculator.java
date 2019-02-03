package model.map;

import helpers.downloaded.pathfinding.grid.GridGraph;
import helpers.my.GeomerticHelper;
import model.Battle;
import model.character.Character;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class GridGrapCalculator {

    public static void regenerateGridGraph(Collection<Point> points) {
        regenerateGridGraph(Battle.getMap(), points, Battle.getCharacters());
    }

    public static void regenerateGridGraph(Map map, Collection<Point> points, List<Character> characters) {
        GridGraph gridGraph = map.getGridGraph();

        MapPiece mapPiece;
        int x, y;
        for (Point point: points) {
            mapPiece = map.getPoints().get(point);
            blockGridPoints(gridGraph, point, !mapPiece.isWalkable());
        }
        for (Character character: characters) {
            for (Point point: GeomerticHelper.pointsInRadius(character.getPosition(), 3, map)) {
                blockGridPoints(gridGraph, point, true);
            }
        }
        map.setGridGraph(gridGraph);
    }

    public static void clearGridGraphForChar(Character character) {
        GridGraph gridGraph = Battle.getMap().getGridGraph();
        clearGridGraphForPoints(GeomerticHelper.pointsInRadius(character.getPosition(), 3, Battle.getMap()),
                true);
        Battle.getMap().setGridGraph(gridGraph);
    }

    public static void clearGridGraphForPoints(List<Point> points, boolean respectWalkability) {
        GridGraph gridGraph = Battle.getMap().getGridGraph();

        for (Point point: points) {
            if (!respectWalkability || Battle.getMap().getPoints().get(point).isWalkable())
                blockGridPoints(gridGraph, point, false);
        }

        Battle.getMap().setGridGraph(gridGraph);
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    private static void blockGridPoints(GridGraph gridGraph, Point p, boolean block) {
        int x = p.x, y = p.y;
        gridGraph.trySetBlocked(x*4 - 1, y*4 + 2, block);
        gridGraph.setBlocked(x*4 + 0, y*4 + 1, block);
        gridGraph.setBlocked(x*4 + 0, y*4 + 2, block);
        gridGraph.trySetBlocked(x*4 + 1, y*4 - 1, block);
        gridGraph.setBlocked(x*4 + 1, y*4 + 0, block);
        gridGraph.setBlocked(x*4 + 1, y*4 + 1, block);
        gridGraph.setBlocked(x*4 + 1, y*4 + 2, block);
        gridGraph.setBlocked(x*4 + 1, y*4 + 3, block);
        gridGraph.setBlocked(x*4 + 2, y*4 + 0, block);
        gridGraph.setBlocked(x*4 + 2, y*4 + 1, block);
        gridGraph.setBlocked(x*4 + 2, y*4 + 2, block);
        gridGraph.setBlocked(x*4 + 2, y*4 + 3, block);
        gridGraph.trySetBlocked(x*4 + 2, y*4 + 4, block);
        gridGraph.setBlocked(x*4 + 3, y*4 + 1, block);
        gridGraph.setBlocked(x*4 + 3, y*4 + 2, block);
        gridGraph.trySetBlocked(x*4 + 4, y*4 + 1, block);
    }
}
