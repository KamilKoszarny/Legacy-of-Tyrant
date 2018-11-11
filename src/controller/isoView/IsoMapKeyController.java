package controller.isoView;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import model.IsoBattleLoop;
import viewIso.IsoViewer;

import java.awt.*;

public class IsoMapKeyController {

    private Scene scene;
    private IsoViewer isoViewer;
    private IsoBattleLoop isoBattleLoop;

    public IsoMapKeyController(Scene scene, IsoViewer isoViewer, IsoBattleLoop isoBattleLoop) {
        this.scene = scene;
        this.isoViewer = isoViewer;
        this.isoBattleLoop = isoBattleLoop;
    }

    void initiazile() {

        scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.UP) {
                isoBattleLoop.changeMapMove(new Point(0, IsoMapMoveController.MAP_MOVE_STEP));
                isoBattleLoop.setMapMoveFlag(true);
            } else if (keyCode == KeyCode.RIGHT) {
                isoBattleLoop.changeMapMove(new Point(-IsoMapMoveController.MAP_MOVE_STEP, 0));
                isoBattleLoop.setMapMoveFlag(true);
            } else if (keyCode == KeyCode.DOWN) {
                isoBattleLoop.changeMapMove(new Point(0, -IsoMapMoveController.MAP_MOVE_STEP));
                isoBattleLoop.setMapMoveFlag(true);
            } else if (keyCode == KeyCode.LEFT) {
                isoBattleLoop.changeMapMove(new Point(IsoMapMoveController.MAP_MOVE_STEP, 0));
                isoBattleLoop.setMapMoveFlag(true);
            } else if (keyEvent.getCode().equals(KeyCode.SLASH))
                isoViewer.switchCutView();
            else if (keyEvent.getCode().equals(KeyCode.ESCAPE))
                isoViewer.getClickMenusDrawer().hideMenus();
        });
        scene.setOnKeyReleased(keyEvent -> {
            isoBattleLoop.resetMapMove(new Point(0, 0));
            isoBattleLoop.setMapMoveFlag(false);
        });
    }
}
