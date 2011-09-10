package xcon.handle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Application which determines which processes have a handle on a file or
 * directory. Pass the file or directory to check as the first application
 * parameter.
 * 
 * This application uses handle.exe, which can be downloaded here:
 * http://technet.microsoft.com/en-us/sysinternals/bb896655
 * 
 * Copy handle.exe to C:/Program Files/handle/
 * 
 * For the Runtime.exec() code I looked at:
 * http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html?page=2
 * 
 * @author Adriaan
 */
public class Handle {

	private static final String HANDLE_PATH = "C:/Program Files/handle/handle.exe";
	private static final String DEFAULT_TARGET = "C:\\WINDOWS";
	private static boolean handleFound;

	public static void main(String[] args) throws IOException,
			InterruptedException {

		checkOS();
		String fileName = getFileName(args);
		Process proc = executeCommand(fileName);
		readResults(fileName, proc);
		checkTermination(proc);
		if (handleFound) {
			System.out.println("One or more handles found on " + fileName);
		}
		else {
			System.out.println("No handles found on " + fileName);
		}
	}

	private static void checkOS() {

		String osName = System.getProperty("os.name");
		if (!osName.contains("Windows")) {
			throw new IllegalStateException("Can only run under Windows");
		}
	}

	private static String getFileName(String[] args) {

		String fileName;
		if (args != null && args.length > 0) {
			fileName = args[0];
		} else {
			fileName = DEFAULT_TARGET;
		}
		return fileName;
	}

	private static Process executeCommand(String fileName) throws IOException {

		String[] cmd = new String[] { HANDLE_PATH, fileName };
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(cmd);
		return proc;
	}

	private static void readResults(final String fileName, final Process proc) {

		Thread errorHandler = new Thread() {
			public void run() {
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(proc.getErrorStream()));
					String line = null;
					while ((line = br.readLine()) != null) {
						System.err.println(line);
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		};

		Thread outputHandler = new Thread() {
			public void run() {
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(proc.getInputStream()));
					String line = null;
					while ((line = br.readLine()) != null) {

						if (line.endsWith(fileName)) {
							handleFound = true;
							System.out.println(line);
						}
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		};
		errorHandler.start();
		outputHandler.start();
	}

	private static void checkTermination(final Process proc)
			throws InterruptedException {
		int exitVal = proc.waitFor();
		if (exitVal != 0) {
			System.err.println("Exit value: " + exitVal);
		}
	}
}
