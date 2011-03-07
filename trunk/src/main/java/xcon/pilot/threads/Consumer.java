package xcon.pilot.threads;

// see: http://oreilly.com/catalog/expjava/excerpt/index.html
public class Consumer extends Thread {
	Producer producer;
	String name;

	Consumer(String name, Producer producer) {
		this.producer = producer;
		this.name = name;
	}

	public void run() {
		try {
			while (true) {
				String message = producer.getMessage();
				System.out.println(name + " got message: " + message);
				sleep(2000);
			}
		} catch (InterruptedException e) {
		}
	}

	public static void main(String args[]) {
		Producer producer = new Producer();
		producer.start();

		// Start two this time
		new Consumer("One", producer).start();
		new Consumer("Two", producer).start();
	}
}
