package xcon.randomstranger.mood;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DayMoodDelta extends MoodDelta {

    private static final Map<Integer, int[]> moods = new HashMap<Integer, int[]>();

    static {
        moods.put(Calendar.MONDAY, new int[] {-20, -20});
        moods.put(Calendar.TUESDAY, new int[] {0, 0});
        moods.put(Calendar.WEDNESDAY, new int[] {0, 0});
        moods.put(Calendar.THURSDAY, new int[] {0, 0});
        moods.put(Calendar.FRIDAY, new int[] {5, 5});
        moods.put(Calendar.SATURDAY, new int[] {30, 30});
        moods.put(Calendar.SUNDAY, new int[] {20, 220});
    }

    public DayMoodDelta() {
        super(moods);
    }
}
