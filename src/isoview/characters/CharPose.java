package isoview.characters;

public enum CharPose {
    IDLE(0, 4, 3, true, false),
    RUN(4, 8, 999, false, false),
    ATTACK(12, 4, 2, false, true),
    ATTACK_RANGE(28, 4, 3, false, true),
    HIT(16, 2, 3, true, true),
    DEATH(18, 6, 3, false, true),
    DEAD(23, 1, 999, false, true)
    ;

    private int startFrame, framesCount, delay;
    private boolean reversible, single;

    CharPose(int startFrame, int framesCount, int delay, boolean reversible, boolean single) {
        this.startFrame = startFrame;
        this.framesCount = framesCount;
        this.delay = delay;
        this.reversible = reversible;
        this.single = single;
    }

    public int getStartFrame() {
        return startFrame;
    }

    public int getFramesCount() {
        return framesCount;
    }

    public int getDelay() {
        return delay;
    }

    public boolean isReversible() {
        return reversible;
    }

    public boolean isSingle() {
        return single;
    }
}
