package xcon.randomstranger.mood;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import xcon.randomstranger.role.Actor;

public class Temper extends Actor {

    private Map<Mood, Integer> moodValues = new HashMap<Mood, Integer>();

    public Temper() {
        Calendar now = Calendar.getInstance();
        for (Mood mood : Mood.values()) {
            moodValues.put(mood, mood.getDelta(now));
        }
        start();
    }

    @Override
    public void run() {
        while (!isStopped) {
            pause(TimeUnit.MINUTES, 22);
            Calendar now = Calendar.getInstance();
            for (Mood mood : Mood.values()) {
                change(mood, mood.getDelta(now));
            }
        }
    }

    public synchronized int get(Mood mood) {
        return moodValues.get(mood);
    }

    public synchronized void set(Mood mood, int value) {
        moodValues.put(mood, value);
    }

    public synchronized void change(Mood mood, int delta) {
        moodValues.put(mood, (int) moodValues.get(mood) + delta);
    }
}
