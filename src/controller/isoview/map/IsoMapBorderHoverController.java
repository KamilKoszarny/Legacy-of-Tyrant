package controller.isoview.map;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.BattleEvent;
import model.EventType;
import model.IsoBattleLoop;

import java.awt.*;
import java.util.List;


public class IsoMapBorderHoverController {
    private static final int MAP_MOVE_BOUNDARY = 10;
    public static final int MAP_MOVE_STEP = 3;

    private static Canvas mapCanvas;
    private static List<Canvas> borderCanvases;
    private static HBox panel;

    public IsoMapBorderHoverController(Canvas mapCanvas, List<Canvas> borderCanvases, HBox panel) {
        IsoMapBorderHoverController.mapCanvas = mapCanvas;
        IsoMapBorderHoverController.borderCanvases = borderCanvases;
        IsoMapBorderHoverController.panel = panel;

        initMapMoving();
    }

    private void initMapMoving(){
        setBorderCanvasesPos();

        for (Canvas borderCanvas: borderCanvases) {
            borderCanvas.setOnMouseEntered(mouseEvent -> {
                IsoBattleLoop.setBattleEvent(new BattleEvent(EventType.MOVE_MAP, moveByBorder(borderCanvas)));
            });
        }
        EventHandler<MouseEvent> stopMoveEventHandler = mouseEvent -> {
            IsoBattleLoop.setBattleEvent(new BattleEvent(EventType.MOVE_MAP, new Point(0, 0)));
        };
        mapCanvas.setOnMouseEntered(stopMoveEventHandler);
        panel.setOnMouseEntered(stopMoveEventHandler);
    }

    private void setBorderCanvasesPos(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        borderCanvases.forEach(canvas -> canvas.getGraphicsContext2D().setFill(Color.GREEN));
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

    public static void borderCanvasesToFront() {
        for (Canvas canvas: borderCanvases) {
            canvas.toFront();
        }
    }
}
