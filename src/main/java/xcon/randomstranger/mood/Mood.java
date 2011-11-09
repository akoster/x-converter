package xcon.randomstranger.mood;

import java.util.Calendar;

public enum Mood {

    HAPPY, TALKATIVE;

    private static MoodDelta dayMoods = new DayMoodDelta();
    private static MoodDelta hourMoods = new HourMoodDelta();

    public int getDelta(Calendar cal) {
        return dayMoods.getDelta(this, cal.get(Calendar.DAY_OF_WEEK)) + hourMoods.getDelta(this, cal.get(Calendar.HOUR_OF_DAY));
    }
}
