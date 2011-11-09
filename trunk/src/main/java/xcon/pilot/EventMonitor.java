package xcon.pilot;

import java.util.LinkedList;

public class EventMonitor {

    private int minimumGroupSize;
    private long window;

    private LinkedList<Event> events = new LinkedList<Event>();

    public EventMonitor(int minimumGroupSize, long window) {
        this.minimumGroupSize = minimumGroupSize;
        this.window = window;
    }

    public void handle(Event newest) {
        
        System.out.println(newest);
        events.addLast(newest);
        if (events.size() == minimumGroupSize) {
            Event oldest = events.peekFirst();
            if (newest.getTimestamp() - oldest.getTimestamp() < window) {
                System.out.println("Group starter: " + oldest);
            }
            events.removeFirst();
        }
    }

    public static class Event {
        private final long timestamp;

        Event(long timestamp) {
            this.timestamp = timestamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String toString() {
            return String.valueOf(timestamp);
        }
    }

    public static void main(String[] args) {

        EventMonitor monitor = new EventMonitor(5, 15);
        feedEventData(monitor);
    }

    private static void feedEventData(EventMonitor monitor) {
        long timestamp = 0;
        for (int i = 0; i < 20; i++) {

            long interval = 1 + (long) (Math.random() * 10);
            timestamp = timestamp + interval;
            monitor.handle(new Event(timestamp));
        }
    }
}
