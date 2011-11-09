package xcon.randomstranger.mood;

import java.util.HashMap;
import java.util.Map;

public class HourMoodDelta extends MoodDelta {

    private static final Map<Integer, int[]> moods = new HashMap<Integer, int[]>();

    static {
        moods.put(0, new int[] {0, -10});
        moods.put(1, new int[] {50, -30});
        moods.put(2, new int[] {50, -70});
        moods.put(3, new int[] {50, -80});
        moods.put(4, new int[] {50, -80});
        moods.put(5, new int[] {50, -80});
        moods.put(6, new int[] {50, -80});
        moods.put(7, new int[] {-10, -60});
        moods.put(8, new int[] {-20, -40});
        moods.put(9, new int[] {-10, -20});
        moods.put(10, new int[] {0, 0});
        moods.put(11, new int[] {10, 10});
        moods.put(12, new int[] {20, 10});
        moods.put(13, new int[] {10, 20});
        moods.put(14, new int[] {20, 20});
        moods.put(15, new int[] {20, 20});
        moods.put(16, new int[] {20, 20});
        moods.put(17, new int[] {20, 20});
        moods.put(18, new int[] {10, 10});
        moods.put(19, new int[] {10, 10});
        moods.put(20, new int[] {20, 30});
        moods.put(21, new int[] {30, 30});
        moods.put(22, new int[] {30, 30});
        moods.put(23, new int[] {20, 10});
        moods.put(24, new int[] {10, 0});
    }

    public HourMoodDelta() {
        super(moods);
    }

}
