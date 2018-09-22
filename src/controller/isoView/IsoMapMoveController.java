package controller.isoView;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import model.IsoBattleLoop;

import java.awt.*;
import java.util.List;


public class IsoMapMoveController {

    private Canvas mapCanvas;
    private List<Canvas> borderCanvases;
    private IsoBattleLoop isoBattleLoop;
    private static final int MAP_MOVE_BOUNDARY = 10;
    public static final int MAP_MOVE_STEP = 5;

    IsoMapMoveController(Canvas mapCanvas, List<Canvas> borderCanvases, IsoBattleLoop isoBattleLoop) {
        this.mapCanvas = mapCanvas;
        this.borderCanvases = borderCanvases;
        this.isoBattleLoop = isoBattleLoop;
        initialize();
    }

    private void initialize(){
        initMapMoving();
    }

    private void initMapMoving(){
        setBorderCanvasesPos();

        for (Canvas borderCanvas: borderCanvases) {
            borderCanvas.setOnMouseEntered(mouseEvent -> {
                isoBattleLoop.changeMapMove(moveByBorder(borderCanvas));
                isoBattleLoop.setMapMoveFlag(true);
            });
        }
        mapCanvas.setOnMouseEntered(mouseEvent -> {
            isoBattleLoop.resetMapMove(new Point(0, 0));
            isoBattleLoop.setMapMoveFlag(false);
        });
    }

    private void setBorderCanvasesPos(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        borderCanvases.get(0).setLayoutX(0);
        borderCanvases.get(0).setLayoutY(0);
        borderCanvases.get(0).setWidth(screenSize.getWidth());
        borderCanvases.get(0).setHeight(MAP_MOVE_BOUNDARY);
        borderCanvases.get(1).setLayoutX(screenSize.getWidth() - MAP_MOVE_BOUNDARY);
        borderCanvases.get(1).setLayoutY(0);
        borderCanvases.get(1).setWidth(MAP_MOVE_BOUNDARY);
        borderCanvases.get(1).setHeight(screenSize.getHeight());
        borderCanvases.get(2).setLayoutX(0);
        borderCanvases.get(2).setLayoutY(screenSize.getHeight() - MAP_MOVE_BOUNDARY);
        borderCanvases.get(2).setWidth(screenSize.getWidth());
        borderCanvases.get(2).setHeight(MAP_MOVE_BOUNDARY);
        borderCanvases.get(3).setLayoutX(0);
        borderCanvases.get(3).setLayoutY(0);
        borderCanvases.get(3).setWidth(MAP_MOVE_BOUNDARY);
        borderCanvases.get(3).setHeight(screenSize.getHeight());

        borderCanvases.get(4).setLayoutX(screenSize.getWidth() - MAP_MOVE_BOUNDARY);
        borderCanvases.get(4).setLayoutY(0);
        borderCanvases.get(4).setWidth(MAP_MOVE_BOUNDARY);
        borderCanvases.get(4).setHeight(MAP_MOVE_BOUNDARY);
        borderCanvases.get(5).setLayoutX(screenSize.getWidth() - MAP_MOVE_BOUNDARY);
        borderCanvases.get(5).setLayoutY(screenSize.getHeight() - MAP_MOVE_BOUNDARY);
        borderCanvases.get(5).setWidth(MAP_MOVE_BOUNDARY);
        borderCanvases.get(5).setHeight(MAP_MOVE_BOUNDARY);
        borderCanvases.get(6).setLayoutX(0);
        borderCanvases.get(6).setLayoutY(screenSize.getHeight() - MAP_MOVE_BOUNDARY);
        borderCanvases.get(6).setWidth(MAP_MOVE_BOUNDARY);
        borderCanvases.get(6).setHeight(MAP_MOVE_BOUNDARY);
        borderCanvases.get(7).setLayoutX(0);
        borderCanvases.get(7).setLayoutY(0);
        borderCanvases.get(7).setWidth(MAP_MOVE_BOUNDARY);
        borderCanvases.get(7).setHeight(MAP_MOVE_BOUNDARY);
    }

    private Point moveByBorder(Canvas borderCanvas){
        Point move = new Point(0, 0);
        if (borderCanvases.get(0) == borderCanvas)
            move.y = MAP_MOVE_STEP;
        else if (borderCanvases.get(1) == borderCanvas)
            move.x = -MAP_MOVE_STEP;
        else if(borderCanvases.get(2) == borderCanvas)
            move.y = -MAP_MOVE_STEP;
        else if (borderCanvases.get(3) == borderCanvas)
            move.x = MAP_MOVE_STEP;

        else if (borderCanvases.get(4) == borderCanvas) {
            move.x = -MAP_MOVE_STEP;
            move.y = MAP_MOVE_STEP;
        } else if (borderCanvases.get(5) == borderCanvas) {
            move.x = -MAP_MOVE_STEP;
            move.y = -MAP_MOVE_STEP;
        } else if (borderCanvases.get(6) == borderCanvas) {
            move.x = MAP_MOVE_STEP;
            move.y = -MAP_MOVE_STEP;
        } else if (borderCanvases.get(7) == borderCanvas) {
            move.x = MAP_MOVE_STEP;
            move.y = MAP_MOVE_STEP;
        }

        return move;
    }
}
