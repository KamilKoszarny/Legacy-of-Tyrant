package model;

import javafx.scene.paint.Color;
import model.actions.CharsChooser;
import model.character.Character;
import model.actions.movement.CharMover;
import model.map.*;
import viewIso.characters.CharsDrawer;

import java.util.List;

public class Battle {

    private static Map map;
    private static List<Character> characters;
    private static Character chosenCharacter;
    private static Color playerColor;
    private final CharsChooser charsChooser = new CharsChooser();
    private static int timer = 0;

    public Battle(Map map, List<Character> characters, Color playerColor) {
        Battle.map = map;
        Battle.characters = characters;
        Battle.playerColor = playerColor;
    }

    public static void updateCharacters(int ms) {
        for (Character character: characters) {
            if (character.getDestination() != null) {
                if (character.getPath().isEmpty())
                    CharMover.stopCharacter(character);
                else
                    CharMover.updateCharacterMove(character, ms);
            } else {
                CharsDrawer.nextFrame(character, timer);
            }
        }
    }

    public static void incrementTimer() {
        timer++;
    }

    public static Map getMap() {
        return map;
    }

    public static List<Character> getCharacters() {
        return characters;
    }

    public static Character getChosenCharacter() {
        return chosenCharacter;
    }

    public static void setChosenCharacter(Character chosenCharacter) {
        Battle.chosenCharacter = chosenCharacter;
    }

    public static Color getPlayerColor() {
        return playerColor;
    }
}
