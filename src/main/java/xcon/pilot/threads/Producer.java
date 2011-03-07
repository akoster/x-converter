package xcon.pilot.threads;

import java.util.Vector;

public class Producer extends Thread {
	static final int MAXQUEUE = 5;
	private Vector messages = new Vector();

	public void run() {
		try {
			while (true) {
				putMessage();
				sleep(1000);
			}
		} catch (InterruptedException e) {
		}
	}

	private synchronized void putMessage() throws InterruptedException {

		while (messages.size() == MAXQUEUE) {
			wait();
		}
		messages.addElement(new java.util.Date().toString());
		notifyAll();
	}

	// Called by Consumer
	public synchronized String getMessage() throws InterruptedException {
		notify();
		while (messages.size() == 0) {
			wait();
		}
		String message = (String) messages.firstElement();
		messages.removeElement(message);
		return message;
	}
}
