package model;

import model.character.Character;
import model.character.Stats;

import java.util.Random;

public class TurnsTracker {

    private static Character activeCharacter;
    private static double activeCharAPBefore;

    public static void startTurnMode() {
        calcStartAPs();
        Battle.setTurnMode(true);
    }

    private static void calcStartAPs() {
        calcAPsMax();
        Random random = new Random();
        for (Character character: Battle.getAliveCharacters()) {
            Stats stats = character.getStats();
            double startAPPercent = 30 + 30 * (stats.getDexterity() / 100.) + 40 * random.nextDouble();
            float startAP = (float) (stats.getActionPointsMax() * startAPPercent / 100);
            stats.setActionPoints(startAP);
        }
    }

    private static void calcAPsMax() {
        for (Character character: Battle.getAliveCharacters()) {
            calcAPMax(character);
        }
    }

    public static void calcAPMax(Character character) {
        Stats stats = character.getStats();
        float maxAP = (float) (50 + 50 * (1 - Math.pow((100 - stats.getVigor()) / 100., 2)));
        stats.setActionPointsMax(maxAP);
    }

    public static void nextTurn() {
        updateStatsAfterTurn();
        nextCharacter();
        activeCharAPBefore = activeCharacter.getStats().getActionPoints();
    }

    private static void updateStatsAfterTurn() {
        double costAPOfLastTurn = activeCharAPBefore - activeCharacter.getStats().getActionPoints();
        double gainAPPerChar = costAPOfLastTurn / (Battle.getAliveCharacters().size() - 1);
        for (Character character: Battle.getAliveCharacters()) {
            if (!character.equals(activeCharacter))
                character.getStats().addActionPoints(gainAPPerChar);
        }
    }

    public static void nextCharacter() {
        Character nextChar = mostAPCharacter();
        activeCharacter = nextChar;
        Battle.setChosenCharacter(nextChar);
    }

    private static Character mostAPCharacter() {
        Character mostAPCharacter = null;
        double maxAP = -100;
        for (Character character: Battle.getCharacters()) {
            double charAP = character.getStats().getActionPoints();
            if (charAP > maxAP) {
                maxAP = charAP;
                mostAPCharacter = character;
            }
        }
        return mostAPCharacter;
    }
}
