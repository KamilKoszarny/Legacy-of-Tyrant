package viewIso;

import helpers.my.CalcHelper;
import helpers.my.SortHelper;
import helpers.my.StringHelper;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.Battle;
import model.actions.attack.AttackCalculator;
import model.actions.attack.BodyPart;
import model.character.Character;
import model.map.MapPiece;
import model.map.buildings.Door;
import viewIso.map.MapDrawCalculator;
import viewIso.map.MapDrawer;
import viewIso.mapObjects.MapObjectDrawer;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClickMenusDrawer {

    private static List<ClickMenuButton> char2PointMenu = Arrays.asList(ClickMenuButton.LOOK, ClickMenuButton.WALK, ClickMenuButton.RUN, ClickMenuButton.SNEAK);
    private static List<ClickMenuButton> char2DoorMenu = Arrays.asList(ClickMenuButton.DOOR_LOOK, ClickMenuButton.DOOR_OPEN, ClickMenuButton.DOOR_CLOSE);
    private static List<ClickMenuButton> char2EnemyMenu = Arrays.asList(ClickMenuButton.ENEMY_LOOK, ClickMenuButton.ATTACK_HEAD, ClickMenuButton.ATTACK_BODY, ClickMenuButton.ATTACK_ARMS, ClickMenuButton.ATTACK_LEGS);
    private static List<ClickMenuButton> activeMenu = char2PointMenu;


    ClickMenusDrawer() {
        initMenu(char2PointMenu);
        initMenu(char2DoorMenu);
        initMenu(char2EnemyMenu);
    }

    public static void drawChar2PointMenu(Point clickPoint) {
        hideMenus();
        activeMenu = char2PointMenu;
        MapPiece clickedMapPiece = MapDrawCalculator.mapPieceByClickPoint(clickPoint);
        if (clickedMapPiece == null)
            return;
        if (!clickedMapPiece.isWalkable() || Battle.getChosenCharacter().getPath().size() == 0) {
            ClickMenuButton.WALK.setGrayed(true);
            ClickMenuButton.RUN.setGrayed(true);
            ClickMenuButton.SNEAK.setGrayed(true);
        } else {
            ClickMenuButton.WALK.setGrayed(false);
            ClickMenuButton.RUN.setGrayed(false);
            ClickMenuButton.SNEAK.setGrayed(false);
        }
        ClickMenuButton.colorButtons(char2PointMenu);

        for (ClickMenuButton button: char2PointMenu) {
            drawButton(button, clickPoint);
        }
    }

    public static void drawChar2DoorMenu(Point clickPoint, Door door, Character character) {
        hideMenus();
        activeMenu = char2DoorMenu;
        Point doorPos = MapObjectDrawer.getMapObjectPointMap().get(door);
        if (doorPos.distance(character.getPosition()) > Door.ACTION_DIST) {
            ClickMenuButton.DOOR_OPEN.setGrayed(true);
            ClickMenuButton.DOOR_CLOSE.setGrayed(true);
        } else {
            ClickMenuButton.DOOR_OPEN.setGrayed(door.isOpen());
            ClickMenuButton.DOOR_CLOSE.setGrayed(!door.isOpen());
        }

        ClickMenuButton.colorButtons(char2DoorMenu);
        for (ClickMenuButton button: char2DoorMenu) {
            drawButton(button, clickPoint);
        }
    }

    public static void drawChar2EnemyMenu(Point clickPoint, Character character, Character enemy) {
        hideMenus();
        activeMenu = char2EnemyMenu;

        boolean inRange = AttackCalculator.isInRange(character, enemy);
        ClickMenuButton.ATTACK_HEAD.setGrayed(!inRange);
        ClickMenuButton.ATTACK_BODY.setGrayed(!inRange);
        ClickMenuButton.ATTACK_ARMS.setGrayed(!inRange);
        ClickMenuButton.ATTACK_LEGS.setGrayed(!inRange);

        ClickMenuButton.colorButtons(char2EnemyMenu);
        setTooltipText(character, enemy);

        for (ClickMenuButton button: char2EnemyMenu) {
            drawButton(button, clickPoint);
        }
    }

    private static void setTooltipText(Character character, Character enemy) {
        for (BodyPart bodyPart: BodyPart.values()) {
            Map<BodyPart, Integer> chancesToHitByPart = AttackCalculator.calcChancesToHitByBodyPart(character, enemy, bodyPart);
            int chanceToHit = CalcHelper.sum(chancesToHitByPart.values());
            StringBuilder tooltipText = new StringBuilder();
            tooltipText.append("Chance to hit: ").append(chanceToHit).append("%\n");
            chancesToHitByPart = SortHelper.sortByValue(chancesToHitByPart, false);

            for (BodyPart bodyPart1: chancesToHitByPart.keySet()) {
                if (chancesToHitByPart.get(bodyPart1) > 0)
                    tooltipText.append(StringHelper.firstUpper(bodyPart1.name())).append(": ").append(chancesToHitByPart.get(bodyPart1)).append("%\n");
            }
            ClickMenuButton.byBodyPart(bodyPart).setTooltipText(tooltipText.toString());
        }
    }

    public static void hideMenus() {
        hideMenu(char2PointMenu);
        hideMenu(char2DoorMenu);
        hideMenu(char2EnemyMenu);
    }

    private static void hideMenu(List<ClickMenuButton> menu) {
        for (ClickMenuButton button: menu) {
            hideButton(button);
        }
    }

    public void moveMenus(Point mapMove) {
        for (ClickMenuButton button: activeMenu) {
            moveButton(button, mapMove);
        }
    }

    public static ClickMenuButton clickedButton () {
        for (ClickMenuButton button: ClickMenuButton.values()) {
            if (button.wasClicked())
                return button;
        }
        return null;
    }

    private void initMenu(List<ClickMenuButton> menu) {
        ClickMenuButton.groupButtons(menu);
        ClickMenuButton.shapeButtons(menu);
        ClickMenuButton.colorButtons(menu);
        for (ClickMenuButton button: menu) {
            Shape shape = button.getShape();
            shape.setVisible(false);
            Label label = button.getLabel();
            label.setVisible(false);
            Pane pane = (Pane) IsoViewer.getCanvas().getParent();
            pane.getChildren().addAll(shape, label);
        }
    }

    private static void drawButton(ClickMenuButton button, Point clickPoint) {
        Shape shape = button.getShape();
        shape.setTranslateX(clickPoint.x);
        shape.setTranslateY(clickPoint.y);
        shape.setVisible(true);

        Label label = button.getLabel();
        label.setTranslateX(button.getLabelVertex().x + clickPoint.x);
        label.setTranslateY(button.getLabelVertex().y + clickPoint.y);
        label.toFront();

        label.setVisible(true);
    }

    private static void hideButton(ClickMenuButton button) {
        Shape shape = button.getShape();
        shape.setVisible(false);

        Label label = button.getLabel();
        label.setVisible(false);
    }

    private void moveButton(ClickMenuButton button, Point mapMove) {
        Shape shape = button.getShape();
        shape.setTranslateX(shape.getTranslateX() + mapMove.x * MapDrawer.MAP_PIECE_SCREEN_SIZE_X);
        shape.setTranslateY(shape.getTranslateY() + mapMove.y * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y);

        Label label = button.getLabel();
        label.setTranslateX(label.getTranslateX() + mapMove.x * MapDrawer.MAP_PIECE_SCREEN_SIZE_X);
        label.setTranslateY(label.getTranslateY() + mapMove.y * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y);
    }
}
