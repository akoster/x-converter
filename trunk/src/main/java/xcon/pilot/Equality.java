package xcon.pilot;


public class Equality {

	public static void main(String[] args) {

		Long i = new Long(200);
		Long p = new Long(200);

		if (i == p)
			System.out.println("i and p are the same.");
		if (i != p)
			System.out.println("i and p are different.");
		if (i.equals(p))
			System.out.println("i and p contain the same value.");
	}

}
