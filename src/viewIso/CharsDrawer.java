package viewIso;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.character.Character;
import model.character.movement.CharTurner;
import model.map.Map;
import sun.font.FontFamily;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CharsDrawer {

    private Dimension SPRITES_SPAN = new Dimension(192, 192);
    private Dimension SPRITE_SIZE = new Dimension(124, 160);
    private Dimension SPRITE_OFFSET =
            new Dimension((SPRITES_SPAN.width - SPRITE_SIZE.width)/2, (SPRITES_SPAN.height - SPRITE_SIZE.height)/2);
    private Dimension SPRITE_BASE = new Dimension(64, 132);

    private Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    MapDrawer mapDrawer;
    private List<Character> characters;
    private Character clickedCharacter;
    private Character hoverCharacter;
    private java.util.Map<Character, CharSprite> charSpriteSheetMap = new HashMap<>();
    private java.util.Map<Character, Label> charLabelsMap = new HashMap<>();

    public CharsDrawer(Map map, Canvas canvas, MapDrawer mapDrawer, List<Character> characters) {
        this.map = map;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        this.mapDrawer = mapDrawer;
        this.characters = characters;

        initCharSpriteMap();
        initCharLabelsMap();
        drawChars(characters);
    }

    public boolean isCharClicked(Point clickPoint) {
        for (Character character: characters) {
            Rectangle clickBox = calcClickBox(character);
            if (clickBox.contains(clickPoint)){
                clickedCharacter = character;
                return true;
            }
        }
        return false;
    }

    public void drawAllChars() {
        drawChars(characters);
    }

    public void drawVisibleChars() {
        List<Character> visibleChars = new ArrayList<>();
        List<Point> visiblePoints = mapDrawer.calcVisiblePoints();
        for (Character character: characters) {
            if (visiblePoints.contains(character.getPosition())) {
                visibleChars.add(character);
            }
        }
        drawChars(visibleChars);
    }

    public void drawChars(List<Character> characters) {
        List<Point> visiblePoints = mapDrawer.calcVisiblePoints();
        for (Character character: characters) {
            if (visiblePoints.contains(character.getPosition()))
                drawChar(character);
        }
    }



    public void drawChar(Character character) {

        clearCharProximity(character);

        Point charScreenPos = mapDrawer.screenPositionWithHeight(character.getPosition());
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

        charSprite.nextFrame();
    }

    private void clearCharProximity(Character character) {
        List<Point> charClosePoints = calcCharClosePoints(character);
        mapDrawer.drawMapPoints(charClosePoints);
    }

    private void drawLabel(Character character, Point charScreenPos) {
        Label label = charLabelsMap.get(character);

        if (character.isChosen())
            label.setStyle("-fx-font-weight: bold");
        else if(character == hoverCharacter)
            label.setStyle("-fx-underline: true");
        else
            label.setStyle("-fx-font-weight: regular");

        label.setTranslateX(charScreenPos.x - canvas.getWidth()/2);
        label.setTranslateY(charScreenPos.y - SPRITE_SIZE.height * (.6) - canvas.getHeight()/2);
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
            charLabelsMap.put(character, label);

            Pane pane = (Pane) canvas.getParent();
            pane.getChildren().add(label);

            label.setVisible(true);
        }
    }


    private CharSprite chooseSpriteSheet(Character character) {
        CharSprite charSprite = null;
        charSprite = new CharSprite(new Image("/sprites/flare/demo/vesuvvio.png"));

        return charSprite;
    }

    private List<Point> calcCharClosePoints (Character character) {
        Point charPos = character.getPosition();
        List<Point> charClosePoints = new ArrayList<>();
        for (int x = -4; x < 5; x++) {
            for (int y = -4; y < 5; y++) {
                charClosePoints.add(new Point(charPos.x + x, charPos.y + y));
            }
        }
        return charClosePoints;
    }

    public Character getClickedCharacter() {
        return clickedCharacter;
    }

    public void checkHoverCharacter(Point hoverPoint){
        hoverCharacter = null;
        for (Character character: characters) {
            if(calcClickBox(character).contains(hoverPoint))
                hoverCharacter = character;
        }
    }

    private Rectangle calcClickBox(Character character) {
        Point charScreenPos = mapDrawer.screenPositionWithHeight(character.getPosition());
        return new Rectangle(charScreenPos.x - SPRITE_BASE.width/2, charScreenPos.y - SPRITE_BASE.height*2/3,
                SPRITE_SIZE.width/2, SPRITE_SIZE.height*2/3);
    }
}
