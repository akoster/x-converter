package xcon.pilot;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceReader {

	public static void main(String[] args) throws URISyntaxException, IOException {

		URL resourceUrl = URL.class.getResource("/xcon/pilot/ResourceReader.class");
		File resourceFile = new File(resourceUrl.toURI());
		RandomAccessFile resourceRAF = new RandomAccessFile(resourceFile, "rw");
		String line = resourceRAF.readLine();
		System.out.println("Read: " + line);

	}
}