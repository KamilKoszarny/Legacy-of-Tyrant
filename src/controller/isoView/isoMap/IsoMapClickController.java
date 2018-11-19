package controller.isoView.isoMap;

import controller.isoView.isoPanel.ItemMoveController;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.IsoBattleLoop;
import model.character.Character;

import java.awt.*;
import java.util.List;

public class IsoMapClickController {


    private IsoBattleLoop isoBattleLoop;
    private Canvas mapCanvas;
    private static List<Rectangle> itemRectangles;

    public IsoMapClickController(Canvas mapCanvas, IsoBattleLoop isoBattleLoop, List<Character> characters) {
        this.mapCanvas = mapCanvas;
        this.isoBattleLoop = isoBattleLoop;
    }

    public void initialize(){
        initCanvasClick();
        initItemClick();
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

    private void initItemClick() {
        for (Rectangle rectangle: itemRectangles) {
            rectangle.setOnMouseClicked(mouseEvent -> {
                isoBattleLoop.setClickedItemNo(itemRectangles.indexOf(rectangle));
                isoBattleLoop.setClickedItemPoint(new Point((int)mouseEvent.getX(), (int)mouseEvent.getY()));
                isoBattleLoop.setItemClickFlag(true);
            });
        }
    }

    public static void setItemRectangles(List<Rectangle> itemRectangles) {
        IsoMapClickController.itemRectangles = itemRectangles;
    }
}
