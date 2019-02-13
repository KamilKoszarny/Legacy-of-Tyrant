package model.ai;

import model.BattleEvent;
import model.EventType;
import model.actions.ActionQueuer;
import model.character.CharState;
import model.character.Character;
import viewIso.characters.CharsDrawer;

public class AIActioner {

    public static void startAction(Character aiCharacter) {
        Character enemy = EnemyChooser.chooseEnemy(aiCharacter);
        if (enemy != null) {
            System.out.println(enemy.getName());
            ActionQueuer.clearEventQueue(aiCharacter);
            ActionQueuer.addEvent(aiCharacter, new BattleEvent(EventType.GO2ENEMY, enemy));
            ActionQueuer.addEvent(aiCharacter, new BattleEvent(EventType.ATTACK_BODY, enemy));
        }
    }

    public static boolean actionFinished(Character character) {
        return (character.getStats().getActionPoints() <= 0 && CharsDrawer.animationFinished(character)) ||
                (ActionQueuer.noMoreActions(character) && character.getState().equals(CharState.IDLE));
    }
}