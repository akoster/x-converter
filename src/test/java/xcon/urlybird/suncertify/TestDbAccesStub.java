package xcon.urlybird.suncertify;

import xcon.urlybird.suncertify.db.RecordNotFoundException;
import xcon.urlybird.suncertify.db.SecurityException;
import xcon.urlybird.suncertify.db.stub.DbAccessStub;
import junit.framework.TestCase;

public class TestDbAccesStub extends TestCase {

	DbAccessStub stub;

	@Override
	protected void setUp() throws Exception {
		System.out.println("Setup");
		stub = new DbAccessStub();
	}

	public void notestCreateRecord() {
		fail("Not yet implemented");
	}

	public void notestLockRecord() throws Exception {

		UserThread1 ut1 = new UserThread1();
		UserThread2 ut2 = new UserThread2();

		Thread thread1 = new Thread(ut1);
		Thread thread2 = new Thread(ut2);

		thread1.start();
		thread2.start();

		/*
		 * assertEquals(true, ut1.isSucces()); assertEquals(true,
		 * ut2.isSucces());
		 */
		Thread.sleep(5000);
	}

	public void notestUnlock() {
		fail("Not yet implemented");
	}

	public void notestDeleteRecord() {
		fail("Not yet implemented");
	}

	public void notestFindByCriteria() {
		long[] foundRows = stub.findByCriteria(new String[] { "best resort",
				"amterdam" });
		assertEquals(3, foundRows.length);
	}

	public void notestReadRecord() {
		fail("Not yet implemented");
	}

	public void testUpdateRecord() throws InterruptedException {
		UserThread3 ut3 = new UserThread3();
		UserThread4 ut4 = new UserThread4();

		Thread thread3 = new Thread(ut3);
		Thread thread4 = new Thread(ut4);

		thread3.start();
		thread4.start();

		Thread.sleep(5000);

	}

	public class UserThread1 implements Runnable {

		private boolean succes = false;

		public boolean isSucces() {
			return succes;
		}

		@Override
		public void run() {
			try {
				System.out.println("running thread 1");
				long cookie = stub.lockRecord(1);
				this.succes = true;

				System.out.println(Thread.currentThread().getName()
						+ "has cookie " + cookie);
				System.out
						.println("waiting 2 seconds before releasing the lock");
				Thread.sleep(2000);
				stub.unlock(1, cookie);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public class UserThread2 implements Runnable {

		private boolean succes = false;

		@Override
		public void run() {
			// make sure that this thread is locking after the first thread
			try {

				try {
					Thread.sleep(1000);
					long cookie = stub.lockRecord(1);
					System.out.println(Thread.currentThread().getName()
							+ "has cookie " + cookie);
					System.out
							.println("waiting 6 seconds before releasing the loch");

					stub.unlock(1, cookie);
					succes = true;
				} catch (RecordNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
			}
			// TODO Auto-generated method stub

		}

		public boolean isSucces() {
			return succes;
		}

	}

	public class UserThread3 implements Runnable {

		@Override
		public void run() {
			// make sure that this thread is locking after the first thread

			try {
				long cookie = stub.lockRecord(1);
				System.out.println(Thread.currentThread().getName()
						+ "has cookie " + cookie);
				stub.updateRecord(1, new String[] { "golden tulip",
						"amsterdam", "2", "n", "120", "12-09-2009", null },
						cookie);
			} catch (RecordNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class UserThread4 implements Runnable {

		private boolean succes = false;

		@Override
		public void run() {
			// make sure that this thread is locking after the first thread

			try {
				Thread.sleep(1000);
				long cookie = stub.lockRecord(1);
				System.out.println(Thread.currentThread().getName()
						+ "has cookie " + cookie);
				try {
					stub.updateRecord(3, new String[] { "golden tulip",
							"amterdam", "2", "n", "120", "12-09-2009", null },
							cookie);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				succes = true;
			} catch (RecordNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// TODO Auto-generated method stub

		}

		public boolean isSucces() {
			return succes;
		}

	}

}
