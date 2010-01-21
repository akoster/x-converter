package xcon.pilot;

public class Hypotheker {

	public static void main(String[] args) {

		// input
		double rente = 4.5;
		double inleg = 0;
		int looptijd = 23;
		double doeltegoed = 167500;
		//
		
		double premie = 0;
		double tegoed = 0;
		int[] tegoeden = new int[looptijd + 1];
		while (tegoed < doeltegoed) {

			tegoed = inleg;
			premie++;
			
			for (int jaar = 1; jaar <= looptijd; jaar++) {

				double jaarpremie = 12 * premie;
				tegoed = tegoed + jaarpremie;
				double jaarrente = (rente * tegoed) / 100;
				tegoed += jaarrente;

				tegoeden[jaar] = (int) tegoed;
			}
			
		}
		
		System.out.println("rente=" + rente);
		System.out.println("inleg=" + inleg);
		System.out.println("looptijd=" + looptijd);
		System.out.println("doeltegoed=" + doeltegoed);
		System.out.println("premie=" + premie);
		
		print("Jaar", "Tegoed");
		for (int jaar = 1; jaar <= looptijd; jaar++) {
			print(jaar, tegoeden[jaar]);
		}
		
	}

	private static void print(Object... args) {

		StringBuilder output = new StringBuilder();
		for (Object arg : args) {
			output.append(arg).append("\t");
		}
		System.out.println(output.toString());
	}

}
