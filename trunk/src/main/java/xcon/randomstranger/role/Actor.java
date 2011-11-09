package xcon.randomstranger.role;

import java.util.concurrent.TimeUnit;

public abstract class Actor implements Runnable {

    protected boolean isStopped;
    private long startTimeMillis;
    Thread thread;

    public Actor() {
        this.startTimeMillis = System.currentTimeMillis();
        thread = new Thread(this);
    }

    protected void start() {
        thread.start();
    }
    
    protected void pause(TimeUnit timeUnit, long duration) {
        try {
            timeUnit.sleep(duration);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void stop() {
        isStopped = true;
    }

    protected long runningTimeSeconds() {
        return (System.currentTimeMillis() - startTimeMillis) / 1000;
    }
}
