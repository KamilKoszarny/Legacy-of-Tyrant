package model.actions;

import model.Battle;
import model.character.Character;
import viewIso.ClickMenusDrawer;
import viewIso.panel.CharPanelViewer;

public class CharsChooser {

    public static void chooseCharacter(Character clickedCharacter) {
        for (Character character : Battle.getCharacters()) {
            character.setChosen(false);
        }
        if (clickedCharacter != null) {
            clickedCharacter.setChosen(true);
            CharPanelViewer.refreshCharInventory(clickedCharacter.getItems().getInventory());
        }

        Battle.setChosenCharacter(clickedCharacter);
        ClickMenusDrawer.hideMenus(true);
    }
}