package viewIso.characters;

import javafx.scene.image.Image;

public class CharSprite {
    private Image charSpriteSheet;
    private int animationFrame = 0;
    private CharPose charPose = CharPose.IDLE;
    private boolean reverse = false, start = true;

    public CharSprite(Image charSpriteSheet) {
        this.charSpriteSheet = charSpriteSheet;
    }

    public int getFramePos() {
        return charPose.getStartFrame() + animationFrame;
    }

    public boolean nextFrame() {
        if (charPose.equals(CharPose.DEAD))
            System.out.println();

        if (charPose.isSingle()) {
            if (animationFrame == charPose.getFramesCount() - 1) {
                start = true;
                return false;
            }
        }

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
        return true;
    }

    public Image getCharSpriteSheet() {
        return charSpriteSheet;
    }

    public void setCharSpriteSheet(Image charSpriteSheet) {
        this.charSpriteSheet = charSpriteSheet;
    }

    public void setCharPose(CharPose charPose) {
        this.charPose = charPose;
        if (charPose.isSingle()) {
            if (start) {
                animationFrame = 0;
                start = false;
            }
        }
    }
}
