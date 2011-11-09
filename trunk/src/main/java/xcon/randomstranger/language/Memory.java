package xcon.randomstranger.language;

import java.util.LinkedList;

public class Memory {

    private final LinkedList<Discussion> longTermMemory = new LinkedList<Discussion>();
    private Discussion currentDiscussion;
    private long startTimeMillis;

    public Memory() {
        this.startTimeMillis = System.currentTimeMillis();
    }

    public boolean inDiscussion() {
        return currentDiscussion != null;
    }

    public void killDiscussion() {
        if (inDiscussion()) {
            longTermMemory.addLast(currentDiscussion);
            currentDiscussion = null;
        }
    }
    
    public Discussion currentDiscussion() {
        if (!inDiscussion()) {
            currentDiscussion = new Discussion();
        }
        return currentDiscussion;
    }

    public long getAloneSeconds() {
        long aloneSeconds;
        Discussion last = currentDiscussion;
        if (last == null) {
            last = longTermMemory.peekLast();
        }
        if (last == null) {
            aloneSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
        }
        else {
            aloneSeconds = last.getAloneSeconds();
        }
        return aloneSeconds;

    }
}
