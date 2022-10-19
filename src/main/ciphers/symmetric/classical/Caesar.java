package main.ciphers.symmetric.classical;

import main.ciphers.AbstractCipher;

public class Caesar extends AbstractCipher {
    private final int substitutionKey;

    public Caesar(int substitutionKey) {

        this.substitutionKey = prepareKey(substitutionKey);
    }

    @Override
    public String encrypt(String msg) {
        return encrypt(msg, this.substitutionKey);
    }

    @Override
    public String decrypt(String msg) {
        return encrypt(msg, prepareKey(-this.substitutionKey));
    }

    private String encrypt(String msg, int substitutionKey) {
        return new String(msg.chars()
                .map(ch -> {
                    // for numbers
                    if (Character.isDigit(ch)) {
                        return ((ch - 48 + substitutionKey) % 10 + 48);
                    }

                    // for latin alphabet
                    if (Character.isLetter(ch)) {
                        // check letter is lower or upper case
                        int codeOffset = (Math.floorDiv(ch, 97) < 1) ? 65 : 97;
                        return ((ch - codeOffset + substitutionKey) % (alphabet.length())) + codeOffset;
                    }

                    return ch;
                })
                .toArray(), 0, msg.length());
    }

}
