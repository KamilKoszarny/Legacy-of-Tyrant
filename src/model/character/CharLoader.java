package model.character;


import javafx.scene.image.Image;
import model.character.Character;

import java.util.Random;

public class CharLoader {

    private static final int MAX_PORTRAIT_PER_STYLE_COUNT = 4;

    public static Image loadPortrait(Character character) {
        String pathString = "/portraits/" + character.getName() + ".png";

        boolean notFound = true;
        while (notFound) {
            notFound = false;
            try {
                Image image = new Image(pathString);
            } catch (Exception e) {
                pathString = "/portraits/" + (character.isMale() ? "m" : "w") + character.getCharClass().getPortaitLetter() +
                        (new Random().nextInt(MAX_PORTRAIT_PER_STYLE_COUNT) + 1) + ".png";
                notFound = true;
            }
        }

        return new Image(pathString);
    }
}
