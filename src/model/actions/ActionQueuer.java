package model.actions;

import model.Battle;
import model.BattleEvent;
import model.EventType;
import model.actions.attack.AttackCalculator;
import model.actions.movement.Char2ObjectMover;
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

    public static void clearEventQueue(Character character) {
        character2BattleEventsQueueMap.get(character).clear();
    }

    public static BattleEvent getNextEvent(Character character) {
        Queue<BattleEvent> charEvents = character2BattleEventsQueueMap.get(character);
        if (charEvents == null || charEvents.isEmpty())
            return null;
        return nextReadyEvent(character, charEvents);
    }

    private static BattleEvent nextReadyEvent(Character character, Queue<BattleEvent> charEvents) {
        BattleEvent event = null;
        if (charEvents.peek() != null && eventReady(character, charEvents.element())) {
            event = charEvents.remove();
        }
        return event;
    }

    private static boolean eventReady(Character character, BattleEvent battleEvent) {
        switch (battleEvent.getType()) {
            case GO2OBJECT:
            case GO2ENEMY:
            case LOOK4ENEMY:
                return true;
            case OPEN_DOOR:
            case CLOSE_DOOR:
            case OPEN_CHEST:
                return Char2ObjectMover.closeToObject(character, battleEvent.getObject());
            case ATTACK:
                return AttackCalculator.isInRange(character, battleEvent.getSubjectCharacter());
        }
        return false;
    }

    public static boolean noMoreActions(Character character) {
        return character2BattleEventsQueueMap.get(character).size() == 0;
    }

    public static EventType checkNextEventType(Character character) {
        BattleEvent battleEvent = character2BattleEventsQueueMap.get(character).peek();
        if (battleEvent != null)
            return battleEvent.getType();
        return null;
    }
}
