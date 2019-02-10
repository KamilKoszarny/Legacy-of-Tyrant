package model;

import model.actions.attack.AttackActioner;
import model.actions.attack.BodyPart;

public enum EventType {
    NULL(false),
    MOVE_MAP(false),
    CHOOSE_CHARACTER(false),
    SHOW_CHAR2POINT,
    SHOW_CHAR2DOOR,
    SHOW_CHAR2CHEST,
    SHOW_CHAR2ENEMY,
    ATTACK_BODY,
    ATTACK_HEAD,
    ATTACK_ARMS,
    ATTACK_LEGS,
    SHOW_MAP_PIECE_INFO (false),
    ITEM_CLICK,
    DROP_ITEM,
    GIVE_ITEM,
    PICKUP_ITEM,
    OPEN_DOOR,
    CLOSE_DOOR,
    OPEN_CHEST,
    GO2OBJECT,
    GO2ENEMY,
    GO2CHAR,
    ;

    private boolean characterAction = true;

    EventType() {
    }

    EventType(boolean characterAction) {
        this.characterAction = characterAction;
    }

    public boolean isCharacterAction() {
        return characterAction;
    }
}
