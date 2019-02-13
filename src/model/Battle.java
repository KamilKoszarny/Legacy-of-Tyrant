package model;

import javafx.scene.paint.Color;
import main.App;
import model.character.CharState;
import model.character.Character;
import model.actions.movement.CharMover;
import model.map.*;
import model.map.lights.VisibilityCalculator;
import viewIso.characters.CharsDrawer;

import java.util.ArrayList;
import java.util.List;

public class Battle {

    private static Map map;
    private static List<Character> characters;
    private static Character chosenCharacter;
    private static Color playerColor;
    private static int timer = 0;
    private static boolean turnMode = false;

    public Battle(Map map, List<Character> characters, Color playerColor) {
        Battle.map = map;
        Battle.characters = characters;
        Battle.playerColor = playerColor;
    }

    public static void update(int ms) {
        for (Character character: characters) {
            if (character.getStats().getSpeed() > 0) {
                CharMover.updateCharacterMove(character, ms);
            } else {
                CharsDrawer.nextFrame(character, timer);
            }
        }
        VisibilityCalculator.updateViews();
        TurnsTracker.update();
        App.showAndResetTime("ViewsUpdate", 2);
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

    public static List<Character> getAliveCharacters() {
        return filterAliveCharacters(characters);
    }

    private static List<Character> filterAliveCharacters(List<Character> characters) {
        List<Character> aliveCharacters = new ArrayList<>();
        for (Character character: characters) {
            if (!character.getState().equals(CharState.DEAD))
                aliveCharacters.add(character);
        }
        return aliveCharacters;
    }

    public static List<Character> getPlayerCharacters() {
        List<Character> playerCharacters = new ArrayList<>();
        for (Character character: characters) {
            if (character.getColor().equals(playerColor))
                playerCharacters.add(character);
        }
        return playerCharacters;
    }

    public static List<Character> getPlayerAliveCharacters() {
        return filterAliveCharacters(getPlayerCharacters());
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

    public static boolean isTurnMode() {
        return turnMode;
    }

    public static void setTurnMode(boolean turnMode) {
        Battle.turnMode = turnMode;
    }
}
