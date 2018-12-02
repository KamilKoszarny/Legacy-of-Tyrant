package controller.isoView.isoMap;

import javafx.scene.canvas.Canvas;
import model.IsoBattleLoop;

import java.awt.*;

public class IsoMapHoverController {

    private Canvas mapCanvas;

    public IsoMapHoverController(Canvas mapCanvas) {
        this.mapCanvas = mapCanvas;

        initialize();
    }

    public void initialize(){
        initCanvasHover();
    }

    private void initCanvasHover(){
        mapCanvas.setOnMouseMoved(mouseEvent -> {
            IsoBattleLoop.setCanvasHoverPoint(new Point((int)mouseEvent.getX(), (int)mouseEvent.getY()));
            IsoBattleLoop.setCanvasHoverFlag(true);
        });
    }
}
