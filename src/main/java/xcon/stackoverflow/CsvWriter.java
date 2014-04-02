package xcon.stackoverflow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

/**
 * Application demonstrating the creating, and reading of a CSV file.
 *
 * http://stackoverflow.com/questions/1516144/how-to-read-and-write-excel-file-in-java/1516235#1516235
 * and
 * http://stackoverflow.com/questions/1518704/get-file-modification-time/1518745#1518745
 */
public class CsvWriter {
	public static void main(String args[]) throws IOException {
		
		String fileName = "test.xls";
		File file = new File(fileName);
		
		PrintWriter out = new PrintWriter(new FileWriter(file));
		out.println("a,b,c,d");
		out.println("e,f,g,h");
		out.println("i,j,k,l");
		out.close();

		System.out.println("Before: " + new Date(file.lastModified()));
		
		System.in.read(); // wait until 'enter' is pressed
		
		// manual steps:
		// open test.xls with OpenOffice
        // close test.xls
        // press enter

        System.in.read(); // wait until 'enter' is pressed
		
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = in.readLine()) != null) {

			Scanner scanner = new Scanner(line);
			String sep = "";
			while (scanner.hasNext()) {
				System.out.println(sep + scanner.next());
				sep = ",";
			}
		}
		in.close();
		
		System.out.println("After: " + new Date(file.lastModified()));
		
		
	}
}
