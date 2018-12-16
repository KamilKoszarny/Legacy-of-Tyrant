package model.actions;

import model.Battle;
import model.character.Character;

public class CharsChooser {

    public static void chooseCharacter(Character clickedCharacter) {
        for (Character character : Battle.getCharacters()) {
            character.setChosen(false);
        }
        clickedCharacter.setChosen(true);
        Battle.setChosenCharacter(clickedCharacter);
    }
}