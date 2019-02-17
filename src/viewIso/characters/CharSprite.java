package viewIso.characters;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class CharSprite {

    private Image charSpriteSheet;
    private int animationFrame = 0;
    private CharPose charPose = CharPose.IDLE;
    private boolean reverse = false;

    CharSprite(BufferedImage charSpriteSheet) {
        this.charSpriteSheet = SwingFXUtils.toFXImage(charSpriteSheet, null);;
    }

    int getFramePos() {
        return charPose.getStartFrame() + animationFrame;
    }

    void nextFrame() {
        if (charPose.isReversible()) {
            if (animationFrame == charPose.getFramesCount() - 1)
                reverse = true;
            if (animationFrame == 0)
                reverse = false;
        } else
            reverse = false;

        if (reverse) {
            animationFrame --;
        } else {
            animationFrame += 1;
            animationFrame %= charPose.getFramesCount();
        }
    }

    boolean singleAnimationFinished() {
        return charPose.isSingle() && animationFrame == charPose.getFramesCount() - 1;
    }

    boolean animationFinishedOrNotSingle() {
        return !charPose.isSingle() || animationFrame == charPose.getFramesCount() - 1;
    }

    boolean animationFinished() {
        return animationFrame == charPose.getFramesCount() - 1;
    }

    Image getCharSpriteSheet() {
        return charSpriteSheet;
    }

    public void setCharSpriteSheet(Image charSpriteSheet) {
        this.charSpriteSheet = charSpriteSheet;
    }

    void setCharPose(CharPose charPose) {
        this.charPose = charPose;
    }

    void zeroAnimationFrame() {
        animationFrame = 0;
    }
}
