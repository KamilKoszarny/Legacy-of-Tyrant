package controller.isoView.isoMap;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import model.IsoBattleLoop;
import viewIso.IsoViewer;

import java.awt.*;

public class IsoMapKeyController {

    private Scene scene;

    public IsoMapKeyController(Scene scene) {
        this.scene = scene;

        initialize();
    }

    public void initialize() {
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.UP) {
                IsoBattleLoop.changeMapMove(new Point(0, IsoMapMoveController.MAP_MOVE_STEP));
                IsoBattleLoop.setMapMoveFlag(true);
            } else if (keyCode == KeyCode.RIGHT) {
                IsoBattleLoop.changeMapMove(new Point(-IsoMapMoveController.MAP_MOVE_STEP, 0));
                IsoBattleLoop.setMapMoveFlag(true);
            } else if (keyCode == KeyCode.DOWN) {
                IsoBattleLoop.changeMapMove(new Point(0, -IsoMapMoveController.MAP_MOVE_STEP));
                IsoBattleLoop.setMapMoveFlag(true);
            } else if (keyCode == KeyCode.LEFT) {
                IsoBattleLoop.changeMapMove(new Point(IsoMapMoveController.MAP_MOVE_STEP, 0));
                IsoBattleLoop.setMapMoveFlag(true);
            } else if (keyEvent.getCode().equals(KeyCode.SLASH))
                IsoViewer.switchCutView();
            else if (keyEvent.getCode().equals(KeyCode.ESCAPE))
                IsoViewer.getClickMenusDrawer().hideMenus();
        });
        scene.setOnKeyReleased(keyEvent -> {
            IsoBattleLoop.resetMapMove(new Point(0, 0));
            IsoBattleLoop.setMapMoveFlag(false);
        });
    }
}
