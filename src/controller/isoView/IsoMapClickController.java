package controller.isoView;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import model.IsoBattleLoop;
import model.map.MapPiece;

import java.awt.*;

public class IsoMapClickController {


    private Canvas mapCanvas;
    private IsoBattleLoop isoBattleLoop;

    IsoMapClickController(Canvas mapCanvas, IsoBattleLoop isoBattleLoop) {
        this.mapCanvas = mapCanvas;
        this.isoBattleLoop = isoBattleLoop;
    }

    void initialize(){
        initInfoOnClick();
    }

    private void initInfoOnClick(){
        mapCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isoBattleLoop.setMapClickPoint(new Point((int)mouseEvent.getX(), (int)mouseEvent.getY()));
                isoBattleLoop.setMapClickFlag(true);
            }
        });
    }
}
