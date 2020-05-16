package isoview.clickmenus;

import helpers.my.CalcHelper;
import helpers.my.SortHelper;
import helpers.my.StringHelper;
import isoview.IsoViewer;
import isoview.map.MapDrawCalculator;
import isoview.map.MapDrawer;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.actions.attack.AttackCalculator;
import model.actions.attack.BodyPart;
import model.actions.movement.Char2CharMover;
import model.actions.movement.Char2ObjectMover;
import model.actions.movement.PathCalculator;
import model.actions.objects.ChestActioner;
import model.character.Character;
import model.map.MapPiece;
import model.map.buildings.Chest;
import model.map.buildings.Door;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClickMenusDrawer {

    private static List<ClickMenuButton> char2PointMenu = Arrays.asList(ClickMenuButton.LOOK, ClickMenuButton.WALK, ClickMenuButton.RUN, ClickMenuButton.SNEAK);
    private static List<ClickMenuButton> char2DoorMenu = Arrays.asList(ClickMenuButton.DOOR_LOOK, ClickMenuButton.DOOR_OPEN, ClickMenuButton.DOOR_CLOSE);
    private static List<ClickMenuButton> char2ChestMenu = Arrays.asList(ClickMenuButton.CHEST_LOOK, ClickMenuButton.CHEST_OPEN, ClickMenuButton.CHEST_CLOSE);
    private static List<ClickMenuButton> char2EnemyMenu = Arrays.asList(ClickMenuButton.ENEMY_LOOK, ClickMenuButton.ATTACK_HEAD, ClickMenuButton.ATTACK_BODY, ClickMenuButton.ATTACK_ARMS, ClickMenuButton.ATTACK_LEGS);
    private static List<ClickMenuButton> activeMenu = char2PointMenu;


    public ClickMenusDrawer() {
        initMenu(char2PointMenu);
        initMenu(char2DoorMenu);
        initMenu(char2ChestMenu);
        initMenu(char2EnemyMenu);
    }

    public static void drawChar2PointMenuAndPath(Point clickPoint, Point mapPoint) {
        List<Point2D> path = PathCalculator.calcAndDrawPath(mapPoint);

        hideMenus(true);
        activeMenu = char2PointMenu;
        MapPiece clickedMapPiece = MapDrawCalculator.mapPieceByClickPoint(clickPoint);
        if (clickedMapPiece == null)
            return;
        if (!clickedMapPiece.isWalkable() || path.size() == 0) {
            ClickMenuButton.WALK.setGrayed(true);
            ClickMenuButton.RUN.setGrayed(true);
            ClickMenuButton.SNEAK.setGrayed(true);
        } else {
            ClickMenuButton.WALK.setGrayed(false);
            ClickMenuButton.RUN.setGrayed(false);
            ClickMenuButton.SNEAK.setGrayed(false);
        }

        ClickMenuButton.WALK.setGrayed(true);
        ClickMenuButton.SNEAK.setGrayed(true);

        drawMenu(char2PointMenu, clickPoint);
    }

    public static void drawChar2DoorMenu(Point clickPoint, Door door, Character character) {
        hideMenus(true);
        activeMenu = char2DoorMenu;

        if (Char2ObjectMover.closeToObject(character, door) || Char2ObjectMover.pathToObjectExists(character, door)) {
            ClickMenuButton.DOOR_OPEN.setGrayed(door.isOpen());
            ClickMenuButton.DOOR_CLOSE.setGrayed(!door.isOpen());
        } else {
            ClickMenuButton.DOOR_OPEN.setGrayed(true);
            ClickMenuButton.DOOR_CLOSE.setGrayed(true);
        }

        drawMenu(char2DoorMenu, clickPoint);
    }

    public static void drawChar2ChestMenu(Point clickPoint, Chest chest, Character character) {
        hideMenus(true);
        activeMenu = char2ChestMenu;

        ClickMenuButton.CHEST_OPEN.setGrayed(!Char2ObjectMover.closeToObject(character, chest) && !Char2ObjectMover.pathToObjectExists(character, chest));
        ClickMenuButton.CHEST_CLOSE.setGrayed(true);

        drawMenu(char2ChestMenu, clickPoint);
    }

    public static void drawChar2EnemyMenu(Point clickPoint, Character character, Character enemy) {
        hideMenus(true);
        activeMenu = char2EnemyMenu;

        character.clearPath();

        boolean attackable = (AttackCalculator.isInRange(character, enemy) ||
                Char2CharMover.drawPathToCharIfExists(character, enemy)) &&
                enemy.getStats().getHitPoints() > 0;
        ClickMenuButton.ATTACK_HEAD.setGrayed(!attackable);
        ClickMenuButton.ATTACK_BODY.setGrayed(!attackable);
        ClickMenuButton.ATTACK_ARMS.setGrayed(!attackable);
        ClickMenuButton.ATTACK_LEGS.setGrayed(!attackable);

        setTooltipText(character, enemy);
        drawMenu(char2EnemyMenu, clickPoint);
    }

    private static void drawMenu(List<ClickMenuButton> menu, Point clickPoint) {
        ClickMenuButton.colorButtons(menu);
        for (ClickMenuButton button: menu) {
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

    public static void hideMenus(boolean hideChestInventoyry) {
        hideMenu(char2PointMenu);
        hideMenu(char2DoorMenu);
        hideMenu(char2ChestMenu);
        if (hideChestInventoyry)
            ChestActioner.hideChestInventory();
        hideMenu(char2EnemyMenu);
    }

    private static void hideMenu(List<ClickMenuButton> menu) {
        for (ClickMenuButton button: menu) {
            hideButton(button);
        }
    }

    public static void moveMenus(Point mapMove) {
        for (ClickMenuButton button: activeMenu) {
            moveButton(button, mapMove);
        }
        ChestActioner.moveChestInventory(mapMove);
    }

    public static ClickMenuButton clickedButton () {
        for (ClickMenuButton button: ClickMenuButton.values()) {
            if (button.wasClicked())
                return button;
        }
        return null;
    }

    public static ClickMenuButton hoveredButton () {
        for (ClickMenuButton button: ClickMenuButton.values()) {
            if (button.isHovered())
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
            Pane pane = IsoViewer.getPane();
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

    private static void moveButton(ClickMenuButton button, Point mapMove) {
        Shape shape = button.getShape();
        shape.setTranslateX(shape.getTranslateX() + mapMove.x * MapDrawer.MAP_PIECE_SCREEN_SIZE_X);
        shape.setTranslateY(shape.getTranslateY() + mapMove.y * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y);

        Label label = button.getLabel();
        label.setTranslateX(label.getTranslateX() + mapMove.x * MapDrawer.MAP_PIECE_SCREEN_SIZE_X);
        label.setTranslateY(label.getTranslateY() + mapMove.y * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y);
    }
}
