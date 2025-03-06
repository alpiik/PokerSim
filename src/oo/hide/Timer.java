package oo.hide;

public class Timer {

    private long startTime;
    public Timer() {
        this.startTime = System.currentTimeMillis();
    }
    public String getPassedTime() {
        long cTime = System.currentTimeMillis();
        long eTime = cTime - startTime;
        double eSeconds = eTime / 1000.0;
        return String.format("%.2f seconds", eSeconds);
    }
}