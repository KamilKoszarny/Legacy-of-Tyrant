package controller.isoView.isoMap;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import model.Battle;
import model.BattleEvent;
import model.EventType;
import model.IsoBattleLoop;
import model.actions.CharsChooser;
import viewIso.ClickMenusDrawer;
import viewIso.IsoViewer;
import viewIso.mapObjects.MapObjectDrawer;

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
            switch (keyCode) {
                case UP:
                    IsoBattleLoop.setBattleEvent(new BattleEvent(EventType.MOVE_MAP,
                            new Point(0, IsoMapBorderHoverController.MAP_MOVE_STEP)));
                    break;
                case RIGHT:
                    IsoBattleLoop.setBattleEvent(new BattleEvent(EventType.MOVE_MAP,
                            new Point(-IsoMapBorderHoverController.MAP_MOVE_STEP, 0)));
                    break;
                case DOWN:
                    IsoBattleLoop.setBattleEvent(new BattleEvent(EventType.MOVE_MAP,
                            new Point(0, -IsoMapBorderHoverController.MAP_MOVE_STEP)));
                    break;
                case LEFT:
                    IsoBattleLoop.setBattleEvent(new BattleEvent(EventType.MOVE_MAP,
                            new Point(IsoMapBorderHoverController.MAP_MOVE_STEP, 0)));
                    break;
                case SLASH:
                    MapObjectDrawer.switchCutView();
                    break;
                case ESCAPE:
                    CharsChooser.chooseCharacter(null);
            }
        });
        scene.setOnKeyReleased(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            switch (keyCode) {
                case UP:
                case RIGHT:
                case DOWN:
                case LEFT:
                    IsoBattleLoop.setBattleEvent(new BattleEvent(EventType.MOVE_MAP, new Point(0, 0)));
            }
        });
    }
}
