package model;

import model.actions.movement.CharMover;
import model.ai.AIActioner;
import model.character.Character;
import model.character.Stats;

import java.util.Random;

public class TurnsTracker {

    private static final int WAIT_AP_COST = 10;

    private static Character activeCharacter;
    private static double activeCharAPBefore;

    public static void startTurnMode() {
        CharMover.haltAllChars();
        calcStartAPs();
        nextCharacter();

        Battle.setTurnMode(true);
    }

    public static void updateAI() {
        if (aiCharActive()) {
            handleAIAction();
        }
    }

    private static void handleAIAction() {
        if(AIActioner.turnFinished(activeCharacter)) {
            System.out.println(activeCharacter.getName() + " finished turn");
            nextTurn();
        } else if (AIActioner.actionFinished(activeCharacter))
            AIActioner.nextAIAction(activeCharacter);
    }

    public static void nextTurn() {
        updateStatsAfterTurn();
        nextCharacter();
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

    private static void updateStatsAfterTurn() {
        double currentAP = activeCharacter.getStats().getActionPoints();
        activeCharacter.getStats().addVigor(currentAP / 5);
        double costAPOfLastTurn = activeCharAPBefore - currentAP;
        if (costAPOfLastTurn == 0) {
            costAPOfLastTurn = WAIT_AP_COST;
//            activeCharacter.getStats().subtractActionPoints(WAIT_AP_COST);
        }
        double gainAPPerChar = costAPOfLastTurn / (Battle.getAliveCharacters().size() - 1);
        for (Character character: Battle.getAliveCharacters()) {
            if (!character.equals(activeCharacter))
                character.getStats().addActionPoints(gainAPPerChar);

            System.out.println(character.getName() + " AP: " + character.getStats().getActionPoints());
        }
    }

    private static void nextCharacter() {
        Character nextChar = nextMostAPCharacter();
        activeCharacter = nextChar;
        activeCharAPBefore = activeCharacter.getStats().getActionPoints();
        if (Battle.getPlayerColor().equals(nextChar.getColor()))
            Battle.setChosenCharacter(nextChar);
        else
            AIActioner.nextAIAction(activeCharacter);
    }

    private static Character nextMostAPCharacter() {
        Character mostAPCharacter = null;
        double maxAP = -100;
        for (Character character: Battle.getAliveCharacters()) {
            double charAP = character.getStats().getActionPoints();
            if (charAP > maxAP && character != activeCharacter) {
                maxAP = charAP;
                mostAPCharacter = character;
            }
        }
        return mostAPCharacter;
    }

    public static boolean aiCharActive() {
        return !Battle.getPlayerCharacters().contains(activeCharacter);
    }

    public static Character getActiveCharacter() {
        return activeCharacter;
    }

    public static boolean activeCharChosen() {
        return Battle.getChosenCharacter() != null && Battle.getChosenCharacter().equals(TurnsTracker.getActiveCharacter());
    }

    public static boolean activeCharMoved() {
        return activeCharacter.getStats().getActionPoints() < activeCharAPBefore;
    }

    public static boolean activeCharOutOfAP() {
        return activeCharacter.getStats().getActionPoints() <= 0;
    }

    public static void chooseActiveChar() {
        if (Battle.getPlayerColor().equals(activeCharacter.getColor()))
            Battle.setChosenCharacter(activeCharacter);
    }
}
