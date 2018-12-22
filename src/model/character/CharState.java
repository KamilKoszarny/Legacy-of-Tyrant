package model.character;

import viewIso.characters.CharPose;

public enum CharState {
    IDLE(CharPose.IDLE),
    RUN(CharPose.RUN),
    ATTACK(CharPose.ATTACK),
    ATTACK_RANGE(CharPose.ATTACK_RANGE),
    HIT(CharPose.HIT),
    DEATH(CharPose.DEATH),
    DEAD(CharPose.DEAD),
    ;

    private CharPose pose;

    CharState(CharPose pose) {
        this.pose = pose;
    }

    public CharPose getPose() {
        return pose;
    }

    public static CharState stateAfter(CharState stateBefore) {
        switch (stateBefore) {
            case ATTACK:
            case ATTACK_RANGE:
            case HIT:
                return IDLE;
            case DEATH:
                return DEAD;
        }
        return stateBefore;
    }
}
