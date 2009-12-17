package xcon.pilot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

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
