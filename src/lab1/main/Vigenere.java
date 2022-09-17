package lab1.main;

public class Vigenere extends AbstractCipher {
    private final String key;
    private final int shift;

    public Vigenere(String key, int shift) throws Exception{
        this.key = key;
        this.shift = shift;

        if (shift == 0) {
            throw new Exception("Shift value should not be equal to 0");
        }
    }

    @Override
    public String encrypt(String msg) {
        return encrypt(msg, true);
    }

    @Override
    public String decrypt(String msg) {
        return encrypt(msg, false);
    }

    /**
     * Vigenere encryption function with shift
     */
    private String encrypt(String msg, boolean encrypt) {
        // cj = (mj + kJ) mod n
        // mj - index of message letter in the alphabete
        // kJ - index of key letter in the alphabet. J is the shifted index of key letter
        // n - count of letters in the alphabet

        int offset = (encrypt) ? 1 : -1;

        char[] chars = msg.toCharArray();
        char[] keyChars = key.toLowerCase().toCharArray();

        StringBuilder builder = new StringBuilder();

        for (int i = 0, k = 0; i < chars.length; i++) {
            char toEncrypt = Character.toLowerCase(chars[i]);
            if (Character.isLetter(toEncrypt)) {
                int encIdx = prepareKey(alphabet.indexOf((toEncrypt))
                        + offset * alphabet.indexOf((keyChars[k % keyChars.length]) + prepareKey(shift) - 1)
                        % alphabet.length()) % alphabet.length();
                char encChr = alphabet.charAt(encIdx);
                builder.append(Character.isUpperCase(chars[i])?Character.toUpperCase(encChr):encChr);
                k++;
            } else {
                builder.append(chars[i]);
            }
        }

        return builder.toString();
    }


}
