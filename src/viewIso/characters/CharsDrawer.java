package viewIso.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Battle;
import model.character.CharState;
import model.character.Character;
import viewIso.IsoViewer;
import viewIso.LabelsDrawer;
import viewIso.map.MapDrawCalculator;

import java.awt.*;
import java.util.HashMap;

public class CharsDrawer {

    private static Dimension SPRITES_SPAN = new Dimension(192, 192);
    public static Dimension SPRITE_SIZE = new Dimension(124, 160);
    private static Dimension SPRITE_OFFSET =
            new Dimension((SPRITES_SPAN.width - SPRITE_SIZE.width)/2, (SPRITES_SPAN.height - SPRITE_SIZE.height)/2);
    private static Dimension SPRITE_BASE = new Dimension(64, 132);

    private static Character clickedCharacter;
    private static java.util.Map<Character, CharSprite> charSpriteSheetMap = new HashMap<>();

    public CharsDrawer() {
        initCharSpriteMap();
    }

    public void drawChar(Character character) {
        Point charScreenPos = MapDrawCalculator.screenPositionWithHeight(character.getPrecisePosition());
        CharSprite charSprite = charSpriteSheetMap.get(character);
        Image spriteSheet = charSprite.getCharSpriteSheet();
        charSprite.setCharPose(character.getState().getPose());
        int framePosX = charSprite.getFramePos();
        int framePosY = (character.getDirection() + 6) % 8;

        GraphicsContext gc = IsoViewer.getCanvas().getGraphicsContext2D();
        gc.drawImage(spriteSheet,
                SPRITES_SPAN.width * framePosX + SPRITE_OFFSET.width, SPRITES_SPAN.height * framePosY + SPRITE_OFFSET.height,
                SPRITE_SIZE.width, SPRITE_SIZE.height,
                charScreenPos.x - SPRITE_BASE.width, charScreenPos.y - SPRITE_BASE.height,
                SPRITE_SIZE.width, SPRITE_SIZE.height);

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
            CharSprite sprite = chooseSpriteSheet(character);
            charSpriteSheetMap.put(character, sprite);
        }
    }

    private CharSprite chooseSpriteSheet(Character character) {
        return new CharSprite(new Image("/sprites/chars/flare/vesuvvio.png"));
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
