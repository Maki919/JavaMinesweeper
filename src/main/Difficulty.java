package main;

public enum Difficulty {
    EASY(8, 10, 610, 530),
    MEDIUM(15, 40, 1025, 990);

    private final int fieldMaxIndex;
    private final int bombsMaxAmount;
    private final int frameHeight;
    private final int frameWidth;

    Difficulty (int fieldMaxIndex, int bombsMaxAmount, int frameHeight, int frameWidth) {
        this.fieldMaxIndex = fieldMaxIndex;
        this.bombsMaxAmount = bombsMaxAmount;
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
    }
    public int getFieldMaxIndex() { return fieldMaxIndex; }
    public int getBombsMaxAmount() { return bombsMaxAmount; }
    public int getFrameHeight() { return frameHeight; }
    public int getFrameWidth() { return frameWidth; }
}
