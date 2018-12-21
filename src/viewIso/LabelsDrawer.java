package viewIso;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Battle;
import model.actions.attack.AttackResultType;
import model.character.Character;
import viewIso.characters.CharsDrawer;

import java.awt.*;
import java.util.HashMap;

public class LabelsDrawer {

    private static java.util.Map<Character, Label> charNameLabelsMap = new HashMap<>();
    private static java.util.Map<Character, Label> charDamageLabelsMap = new HashMap<>();
    private static java.util.Map<Character, Point> damageLabelsOffsetMap = new HashMap<>();
    private static Character hoverCharacter;

    public LabelsDrawer() {
        initCharNameLabelsMap();
        initCharDamageLabelsMap();
        initDamageLabelsOffsetMap();
    }

    private static void initCharNameLabelsMap() {
        for (Character character: Battle.getCharacters()) {
            Label label = new Label(character.getName().toUpperCase());
            label.setFont(new Font("SansSerif", 15));
            label.setTextFill(character.getColor());
            label.setEffect(new DropShadow(.5f, 1.f, 1.f, Color.BLACK));
            label.setCache(true);
            label.setVisible(true);

            Pane pane = (Pane) IsoViewer.getCanvas().getParent();
            pane.getChildren().add(label);

            charNameLabelsMap.put(character, label);
        }
    }

    private static void initCharDamageLabelsMap() {
        for (Character character: Battle.getCharacters()) {
            Label label = new Label();
            label.setFont(new Font("SansSerif", 15));
            label.setTextFill(Color.WHITE);
            label.setEffect(new DropShadow(.5f, 1.f, 1.f, Color.BLACK));
            label.setCache(true);
            label.setVisible(false);

            Pane pane = (Pane) IsoViewer.getCanvas().getParent();
            pane.getChildren().add(label);

            charDamageLabelsMap.put(character, label);
        }
    }

    private static void initDamageLabelsOffsetMap() {
        for (Character character: Battle.getCharacters()) {
            Point offset = new Point(0, 0);
            damageLabelsOffsetMap.put(character, offset);
        }
    }

    public static void drawNameLabel(Character character, Point charScreenPos) {
        Label label = charNameLabelsMap.get(character);

        if (character.isChosen())
            label.setStyle("-fx-font-weight: bold");
        else if(character == hoverCharacter)
            label.setStyle("-fx-underline: true");
        else
            label.setStyle("-fx-font-weight: regular");

        label.setTranslateX(charScreenPos.x - label.getWidth()/2);
        label.setTranslateY(charScreenPos.y - CharsDrawer.SPRITE_SIZE.height * (.6));

        label.setVisible(true);
    }

    public static void drawDamageLabel(Character character, Point charScreenPos) {
        final int OFFSET_X = 10, OFFSET_Y = -50;
        Label label = charDamageLabelsMap.get(character);
        Point offset = damageLabelsOffsetMap.get(character);

        if (character.getAttackResult().getType().equals(AttackResultType.HIT))
            label.setText("-" + String.valueOf(character.getAttackResult().getDamage()));
        else
            label.setText(character.getAttackResult().getType().toString());

        if (offset.y > OFFSET_Y) {
            offset = new Point(offset.x, offset.y - 2);
            label.setTranslateX(charScreenPos.x - label.getWidth() / 2 + offset.getX());
            label.setTranslateY(charScreenPos.y - CharsDrawer.SPRITE_SIZE.height * (.6) - 10 + offset.getY());

            label.setVisible(true);
        } else {
            offset = new Point(0, 0);
            character.setAttackResult(null);
            label.setVisible(false);
        }
        damageLabelsOffsetMap.put(character, offset);
    }

    public static void resetDamageLabel(Character character) {
        damageLabelsOffsetMap.put(character, new Point(0, 0));
    }

    public static void checkHoverCharacter(Point hoverPoint){
        hoverCharacter = null;
        for (Character character: Battle.getCharacters()) {
            if(CharsDrawer.calcClickBox(character).contains(hoverPoint))
                hoverCharacter = character;
        }
    }

    public static void hideLabels() {
        for (Label label: charNameLabelsMap.values()) {
            label.setVisible(false);
        }
    }
}