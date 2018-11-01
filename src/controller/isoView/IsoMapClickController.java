package controller.isoView;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.IsoBattleLoop;
import model.character.Character;

import java.awt.*;
import java.util.List;

public class IsoMapClickController {


    private Canvas mapCanvas;
    private IsoBattleLoop isoBattleLoop;

    IsoMapClickController(Canvas mapCanvas, IsoBattleLoop isoBattleLoop, List<Character> characters) {
        this.mapCanvas = mapCanvas;
        this.isoBattleLoop = isoBattleLoop;
    }

    void initialize(){
        initCanvasClick();
    }

    private void initCanvasClick(){
        mapCanvas.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                isoBattleLoop.setCanvasLClickPoint(new Point((int) mouseEvent.getX(), (int) mouseEvent.getY()));
                isoBattleLoop.setCanvasLClickFlag(true);
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                isoBattleLoop.setCanvasRClickPoint(new Point((int) mouseEvent.getX(), (int) mouseEvent.getY()));
                isoBattleLoop.setCanvasRClickFlag(true);
            }
        });
    }
}
