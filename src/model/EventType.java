package model;

public enum EventType {
    NULL,
    MOVE_MAP,
    SHOW_MAP_PIECE_INFO ,
    CHOOSE_CHARACTER,
    SHOW_CHAR2POINT(true),
    SHOW_CHAR2DOOR(true),
    SHOW_CHAR2CHEST(true),
    SHOW_CHAR2ENEMY(true),
    ATTACK,
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
    LOOK4ENEMY,
    ;

    private boolean playerAction = false;

    EventType() {
    }

    EventType(boolean playerAction) {
        this.playerAction = playerAction;
    }

    public boolean isPlayerAction() {
        return playerAction;
    }


}
