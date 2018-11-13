package controller.isoView.isoMap;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import model.IsoBattleLoop;
import model.character.Character;

import java.awt.*;
import java.util.List;

public class IsoMapHoverController {


    private Canvas mapCanvas;
    private IsoBattleLoop isoBattleLoop;

    public IsoMapHoverController(Canvas mapCanvas, IsoBattleLoop isoBattleLoop, List<Character> characters) {
        this.mapCanvas = mapCanvas;
        this.isoBattleLoop = isoBattleLoop;
    }

    public void initialize(){
        initCanvasHover();
    }

    private void initCanvasHover(){
        mapCanvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isoBattleLoop.setCanvasHoverPoint(new Point((int)mouseEvent.getX(), (int)mouseEvent.getY()));
                isoBattleLoop.setCanvasHoverFlag(true);
            }
        });
    }
}
