package model.ai;

import isoview.characters.CharsDrawer;
import model.EventType;
import model.actions.ActionQueuer;
import model.actions.attack.AttackActioner;
import model.actions.attack.BodyPart;
import model.character.CharState;
import model.character.Character;

public class AIActioner {

    public static void nextAIAction(Character aiCharacter) {
        Character enemy = EnemyChooser.chooseVisibleEnemy(aiCharacter);
        if (enemy != null) {
            AttackActioner.scheduleGoAndAttack(aiCharacter, enemy, BodyPart.BODY);
            System.out.println(aiCharacter.getName() + " attacks");
        } else
            EnemyFinder.scheduleFinding(aiCharacter);
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
