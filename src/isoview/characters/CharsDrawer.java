package isoview.characters;

import helpers.my.MouseHelper;
import isoview.IsoViewer;
import isoview.LabelsDrawer;
import isoview.clickmenus.ClickMenusDrawer;
import isoview.map.MapDrawCalculator;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import main.App;
import model.Battle;
import model.character.CharState;
import model.character.Character;
import model.items.ItemImagesLoader;
import model.map.visibility.VisibilityChecker;

import java.awt.*;
import java.awt.image.BufferedImage;
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

    public static void drawChar(Character character, boolean transparency) {
        CharSprite charSprite = charSpriteSheetMap.get(character);
        Image spriteSheet = charSprite.getCharSpriteSheet();
        charSprite.setCharPose(character.getState().getPose());

        GraphicsContext gc = IsoViewer.getCanvas().getGraphicsContext2D();
        if (character.equals(Battle.getChosenCharacter()))
            gc.setEffect(new Glow(.6));
        else if (character.getColor().equals(Battle.getPlayerColor()))
            gc.setEffect(new Glow(.3));
        else if (VisibilityChecker.isInChosenCharView(character.getPosition()))
            gc.setEffect(new Glow(.3));
        else if (VisibilityChecker.isInPlayerCharsView(character.getPosition()))
            gc.setEffect(new ColorAdjust(0, -.5, -.5, 0));
        else
            return;

        Point charScreenPos = MapDrawCalculator.screenPositionWithHeight(character.getPrecisePosition());
        int framePosX = charSprite.getFramePos();
        int framePosY = (character.getDirection() + 6) % 8;

        if(transparency)
            gc.setGlobalAlpha(.5);

        gc.drawImage(spriteSheet,
                    (SPRITES_SPAN.width * framePosX + SPRITE_OFFSET.width)/SCALING, (SPRITES_SPAN.height * framePosY + SPRITE_OFFSET.height)/SCALING,
                    SPRITE_SIZE.width/SCALING, SPRITE_SIZE.height/SCALING,
                    charScreenPos.x - SPRITE_BASE.width, charScreenPos.y - SPRITE_BASE.height,
                    SPRITE_SIZE.width, SPRITE_SIZE.height);
        gc.setGlobalAlpha(1);
        gc.setEffect(null);

        LabelsDrawer.drawNameLabel(character, charScreenPos);
        if (character.getAttackResult() != null)
            LabelsDrawer.drawDamageLabel(character, charScreenPos);
        LabelsDrawer.drawAttackAPCostLabel(ClickMenusDrawer.hoveredButton());
    }

    public static void nextFrame(Character character, int timer) {
        if (timer % character.getState().getPose().getDelay() == 0) {
            CharSprite charSprite = charSpriteSheetMap.get(character);
            if (charSprite.singleAnimationFinished()) {
                if (!Battle.isTurnMode() || character.getStats().getActionPoints() > 0) {
                    charSprite.zeroAnimationFrame();
                    character.setState(CharState.stateAfter(character.getState()));
                }
            } else {
                charSprite.nextFrame();
            }
        }
    }

    private void initCharSpriteMap() {
        for (Character character: Battle.getCharacters()) {
            createCharSpriteSheet(character);
        }
    }

    public static void createCharSpriteSheet(Character character) {
        App.resetTime(0);
        CharSprite sprite = createSpriteSheet(character);
        charSpriteSheetMap.put(character, sprite);
        App.showAndResetTime(character.getName() + " TOTAL sprite", -1);
    }

    private static CharSprite createSpriteSheet(Character character) {
        List<BufferedImage> charSubSprites = new ArrayList<>(ItemImagesLoader.loadItemSprites(character));

        BufferedImage combinedImage = new BufferedImage(SPRITESHEET_SIZE.width, SPRITESHEET_SIZE.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = combinedImage.getGraphics();
        for (BufferedImage sprite: charSubSprites) {
            g.drawImage(sprite, 0, 0, null);
        }

        return new CharSprite(combinedImage);
    }


    public static boolean isOtherCharClicked(Point clickPoint, Character chosenChar) {
        Point mapPoint = MapDrawCalculator.mapPointByClickPoint(clickPoint);
        for (Character character: Battle.getCharacters()) {
            Rectangle clickBox = calcClickBox(character);
            if (!character.equals(chosenChar)
                    && clickBox.contains(clickPoint)
                    && (VisibilityChecker.isInPlayerCharsView(mapPoint)
                    || Battle.getPlayerColor().equals(character.getColor()))){
                clickedCharacter = character;
                return true;
            }
        }
        return false;
    }

    private static boolean checkPointOnCharacter(Point mousePoint, Character character) {

        Point mapPos = character.getPosition();
        CharSprite charSprite = charSpriteSheetMap.get(character);
        Image spriteSheet = charSprite.getCharSpriteSheet();

        int framePosX = charSprite.getFramePos();
        int framePosY = (character.getDirection() + 6) % 8;

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(spriteSheet, null);
        bufferedImage = bufferedImage.getSubimage((int)((SPRITES_SPAN.width * framePosX + SPRITE_OFFSET.width)/SCALING),
                (int)((SPRITES_SPAN.height * framePosY + SPRITE_OFFSET.height)/SCALING),
                (int)(SPRITE_SIZE.width/SCALING), (int)(SPRITE_SIZE.height/SCALING));
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);

        Boolean alpha = MouseHelper.mouseOnImage(mousePoint, mapPos, image,
                new Point((int)(SPRITE_OFFSET.getWidth()/SCALING), (int)(SPRITE_OFFSET.getHeight()/SCALING)));
        if (alpha != null) return alpha;
        return false;
    }

    public static Rectangle calcClickBox(Character character) {
        Point charScreenPos = MapDrawCalculator.screenPositionWithHeight(character.getPosition());
        assert charScreenPos != null;
        return new Rectangle(charScreenPos.x - SPRITE_BASE.width/2, charScreenPos.y - SPRITE_BASE.height*2/3,
                SPRITE_SIZE.width/2, SPRITE_SIZE.height*2/3);
    }

    public static Character getClickedCharacter() {
        return clickedCharacter;
    }

    public static void resetAnimation(Character character) {
        CharSprite sprite = charSpriteSheetMap.get(character);
        sprite.zeroAnimationFrame();
    }

    public static boolean animationFinished(Character character) {
        CharSprite charSprite = charSpriteSheetMap.get(character);
        return charSprite.animationFinished();
    }

    public static boolean animationFinishedOrNotSingle(Character character) {
        CharSprite charSprite = charSpriteSheetMap.get(character);
        return charSprite.animationFinishedOrNotSingle();
    }

}
