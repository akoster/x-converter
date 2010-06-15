package xcon.pilot;
import java.util.zip.Adler32;

public class StringSwitch {

	public static void main(String[] args) {

		String arg;
		if (args == null || args.length == 0) {
			arg = "stackoverflow";
		} else {
			arg = args[0];
		}

		Adler32 algorithm = new Adler32();
		algorithm.update(arg.getBytes());
		int hash = (int) algorithm.getValue();

		switch (hash) {
		case 647693707:
			System.out.println("Programming Q & A");
			break;
		case 145556093:
			System.out.println("Narwhals and bacon");
			break;
		case 193790704:
			System.out.println("How do they work?");
			break;
		}
	}
}
