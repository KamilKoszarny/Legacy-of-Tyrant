package viewIso.characters;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.character.Character;
import model.map.Map;
import viewIso.map.MapDrawCalculator;
import viewIso.map.MapDrawer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CharsDrawer {

    private static Dimension SPRITES_SPAN = new Dimension(192, 192);
    private static Dimension SPRITE_SIZE = new Dimension(124, 160);
    private static Dimension SPRITE_OFFSET =
            new Dimension((SPRITES_SPAN.width - SPRITE_SIZE.width)/2, (SPRITES_SPAN.height - SPRITE_SIZE.height)/2);
    private static Dimension SPRITE_BASE = new Dimension(64, 132);

    private Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    private MapDrawer mapDrawer;
    private static List<Character> characters;
    private static Character clickedCharacter;
    private static Character hoverCharacter;
    private static java.util.Map<Character, CharSprite> charSpriteSheetMap = new HashMap<>();
    private java.util.Map<Character, Label> charLabelsMap = new HashMap<>();

    public CharsDrawer(Map map, Canvas canvas, MapDrawer mapDrawer, List<Character> characters) {
        this.map = map;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        this.mapDrawer = mapDrawer;
        CharsDrawer.characters = characters;

        initCharSpriteMap();
        initCharLabelsMap();
//        drawChars(characters);
    }

    public void drawChar(Character character) {
        Point charScreenPos = MapDrawCalculator.screenPositionWithHeight(character.getPrecisePosition());
        CharSprite charSprite = charSpriteSheetMap.get(character);
        Image spriteSheet = charSprite.getCharSpriteSheet();
        charSprite.setCharPose(character.getState().getPose());
        int framePosX = charSprite.getFramePos();
        int framePosY = (character.getDirection() + 6) % 8;

        gc.drawImage(spriteSheet,
                SPRITES_SPAN.width * framePosX + SPRITE_OFFSET.width, SPRITES_SPAN.height * framePosY + SPRITE_OFFSET.height,
                SPRITE_SIZE.width, SPRITE_SIZE.height,
                charScreenPos.x - SPRITE_BASE.width, charScreenPos.y - SPRITE_BASE.height,
                SPRITE_SIZE.width, SPRITE_SIZE.height);

        drawLabel(character, charScreenPos);
    }

    public static void nextFrame(Character character) {
        CharSprite charSprite = charSpriteSheetMap.get(character);
        charSprite.nextFrame();
    }

    private void drawLabel(Character character, Point charScreenPos) {
        Label label = charLabelsMap.get(character);

        if (character.isChosen())
            label.setStyle("-fx-font-weight: bold");
        else if(character == hoverCharacter)
            label.setStyle("-fx-underline: true");
        else
            label.setStyle("-fx-font-weight: regular");

        label.setTranslateX(charScreenPos.x - label.getWidth()/2);
        label.setTranslateY(charScreenPos.y - SPRITE_SIZE.height * (.6));

//        if (label.getTranslateY() < canvas.getHeight() - label.getHeight()/2)
            label.setVisible(true);
//        else
//            label.setVisible(false);
    }

    public void hideLabels() {
        for (Label label: charLabelsMap.values()) {
            label.setVisible(false);
        }
    }

    private void initCharSpriteMap() {
        for (Character character: characters) {
            CharSprite sprite = chooseSpriteSheet(character);
            charSpriteSheetMap.put(character, sprite);
        }
    }

    private void initCharLabelsMap() {
        for (Character character: characters) {
            Label label = new Label(character.getName().toUpperCase());
            label.setFont(new Font("SansSerif", 20));
            label.setTextFill(character.getColor());
            label.setEffect(new DropShadow(.5f, 1.f, 1.f, Color.BLACK));
            label.setCache(true);

            charLabelsMap.put(character, label);

            Pane pane = (Pane) canvas.getParent();
            pane.getChildren().add(label);

            label.setVisible(true);
        }
    }

    private CharSprite chooseSpriteSheet(Character character) {
        return new CharSprite(new Image("/sprites/chars/flare/vesuvvio.png"));
    }


    public static boolean isOtherCharClicked(Point clickPoint, Character chosenChar) {
        for (Character character: characters) {
            Rectangle clickBox = calcClickBox(character);
            if (!character.equals(chosenChar) && clickBox.contains(clickPoint)){
                clickedCharacter = character;
                return true;
            }
        }
        return false;
    }

    private static Rectangle calcClickBox(Character character) {
        Point charScreenPos = MapDrawCalculator.screenPositionWithHeight(character.getPosition());
        return new Rectangle(charScreenPos.x - SPRITE_BASE.width/2, charScreenPos.y - SPRITE_BASE.height*2/3,
                SPRITE_SIZE.width/2, SPRITE_SIZE.height*2/3);
    }

    public static Character getClickedCharacter() {
        return clickedCharacter;
    }

    public static void checkHoverCharacter(Point hoverPoint){
        hoverCharacter = null;
        for (Character character: characters) {
            if(calcClickBox(character).contains(hoverPoint))
                hoverCharacter = character;
        }
    }
}
