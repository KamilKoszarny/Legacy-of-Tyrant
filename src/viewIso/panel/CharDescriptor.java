package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.character.Character;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CharDescriptor {

    private Panel panel;
    private List<Character> characters;

    public CharDescriptor(Panel panel, List<Character> characters) {
        this.panel = panel;
        this.characters = characters;
    }

    public void refresh() {
        List<Character> chosenCharacters = calcChosenCharacters();
        if (chosenCharacters.size() == 1) {
            refreshLabels(chosenCharacters.get(0));
            refreshBars(chosenCharacters.get(0));
        }
    }

    private void refreshBars(Character character) {
        int baseWidth = (int) panel.getCharBars().get(0).getPrefWidth();
        ProgressBar hpBar = panel.getCharBars().get(0), manaBar = panel.getCharBars().get(1), vigorBar = panel.getCharBars().get(2);

        hpBar.setMinWidth(baseWidth * character.getHitPoints() / 100.);
        hpBar.setMaxWidth(baseWidth * character.getHitPoints() / 100.);
        hpBar.setTranslateY(baseWidth / 2 - hpBar.getWidth() / 2);
        hpBar.setProgress((double)character.getCurrentHitPoints() / (double)character.getHitPoints());
        manaBar.setMinWidth(baseWidth * character.getMana() * 5 / 100.);
        manaBar.setMaxWidth(baseWidth * character.getMana() * 5 / 100.);
        manaBar.setTranslateY(baseWidth / 2 - manaBar.getWidth() / 2);
        manaBar.setProgress((double)character.getCurrentMana() / (double)character.getMana());
        vigorBar.setMinWidth(baseWidth * character.getVigor() / 100.);
        vigorBar.setMaxWidth(baseWidth * character.getVigor() / 100.);
        vigorBar.setTranslateY(baseWidth / 2 - vigorBar.getWidth() / 2);
        vigorBar.setProgress((double)character.getCurrentVigor() / (double)character.getVigor());

//        for (ProgressBar bar: panel.getCharBars()) {
//            bar.setTranslateY(baseWidth/2 - bar.getWidth() / 2);
//        }
    }

    private void refreshLabels(Character character){
        for (Label label: panel.getCharLabels()) {
            String parameterText = getParameterText(label, character);
            label.setText(parameterText);
        }
    }

    private String getParameterText(Label label, Character character) {
        String parameter = label.getId().substring(0,label.getId().length() - 5);

        String parText = CharParameter.signByName(parameter) + findValue(character, parameter);

        if (CharParameter.isWithCurrent(parameter)){
            parameter = "current" + parameter.substring(0, 1).toUpperCase() + parameter.substring(1, parameter.length());
            parText = findValue(character, parameter) + "/" + parText;
        }

        return parText;
    }

    private String findValue(Character character, String parameter) {
        String value = "";
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
