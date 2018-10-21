package model.character;

import viewIso.characters.CharPose;

public enum CharState {
    IDLE(CharPose.IDLE),
    RUN(CharPose.RUN);

    private CharPose pose;

    CharState(CharPose pose) {
        this.pose = pose;
    }

    public CharPose getPose() {
        return pose;
    }
}
