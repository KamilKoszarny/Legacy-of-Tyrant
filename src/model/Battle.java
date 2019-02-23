package model;

import javafx.scene.paint.Color;
import main.App;
import model.character.CharState;
import model.character.Character;
import model.actions.movement.CharMover;
import model.map.*;
import model.map.visibility.VisibilityCalculator;
import viewIso.characters.CharsDrawer;

import java.util.ArrayList;
import java.util.List;

public class Battle {

    private static Map map;
    private static List<Character> characters;
    private static Character chosenCharacter;
    private static Color playerColor, enemyColor;
    private static int timer = 0;
    private static boolean turnMode = false;

    public Battle(Map map, List<Character> characters, Color playerColor, Color enemyColor) {
        Battle.map = map;
        Battle.characters = characters;
        Battle.playerColor = playerColor;
        Battle.enemyColor = enemyColor;
    }

    public static void update(int ms) {
        updateAnimation(ms);
        VisibilityCalculator.updateViews();
        if (TurnModeActivator.shouldStart())
            TurnsTracker.startTurnMode();
        if (Battle.isTurnMode())
            TurnsTracker.updateAI();
        App.showAndResetTime("ViewsUpdate", 2);
    }

    private static void updateAnimation(int ms) {
        for (Character character: characters) {
            if (character.getStats().getSpeed() > 0) {
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

    public static List<Character> getAliveCharacters() {
        return filterAliveCharacters(characters);
    }

    private static List<Character> filterAliveCharacters(List<Character> characters) {
        List<Character> aliveCharacters = new ArrayList<>();
        for (Character character: characters) {
            if (!character.getState().equals(CharState.DEAD) && ! character.getState().equals(CharState.DEATH))
                aliveCharacters.add(character);
        }
        return aliveCharacters;
    }

    public static List<Character> getPlayerAliveCharacters() {
        return filterAliveCharacters(getPlayerCharacters());
    }

    public static List<Character> getPlayerCharacters() {
        return getTeamCharacters(playerColor);
    }

    public static List<Character> getEnemyAliveCharacters() {
        return filterAliveCharacters(getEnemyCharacters());
    }

    public static List<Character> getEnemyCharacters() {
        return getTeamCharacters(enemyColor);
    }

    public static List<Character> getTeamCharacters(Color color) {
        List<Character> teamCharacters = new ArrayList<>();
        for (Character character: characters) {
            if (character.getColor().equals(color))
                teamCharacters.add(character);
        }
        return teamCharacters;
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
