package viewIso;

import javafx.scene.image.Image;

import java.awt.*;

public class CharSprite {
    private Image charSpriteSheet;
    private int dir = 0, animationFrame = 0;
    private CharPose charPose = CharPose.IDLE;
    boolean reverse = false;

    public CharSprite(Image charSpriteSheet) {
        this.charSpriteSheet = charSpriteSheet;
    }

    public Point getFramePos() {
        int x = charPose.getStartFrame() + animationFrame;
        int y = (dir + 6)%8;
        return new Point(x, y);
    }

    public void nextFrame() {
        if (charPose.isReversible()) {
            if (animationFrame == charPose.getFramesCount() - 1)
                reverse = true;
            if (animationFrame == 0)
                reverse = false;
        }

        if (reverse) {
            animationFrame --;
        } else {
            animationFrame += 1;
            animationFrame %= charPose.getFramesCount();
        }
    }

    public Image getCharSpriteSheet() {
        return charSpriteSheet;
    }

    public void setCharSpriteSheet(Image charSpriteSheet) {
        this.charSpriteSheet = charSpriteSheet;
    }


}
