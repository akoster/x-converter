package xcon.pilot.callback;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Main initializes in state WAITING and creates 'start' and 'stop' Trigger
 * threads that perform timed callbacks to fire(). The fire() method updates the
 * state first to RUNNING and then to FINISHED. The go() method waits for the
 * state to become RUNNING after which it prints some output until the state
 * becomes FINISHED.
 * 
 * In the current implementation the go() method performs a so-called 'active
 * wait loop' which is not optimal because it uses a Thread and CPU cycles even
 * though it is not doing anything useful.
 * 
 * How can you apply synchronization to avoid the active wait loop in go()?
 * 
 * Hint 1: use synchronized, wait() and notify()
 * 
 * Hint 2: it is generally best to keep the amount of synchronized code as small
 * as possible
 * 
 * @author Adriaan
 */
public class Main implements Trigger.Target {

	private enum State {
		WAITING, RUNNING, FINISHED
	}

	private State state = State.WAITING;

	public Main() {
		new Trigger("start", 5, this);
		new Trigger("stop", 10, this);
		try {
			go();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void go() throws InterruptedException {

		// XXX: active wait loop, hogs Thread and eats CPU
		while (state == State.WAITING) {
			TimeUnit.MILLISECONDS.sleep(50);
		}
		while (state == State.RUNNING) {
			System.out.println(new Date());
			TimeUnit.SECONDS.sleep(1);
		}
	}

	@Override
	public void fire(Trigger trigger) {

		switch (state) {
		case WAITING:
			state = State.RUNNING;
			break;
		case RUNNING:
			state = State.FINISHED;
			break;
		default:
			break;
		}
		System.out.println("Called by " + trigger + ", state = " + state);
	}

	public static void main(String[] args) {
		new Main();
	}
}
