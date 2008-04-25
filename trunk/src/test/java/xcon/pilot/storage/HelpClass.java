package xcon.pilot.storage;

import org.apache.log4j.Logger;

public class HelpClass implements StartInterface{

	 private static final Logger LOG = Logger.getLogger(HelpClass.class);
	 
	
	
	private void handleHelp() {
    System.out.println("Commands:");
    System.out.println("set <key>: setCapacity");
    System.out.println("read <key>: reads the value for the key");
    System.out.println("store <key <value> : stores the value under the key");
    System.out.println("dump: dumps the contents of the storage");
    System.out.println("impl <implementation>: change the storage implementation");
    System.out.println("quit: exits the application");
    System.out.println("help: shows this help sheet");

}


	public void run(String command) {
		handleHelp();
	}

}
