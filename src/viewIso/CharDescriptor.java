package viewIso;

import javafx.scene.control.Label;
import model.character.Character;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CharDescriptor {

    List<Label> charBasicLabels;
    private List<Character> characters;
    private List<Character> chosenCharacters;

    public CharDescriptor(List<Label> charBasicLabels, List<Character> characters) {
        this.charBasicLabels = charBasicLabels;
        this.characters = characters;
        characters.get(0).setChosen(true);
    }

    void refresh() {
        if (calcChosenCharacters() == 1) {
            showBasicLabels(chosenCharacters.get(0));
        }
    }

    private int calcChosenCharacters(){
        int chosenCount = 0;
        chosenCharacters = new ArrayList<>();
        for (Character character: characters) {
            if (character.isChosen()) {
                chosenCount++;
                chosenCharacters.add(character);
            }
        }
        return chosenCount;
    }

    private void showBasicLabels(Character character){
        for (Label label: charBasicLabels) {
            String basicParameter = getBasicParameter(label, character);
            label.setText(basicParameter);
        }
    }

    private String getBasicParameter(Label label, Character character) {
        String param = "";
        String getterName = "get" + label.getId().substring(0, 1).toUpperCase() + label.getId().substring(1);
        java.lang.reflect.Method method;
        try {
            method = character.getClass().getMethod(getterName);
            param = method.invoke(character).toString();
        } catch (SecurityException | NoSuchMethodException e) {} catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return param;
    }
}
