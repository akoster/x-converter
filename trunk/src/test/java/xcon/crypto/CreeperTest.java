package xcon.crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CreeperTest {

	@Test
	public void testRoundtrip() {

		String key = "secret";
		String plainText = "Hello world!";

		System.out.println("Plaintext");
		System.out.println(plainText);

		System.out.println("Encrypting");
		String crypotext = Creeper.encrypt(plainText, key);
		System.out.println("Cryptotext");
		System.out.println(crypotext);

		System.out.println("Decrypting");
		String result = Creeper.decrypt(crypotext, key);
		System.out.println("Plaintext");
		System.out.println(result);
		assertEquals(plainText, result);
	}

	@Test
	public void testStandardDeviation() {

		String key = "secret";
		String plainText = "China heeft een Tibetaanse lama tot meer dan acht jaar "
				+ "cel veroordeeld wegens verduistering en het in bezit hebben "
				+ "van munitie. Volgens zijn advocaat ontkent de monnik, Phurbu "
				+ "Tsering Rinpoche, alle beschuldigingen. De man die wordt "
				+ "omschreven als een 'levende boeddha', werd gearresteerd "
				+ "nadat nonnen uit zijn klooster hadden geprotesteerd tegen "
				+ "de Chinese overheersing. Na dit protest volgde een reeks van "
				+ "anti-Chinese demonstraties in 2008. Phurbu Tsering Rinpoche "
				+ "werd aangehouden op 18 mei 2008, een paar dagen na een "
				+ "demonstratie van zeker tachtig nonnen in Ganzi. Ze "
				+ "protesteerden tegen de officiële campagne voor "
				+ "'patriottistische heropvoeding' in hun kloosters, de nonnen "
				+ "moesten voortaan de Tibetaanse spirituele leider dalai lama "
				+ "veroordelen. \"De rechtszaak was in april en de uitspraak "
				+ "zou een paar dagen later zijn maar werd zonder een "
				+ "duidelijke reden uitgesteld tot 23 december,\" zo vertelde "
				+ "de advocaat van Rinpoche aan het Franse persbureau AFP. "
				+ "Volgens de raadsman heeft de monnik nog niet besloten of hij "
				+ "in hoger beroep gaat. De advocaat benadrukte dat hij zijn "
				+ "cliënt niet had kunnen bijstaan in de rechtszaal. ";

		System.out.println("Plaintext");
		System.out.println(plainText);
		String stripped = plainText.replaceAll("\\W", "").replaceAll("\\d", "")
				.toUpperCase();
		CreeperApp.analyzeText(stripped, true);

		System.out.println("Encrypting");
		String crypotext = Creeper.encrypt(plainText, key);
		System.out.println("Cryptotext");
		System.out.println(crypotext);
		CreeperApp.analyzeText(crypotext, true);

		System.out.println("Decrypting");
		String result = Creeper.decrypt(crypotext, key);
		System.out.println("Plaintext");
		System.out.println(result);
		assertEquals(plainText, result);

		System.out.println("Random");
		String random = "";
		for (int i = 0; i < crypotext.length(); i++) {
			random += (char) (65 + Math.random() * 26);
		}
		CreeperApp.analyzeText(random, true);

		System.out.println("Even");
		String even = "";
		for (int i = 0; i < crypotext.length(); i++) {
			even += (char) (i % 26 + 65);
		}
		CreeperApp.analyzeText(even, true);
	}

	@Test
	public void testBruteForceHack() {

		String key = "secret";
		String plainText = "What is the reason for existence?";

		System.out.println("Plaintext");
		System.out.println(plainText);

		System.out.println("Encrypting");
		String cryptex = Creeper.encrypt(plainText, key);
		System.out.println("Cryptex");
		System.out.println(cryptex);

		System.out.println("Brute force");

		int cryptexSize = cryptex.length();
		String decryptAttempt = "";
		int keyLength = 1;
		long start = System.currentTimeMillis();
		long TWO_MINUTES = 2 * 60 * 1000;
		long duration = 0;
		while (!recognizedAsPlainText(decryptAttempt) && duration < TWO_MINUTES) {

			if (cryptexSize % keyLength != 0) {
				// cryptex does not match keysize
				continue;
			}
			for (int startPos = 0; startPos < cryptexSize; startPos++) {

				String guessedKey = guessKey(startPos, keyLength);
				decryptAttempt = Creeper.decrypt(cryptex, guessedKey);

			}
			System.out.println("keyLength=" + keyLength + " decryptAttempt=" + decryptAttempt);
			keyLength++;
			duration = System.currentTimeMillis() - start;
		}

		System.out.println("Plaintext?");
		System.out.println(decryptAttempt);
		// hack succeeded?
		assertEquals(plainText, decryptAttempt);
	}

	private String guessKey(int startPos, int keyLength) {

		String guessedKey = "";

		// encode the startPos as an nGram
		//
		// the positional value of the first nGram digit
		long posValue = (long) Math.pow(Creeper.CRYPTEX_CHAR_RANGE,
				keyLength - 1);
		long remain = startPos;
		for (int i = 0; i < keyLength; i++) {

			// convert characters to make the cryptex more 'readable'
			guessedKey += (char) ((remain / posValue) + Creeper.CRYPTEX_MIN_CHAR);
			remain = remain % posValue;
			posValue = posValue / Creeper.CRYPTEX_CHAR_RANGE;
		}
		return guessedKey;
	}

	@Test
	public void test() {
		int cryptexSize = 33;
		int keyLength = 4;
		if (cryptexSize % keyLength > 0) {
			System.out.println("no match");
		} else {
			System.out.println("match");
		}
	}

	private boolean recognizedAsPlainText(String decryptAttempt) {
		// XXX: look for dictionary words
		return (CreeperApp.analyzeText(decryptAttempt, false) > 0.9);
	}
}
