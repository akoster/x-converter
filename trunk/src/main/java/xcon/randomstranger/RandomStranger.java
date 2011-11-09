package xcon.randomstranger;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import xcon.randomstranger.language.Discussion;
import xcon.randomstranger.language.Memory;
import xcon.randomstranger.language.Utterance;
import xcon.randomstranger.mood.Mood;
import xcon.randomstranger.mood.Temper;
import xcon.randomstranger.role.Actor;
import xcon.randomstranger.role.Receiver;

public class RandomStranger extends Actor implements Receiver {

    private final String name;
    private final PrintWriter out;
    private final Memory memory;
    private final Temper temper;

    public RandomStranger(OutputStream outputStream) {
        this("random_stranger", outputStream);
    }

    public RandomStranger(String name, OutputStream outputStream) {
        this.name = name;
        this.out = new PrintWriter(outputStream, true);
        this.memory = new Memory();
        this.temper = new Temper();
        start();
        System.out.println(name + " has started");
    }

    public void receive(String name, String input) {
        memory.currentDiscussion().addUtterance(new Utterance(name, input));
    }

    public void send(String message) {
        out.println(String.format("%s : %s", name, message));
        memory.currentDiscussion().addUtterance(new Utterance(name, message));
    }

    @Override
    public void run() {
        while (!isStopped) {

            pause(TimeUnit.SECONDS, 2);

            if (memory.inDiscussion()) {
                continueDiscussion();
            }
            else {
                startDiscussion();
            }
        }
    }

    private void startDiscussion() {
        if (memory.getAloneSeconds() > 40) {
            if (temper.get(Mood.TALKATIVE) > 20) {
                send("anybody there?");
            }
        }
    }

    private void continueDiscussion() {
        Discussion discussion = memory.currentDiscussion();
        if (!discussion.lastSpokenBy(name) && discussion.getSilenceSeconds() > 2) {
            String response = determineResponse(discussion);
            if (response != null) {
                send(response);
            }
        }
        else if (discussion.lastSpokenBy(name) && memory.getAloneSeconds() > 60) {
            send("I guess nobody wants to talk to me.");
            memory.killDiscussion();
        }
        else if (discussion.lastSpokenBy(name) && discussion.getSilenceSeconds() > 30) {
            send("helloooooo?");
        }
    }

    private String determineResponse(Discussion discussion) {
        String response = null;
        Utterance lastUtterance = discussion.getLastUtterance();
        if (discussion.getSilenceSeconds() > (lastUtterance.complexity() / 2)) {
            if (lastUtterance.getMessage().contains("?")) {
                response = "I have no idea...";
            }
            else if (lastUtterance.complexity() < 3) {
                response = "sup?";
            }
            else if (lastUtterance.complexity() < 10) {
                response = "you could say that";
            }
            else {
                response = "hmmm.... interesting";
            }
        }
        return response;
    }

}
