package xcon.randomstranger.language;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Discussion {

    private final LinkedList<Utterance> utterances = new LinkedList<Utterance>();
    private Set<String> names = new HashSet<String>();
    private Set<String> words = new HashSet<String>();

    public final void addUtterance(Utterance utterance) {
        utterances.addLast(utterance);
        names.add(utterance.getName());
        words.addAll(Arrays.asList(utterance.getMessage().split("\\s+")));
    }

    public String getLastSpeaker() {
        Utterance last = utterances.peekLast();
        if (last != null) {
            return last.getName();
        }
        else {
            return null;
        }
    }

    public boolean lastSpokenBy(String name) {
        String lastSpeaker = getLastSpeaker();
        return lastSpeaker != null && lastSpeaker.equals(name);
    }

    public Utterance getLastUtterance() {
        return utterances.peekLast();
    }

    public long getSilenceSeconds() {
        return (System.currentTimeMillis() - utterances.peekLast().getTimeMillis()) / 1000;
    }

    public long getAloneSeconds() {
        long aloneSeconds;
        Iterator<Utterance> it = utterances.descendingIterator();
        String name = null;
        Utterance oldestBySameSpeaker = null;
        while (it.hasNext()) {
            Utterance utterance = it.next();
            if (name == null) {
                name = utterance.getName();
            }
            else if (!utterance.getName().equals(name)) {
                break;
            }
            oldestBySameSpeaker = utterance;
        }
        if (oldestBySameSpeaker == null) {
            aloneSeconds = 0;
        }
        else {
            aloneSeconds = (System.currentTimeMillis() - oldestBySameSpeaker.getTimeMillis()) / 1000;
        }
        return aloneSeconds;
    }

    public Collection<String> getNames() {
        return names;
    }

    public Collection<String> getWords() {
        return words;
    }
}
