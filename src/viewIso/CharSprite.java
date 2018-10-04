package viewIso;

import javafx.scene.image.Image;

public class CharSprite {
    Image charSpriteSheet;

    public CharSprite(Image charSpriteSheet) {
        this.charSpriteSheet = charSpriteSheet;
    }

    public Image getCharSpriteSheet() {
        return charSpriteSheet;
    }

    public void setCharSpriteSheet(Image charSpriteSheet) {
        this.charSpriteSheet = charSpriteSheet;
    }
}
