package viewIso;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import model.character.Character;
import model.map.Map;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class CharsDrawer {

    private Dimension SPRITE_PIX_SIZE = new Dimension(124, 160);

    private Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    MapDrawer mapDrawer;
    private List<Character> characters;
    private java.util.Map<Character, CharSprite> charSpriteSheetMap = new HashMap<>();

    public CharsDrawer(Map map, Canvas canvas, MapDrawer mapDrawer, List<Character> characters) {
        this.map = map;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        this.mapDrawer = mapDrawer;
        this.characters = characters;

        initCharSpriteMap();
        drawChars(characters);
    }

    public void drawAllChars() {
        drawChars(characters);
    }

    public void drawChars(List<Character> characters) {
        List<Point> visiblePoints = mapDrawer.calcVisiblePoints();
        for (Character character: characters) {
            if (visiblePoints.contains(character.getPosition()))
                drawChar(character);
        }
    }

    public void drawChar(Character character) {
        Image spriteSheet = charSpriteSheetMap.get(character).getCharSpriteSheet();
        gc.drawImage(spriteSheet, 70, 70, SPRITE_PIX_SIZE.getWidth(), SPRITE_PIX_SIZE.getHeight(),
                100, 100,  SPRITE_PIX_SIZE.getWidth(), SPRITE_PIX_SIZE.getHeight());
    }

    private void initCharSpriteMap() {
        for (Character character: characters) {
            CharSprite sprite = chooseSpriteSheet(character);
            charSpriteSheetMap.put(character, sprite);
        }
    }

    private CharSprite chooseSpriteSheet(Character character) {
        CharSprite charSprite = null;
        charSprite = new CharSprite(new Image("/sprites/flare/demo/vesuvvio.png"));

        return charSprite;
    }

}
