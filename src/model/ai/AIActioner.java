package model.ai;

import model.BattleEvent;
import model.EventType;
import model.actions.ActionQueuer;
import model.actions.attack.AttackActioner;
import model.actions.attack.BodyPart;
import model.character.CharState;
import model.character.Character;
import viewIso.characters.CharsDrawer;

public class AIActioner {

    private static final int MAX_TURNS = 8;

    public static void nextAIAction(Character aiCharacter) {
        Character enemy = EnemyChooser.chooseVisibleEnemy(aiCharacter);
        if (enemy != null) {
            AttackActioner.goAndAttack(aiCharacter, enemy, BodyPart.BODY);
            System.out.println(aiCharacter.getName() + " attacks");
        } else
            turnAround(aiCharacter);
    }

    private static void turnAround(Character aiCharacter) {
        ActionQueuer.clearEventQueue(aiCharacter);
        for (int turns = 0; turns < MAX_TURNS; turns++) {
            ActionQueuer.addEvent(aiCharacter, new BattleEvent(EventType.LOOK4ENEMY));
        }
    }

    public static boolean actionFinished(Character character) {
        return CharsDrawer.animationFinished(character) && ActionQueuer.noMoreActions(character) || actionInterrupted(character);
    }

    public static boolean turnFinished(Character character) {
        return (character.getStats().getActionPoints() <= 0 && CharsDrawer.animationFinishedOrNotSingle(character)) ||
                (ActionQueuer.noMoreActions(character) && character.getState().equals(CharState.IDLE));
    }

    private static boolean actionInterrupted(Character character) {
        EventType eventType = ActionQueuer.checkNextEventType(character);
        if (eventType == null)
            return false;
        switch (eventType) {
            case LOOK4ENEMY: return EnemyChooser.chooseVisibleEnemy(character) != null;
        }
        return false;
    }
}