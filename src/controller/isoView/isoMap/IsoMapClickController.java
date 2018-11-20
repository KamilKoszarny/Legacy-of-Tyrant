package controller.isoView.isoMap;

import controller.isoView.isoPanel.Panel;
import helpers.my.GeomerticHelper;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import model.IsoBattleLoop;
import model.character.Character;

import java.awt.*;
import java.util.List;

public class IsoMapClickController {


    private IsoBattleLoop isoBattleLoop;
    private Canvas mapCanvas;
    private Panel panel;

    public IsoMapClickController(Canvas mapCanvas, Panel panel, IsoBattleLoop isoBattleLoop, List<Character> characters) {
        this.mapCanvas = mapCanvas;
        this.panel = panel;
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
        for (Rectangle rectangle: panel.getItemRectangles()) {
            rectangle.setOnMouseClicked(mouseEvent -> {
                isoBattleLoop.setClickedItemNo(panel.getItemRectangles().indexOf(rectangle));
                isoBattleLoop.setClickedItemPoint(new Point((int)mouseEvent.getX(), (int)mouseEvent.getY()));
                isoBattleLoop.setItemCatch(true);
                isoBattleLoop.setItemClickFlag(true);
            });
        }

        panel.getCatchedItemRect().setOnMouseClicked(mouseEvent -> {
            Rectangle itemRectangle = null;
            for (Rectangle rectangle: panel.getItemRectangles()) {
                Point clickPoint = MouseInfo.getPointerInfo().getLocation();
                Rectangle screenRectangle = GeomerticHelper.screenRectangle(rectangle);
                if (screenRectangle.contains(new Point2D(clickPoint.x, clickPoint.y)))
                    itemRectangle = rectangle;
            }
            if (itemRectangle != null)
                isoBattleLoop.setClickedItemNo(panel.getItemRectangles().indexOf(itemRectangle));
            isoBattleLoop.setItemCatch(false);
            isoBattleLoop.setItemClickFlag(true);
        });
    }
}
