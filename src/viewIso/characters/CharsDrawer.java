package viewIso.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.App;
import model.Battle;
import model.character.CharState;
import model.character.Character;
import model.items.ItemsLoader;
import model.items.weapon.Weapon;
import viewIso.IsoViewer;
import viewIso.LabelsDrawer;
import viewIso.map.MapDrawCalculator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CharsDrawer {

    public static final Dimension
            SPRITESHEET_SIZE = new Dimension(6144, 1536),
            SPRITE_SIZE = new Dimension(124, 160),
            SPRITES_SPAN = new Dimension(192, 192),
            SPRITE_BASE = new Dimension(64, 132),
            SPRITE_OFFSET = new Dimension
                    ((SPRITES_SPAN.width - SPRITE_SIZE.width)/2, (SPRITES_SPAN.height - SPRITE_SIZE.height)/2);
    public static final double SCALING = 1.5;

    private static Character clickedCharacter;
    private static java.util.Map<Character, CharSprite> charSpriteSheetMap = new HashMap<>();

    public CharsDrawer() {
        initCharSpriteMap();
    }

    public void drawChar(Character character, boolean transparency) {
        Point charScreenPos = MapDrawCalculator.screenPositionWithHeight(character.getPrecisePosition());
        CharSprite charSprite = charSpriteSheetMap.get(character);
        Image spriteSheet = charSprite.getCharSpriteSheet();
        charSprite.setCharPose(character.getState().getPose());
        int framePosX = charSprite.getFramePos();
        int framePosY = (character.getDirection() + 6) % 8;

        GraphicsContext gc = IsoViewer.getCanvas().getGraphicsContext2D();

        if(transparency)
            gc.setGlobalAlpha(.5);

        gc.drawImage(spriteSheet,
                    (SPRITES_SPAN.width * framePosX + SPRITE_OFFSET.width)/SCALING, (SPRITES_SPAN.height * framePosY + SPRITE_OFFSET.height)/SCALING,
                    SPRITE_SIZE.width/SCALING, SPRITE_SIZE.height/SCALING,
                    charScreenPos.x - SPRITE_BASE.width, charScreenPos.y - SPRITE_BASE.height,
                    SPRITE_SIZE.width, SPRITE_SIZE.height);
        gc.setGlobalAlpha(1);

        LabelsDrawer.drawNameLabel(character, charScreenPos);
        if (character.getAttackResult() != null)
            LabelsDrawer.drawDamageLabel(character, charScreenPos);
    }

    public static void nextFrame(Character character, int timer) {
        if (timer%character.getState().getPose().getDelay() == 0) {
            CharSprite charSprite = charSpriteSheetMap.get(character);
            if (!charSprite.nextFrame()) {
                character.setState(CharState.stateAfter(character.getState()));
            }
        }
    }

    private void initCharSpriteMap() {
        for (Character character: Battle.getCharacters()) {
            createCharSpriteSheet(character);
        }
    }

    public static void createCharSpriteSheet(Character character) {
        long startTime = System.nanoTime();
        long time = startTime;
        CharSprite sprite = createSpriteSheet(character);
        charSpriteSheetMap.put(character, sprite);
        System.out.println(character.getName() + " TOTAL sprite: " + (System.nanoTime() - time)/1000000. + " ms");
    }

    private static CharSprite createSpriteSheet(Character character) {
        List<BufferedImage> charSubSprites = new ArrayList<>(ItemsLoader.loadItemSprites(character));

        BufferedImage combinedImage = new BufferedImage(SPRITESHEET_SIZE.width, SPRITESHEET_SIZE.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = combinedImage.getGraphics();
        for (BufferedImage sprite: charSubSprites) {
            g.drawImage(sprite, 0, 0, null);
        }

        return new CharSprite(combinedImage);
    }


    public static boolean isOtherCharClicked(Point clickPoint, Character chosenChar) {
        for (Character character: Battle.getCharacters()) {
            Rectangle clickBox = calcClickBox(character);
            if (!character.equals(chosenChar) && clickBox.contains(clickPoint)){
                clickedCharacter = character;
                return true;
            }
        }
        return false;
    }

    public static Rectangle calcClickBox(Character character) {
        Point charScreenPos = MapDrawCalculator.screenPositionWithHeight(character.getPosition());
        return new Rectangle(charScreenPos.x - SPRITE_BASE.width/2, charScreenPos.y - SPRITE_BASE.height*2/3,
                SPRITE_SIZE.width/2, SPRITE_SIZE.height*2/3);
    }

    public static Character getClickedCharacter() {
        return clickedCharacter;
    }

}
