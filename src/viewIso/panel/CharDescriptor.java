package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.scene.control.Label;
import model.character.Character;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CharDescriptor {

    private List<Label> charLabels;
    private List<Character> characters;

    public CharDescriptor(Panel panel, List<Character> characters) {
        charLabels = panel.getCharLabels();
        this.characters = characters;
    }

    public void refresh() {
        List<Character> chosenCharacters = calcChosenCharacters();
        if (chosenCharacters.size() == 1) {
            showLabels(chosenCharacters.get(0));
        }
    }

    private void showLabels(Character character){
        for (Label label: charLabels) {
            String basicParameter = getParameter(label, character);
            label.setText(basicParameter);
        }
    }

    private String getParameter(Label label, Character character) {
        String value = "";
        String parameter = label.getId().substring(0,label.getId().length() - 5);
        String getterName = "get" + parameter.substring(0, 1).toUpperCase() + parameter.substring(1, parameter.length());
        java.lang.reflect.Method method;
        Class returnType = null;
        try {
            method = character.getClass().getMethod(getterName);
            returnType = method.getReturnType();
            value = method.invoke(character).toString();
        } catch (SecurityException | NoSuchMethodException e) {} catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (returnType != null && returnType.equals(Double.TYPE)) {
            Double doubleValue = Double.parseDouble(value);
            doubleValue = (double) Math.round(doubleValue * 10.) / 10;
            value = doubleValue.toString();
        }

        value = CharParameter.signByName(parameter) + value;

        return value;
    }

    private List<Character> calcChosenCharacters(){
        List<Character> chosenCharacters = new ArrayList<>();
        for (Character character: characters) {
            if (character.isChosen()) {
                chosenCharacters.add(character);
            }
        }
        return chosenCharacters;
    }
}
