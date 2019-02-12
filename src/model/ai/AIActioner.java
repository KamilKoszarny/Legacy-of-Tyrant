package model.ai;

import model.BattleEvent;
import model.EventType;
import model.TurnsTracker;
import model.actions.ActionQueuer;
import model.character.Character;

public class AIActioner {

    public static void doAction(Character aiCharacter) {
        Character enemy = EnemyChooser.chooseEnemy(aiCharacter);
        if (enemy != null) {
            System.out.println(enemy.getName());
            ActionQueuer.clearEventQueue(aiCharacter);
            ActionQueuer.addEvent(aiCharacter, new BattleEvent(EventType.GO2ENEMY, enemy));
            ActionQueuer.addEvent(aiCharacter, new BattleEvent(EventType.ATTACK_BODY, enemy));
        }
    }


}