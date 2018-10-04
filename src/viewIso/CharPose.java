package viewIso;

public enum CharPose {
    IDLE(0, 4, true);

    private int startFrame, framesCount;
    private boolean reversible;

    CharPose(int startFrame, int framesCount, boolean reversible) {
        this.startFrame = startFrame;
        this.framesCount = framesCount;
        this.reversible = reversible;
    }

    public int getStartFrame() {
        return startFrame;
    }

    public int getFramesCount() {
        return framesCount;
    }

    public boolean isReversible() {
        return reversible;
    }
}
