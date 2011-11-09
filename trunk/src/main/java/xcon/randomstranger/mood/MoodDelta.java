package xcon.randomstranger.mood;

import java.util.Map;

public abstract class MoodDelta {

    private Map<Integer, int[]> moods;

    MoodDelta(Map<Integer, int[]> moods) {
        this.moods = moods;
    }

    public int getDelta(Mood mood, int value) {
        Integer moodValue = moods.get(value)[mood.ordinal()];
        if (moodValue == null) {
            moodValue = 0;
        }
        return moodValue;
    }
}
