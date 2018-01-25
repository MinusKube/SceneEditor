package fr.minuskube.editor.animation;

public class FrameRepeat {

    private int start;
    private int end;
    private int times;

    private int currentTimes = 0;

    public FrameRepeat(int start, int end, int times) {
        this.start = start;
        this.end = end;
        this.times = times;
    }

    public int getStart() { return start; }
    public int getEnd() { return end; }
    public int getTimes() { return times; }

    public int getCurrentTimes() { return currentTimes; }
    public void setCurrentTimes(int currentTimes) { this.currentTimes = currentTimes; }

}
