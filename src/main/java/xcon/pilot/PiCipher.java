package xcon.pilot;

public class PiCipher {

    private static final String PI = "3141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117067982148086513282306647093844609550582231725359408128481117450284102701938521105559644622948954930381964428810975665933446128475648233786783165271201909145648566923460348610454326648213393607260249141273724587006606315588174881520920962829254091715364367892590360011330530548820466521384146951941511609433057270365759591953092186117381932611793105118548074462379962749567351885752724891227938183011949129833673362440656643086021394946395224737190702179860943702770539217176293176752384674818467669405132000568127145263560827785771342757789609173637178721468440901224953430146549585371050792279689258923542019956112129021960864034418159813629774771309960518707211349999998372978049951059731732816096318595024459455346908302642522308253344685035261931188171010003137838752886587533208381420617177669147303598253490428755468731159562863882353787593751957781857780532171226806613001927876611195909216420198";
    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    public static String encrypt(String text, int start) {
        return transform(text, start, true);
    }

    public static String decrypt(String text, int start) {
        return transform(text, start, false);
    }

    public static String transform(String text, int start, boolean encrypt) {
        int size = text.length();
        if ((start + size) > PI.length()) {
            throw new IllegalArgumentException("Start index too large");
        }
        char[] input = text.trim().toUpperCase().toCharArray();
        int[] key = createKey(start, size);
        char[] output = Vigenere.transform(input, key, encrypt);

        return new String(output);
    }

    private static int[] createKey(int start, int size) {
        int[] key = new int[size];
        int i = 0;
        while (i < key.length) {
            key[i] = toKeyChar(getThreeDigits(start + i));
            i++;
        }
        return key;
    }

    private static int[] getThreeDigits(int start) {
        return new int[]{DIGITS.indexOf(PI.charAt(start)), DIGITS.indexOf(PI.charAt(start + 1)), DIGITS.indexOf(PI.charAt(start + 2))};
    }

    private static int toKeyChar(int[] threeDigits) {
        int value = threeDigits[0] + threeDigits[1] + threeDigits[2];
        if (value == 0) {
            value = 1;
        }
        if (value == 27) {
            value = 26;
        }
        return value;
    }

    private static class Vigenere {

        static char[] transform(char[] input, int[] key, boolean encrypt) {
            char[] output = new char[input.length];
            for (int i = 0, k = 0; i < input.length; i++, k++) {
                k = rotate(k, key.length);
                output[i] = (char) ('A' + transform(ALPHA.indexOf(input[i]), key[k], encrypt));
            }
            return output;
        }

        private static int transform(int inputIndex, int keyIndex, boolean encrypt) {
            int outputIndex;
            if (encrypt) {
                outputIndex = (inputIndex + keyIndex + 1) % ALPHA.length();
            } else {
                outputIndex = inputIndex - keyIndex - 1;
                if (outputIndex < 0) {
                    outputIndex = ALPHA.length() + outputIndex;
                }
            }
            return outputIndex;
        }

        private static int rotate(int k, int length) {
            if (k == length) {
                k = 0;
            }
            return k;
        }
    }

    public static void main(String[] args) {
        int pin = 988;
        String encrypted = encrypt("HELLOWORLD", pin);
        System.out.println(encrypted);

        String decrypted = decrypt(encrypted, pin);
        System.out.println(decrypted);
    }

}
