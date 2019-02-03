package model.actions;

import model.Battle;
import model.BattleEvent;
import model.actions.movement.CharMover;
import model.actions.movement.ToObjectMover;
import model.character.Character;

import java.util.*;

public class ActionQueuer {

    private static Map<Character, Queue<BattleEvent>> character2BattleEventsQueueMap = new HashMap<>();

    public static void initCharacterQueues(List<Character> characters) {
        for (Character character: characters) {
            character2BattleEventsQueueMap.put(character, new LinkedList<>());
        }
    }

    public static void addEvent(BattleEvent battleEvent) {
        addEvent(Battle.getChosenCharacter(), battleEvent);
    }

    public static void addEvent(Character character, BattleEvent battleEvent) {
        battleEvent.setDoingCharacter(character);
        character2BattleEventsQueueMap.get(character).add(battleEvent);
    }

    public static void clearEventQueue() {
        character2BattleEventsQueueMap.get(Battle.getChosenCharacter()).clear();
    }

    public static BattleEvent getEvent(Character character) {
        Queue<BattleEvent> charEvents = character2BattleEventsQueueMap.get(character);
        if (charEvents == null || charEvents.isEmpty())
            return null;
        return nextEventIfReady(character, charEvents);
    }

    private static BattleEvent nextEventIfReady(Character character, Queue<BattleEvent> charEvents) {
        if (eventReady(character, charEvents.element()))
            return charEvents.remove();
        return null;
    }

    private static boolean eventReady(Character character, BattleEvent battleEvent) {
        switch (battleEvent.getType()) {
            case GO2OBJECT:
                return true;
            case OPEN_DOOR:
            case CLOSE_DOOR:
            case OPEN_CHEST:
                if (ToObjectMover.closeToObject(character, battleEvent.getObject())) {
                    CharMover.stopCharacter(character);

                    return true;
                }
        }
        return false;
    }
}
