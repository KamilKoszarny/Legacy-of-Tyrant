package model.actions;

import model.Battle;
import model.character.Character;
import viewIso.ClickMenusDrawer;
import viewIso.panel.CharDescriptor;

public class CharsChooser {

    public static void chooseCharacter(Character clickedCharacter) {
        for (Character character : Battle.getCharacters()) {
            character.setChosen(false);
        }
        clickedCharacter.setChosen(true);
        Battle.setChosenCharacter(clickedCharacter);
        CharDescriptor.refreshInventory(clickedCharacter);
        ClickMenusDrawer.hideMenus();
    }
}