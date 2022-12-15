package ciphers.symmetric.block;

import ciphers.AbstractCipher;
import utils.ciphers.symmetric.AesCipherUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.ciphers.symmetric.AesCipherUtils.*;

public class AesCipher extends AbstractCipher {
    private final int NB = 4;
    private final int NR = 10;
    private final char[] key;

    public AesCipher(char[] key) {
        this.key = key;
    }

    @Override
    public String encrypt(String msg) {
      return crypt(msg, true);
    }

    @Override
    public String decrypt(String msg) {
        return crypt(msg, false);
    }

    private String crypt(String msg, boolean encrypt) {
        long[] w = new long[4 * NB * (NR + 1)];
        keyExpansion(key, w);

//        while (msg.length()%16!=0) {
//           msg+="\u0000";
//        }

        char[] msgBytes = new char[((int) Math.ceil(msg.length()/(double)16))*16];
        Arrays.fill(msgBytes, (char) 0);
        char[] msgChars = msg.toCharArray();
        System.arraycopy(msgChars, 0, msgBytes, 0, msgChars.length);

        List<Character> cipheredMsg = new ArrayList<>();

        int blockLen = 16;
        for (int i = 0; i < msgBytes.length; i +=blockLen  ) {
            char[] ciphered = cryptBlock(Arrays.copyOfRange(msgBytes, i, i+blockLen), w, encrypt);
            for (char b : ciphered) {
                cipheredMsg.add(b);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Character c :
                cipheredMsg) {
            sb.append(String.format("%02X", (int) c));
        }

        System.out.println(sb);

        StringBuilder sb2 = new StringBuilder();
        for (Character c :
                cipheredMsg) {
            sb2.append(c);
        }

        return sb2.toString();
    }

    private char[] cryptBlock(char[] msgBytes, long[] w, boolean encrypt) {
        char[][] state = new char[4][NB];
        char[] cryptedBytes = new char[msgBytes.length];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < NB; j++) {
                state[i][j] = msgBytes[i + 4 * j];
            }
        }

        if (encrypt) {
            // initial round
            addRoundKey(state, w);

            for (int i = 1; i <= NR; i++) {
                // Transformation rounds
                subBytes(state, false);
                shiftBytes(state, false);
                if (i < NR) {
                    state = mixColumns(state, false);
                }
                addRoundKey(state, Arrays.copyOfRange(w, 4 * i, w.length - 1));
            }
        } else {
            // initial round
            addRoundKey(state, Arrays.copyOfRange(w, NB * NR, w.length - 1));
            for (int i = NR - 1; i >= 0; i--) {
                // Transformation rounds
                subBytes(state, true);
                shiftBytes(state, true);
                addRoundKey(state, Arrays.copyOfRange(w, NB * i, w.length - 1));
                if (i > 0) {
                    state = mixColumns(state, true);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < NB; j++) {
                cryptedBytes[i + 4 * j] = state[i][j];
            }
        }

        return cryptedBytes;
    }

    private void keyExpansion(char[] key, long[] w) {
        long temp;
        long[] arcon = new long[4];

        int i = 0;
        int NK = 4;
        while (i < NK) {
            w[i] = convertToInt(new long[]{key[4 * i], key[4 * i + 1], key[4 * i + 2], key[4 * i + 3]});
            i++;
        }

        i = NK;

        while (i < NB * (NR + 1)) {
            temp = w[i - 1];
            if (i % NK == 0) {
                rcon(arcon, i / NK);
                temp = subWord(rotWord(temp)) ^ convertToInt(arcon);
            }
            w[i] = w[i - NK] ^ temp;
            i++;
        }
    }

    private char[][] mixColumns(char[][] state, boolean inv) {
        char[][] columns = swapMatrix(state);
        for (int i = 0; i < 4; i++) {
            mixColumn(columns[i], inv);
        }
        return swapMatrix(columns);
    }

    private void mixColumn(char[] column, boolean inv) {
        char a = column[0];
        char b = column[1];
        char c = column[2];
        char d = column[3];

        if (inv) {
            char x = dbl((char) (a ^ b ^ c ^ d));
            char y = dbl((char) (x ^ a ^ c));
            char z = dbl((char) (x ^ b ^ d));

            column[0] = (char) (dbl((char) (y ^ a ^ b)) ^ b ^ c ^ d);  /* 14a + 11b + 13c + 9d */
            column[1] = (char) (dbl((char) (z ^ b ^ c)) ^ c ^ d ^ a);  /* 14b + 11c + 13d + 9a */
            column[2] = (char) (dbl((char) (y ^ c ^ d)) ^ d ^ a ^ b);  /* 14c + 11d + 13a + 9b */
            column[3] = (char) (dbl((char) (z ^ d ^ a)) ^ a ^ b ^ c);  /* 14d + 11a + 13b + 9c */
        } else {
            column[0] = (char) (dbl((char) (a ^ b)) ^ b ^ c ^ d);  /* 2a + 3b + c + d */
            column[1] = (char) (dbl((char) (b ^ c)) ^ c ^ d ^ a);  /* 2b + 3c + d + a */
            column[2] = (char) (dbl((char) (c ^ d)) ^ d ^ a ^ b);  /* 2c + 3d + a + b */
            column[3] = (char) (dbl((char) (d ^ a)) ^ a ^ b ^ c);  /* 2d + 3a + b + c */
        }
    }

    private char dbl(char b) {
        return (char) ((b << 1) ^ (0x11b & -(b >> 7)));
    }

    private void shiftBytes(char[][] state, boolean inv) {
        for (int i = 1; i < state.length; i++) {
            if (inv) {
                state[i] = shiftRow(state[i], NB - i);
            } else {
                state[i] = shiftRow(state[i], i);
            }
        }
    }

    private char[] shiftRow(char[] row, int shift) {
        char[] temp = new char[row.length];
        for (int i = 0; i < row.length; i++) {
            temp[i] = row[(i + shift) % row.length];
        }
        return temp;
    }

    private void subBytes(char[][] state, boolean inv) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < NB; j++) {
                if (inv) {
                    state[i][j] = (char) AesCipherUtils.ISBOX[state[i][j] / 16][state[i][j] % 16];
                } else {
                    state[i][j] = (char) AesCipherUtils.SBOX[state[i][j] / 16][state[i][j] % 16];
                }
            }
        }
    }

    private void addRoundKey(char[][] state, long[] w) {
        long[][] keyState = transformKey(w);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < NB; j++) {
                state[i][j] = (char) (state[i][j] ^ keyState[i][j]);
            }
        }
    }

    private long subWord(long temp) {
        long[] tBytes = intToBytes(temp);

        int l = tBytes.length;
        long[] cBytes = new long[l];

        for (int i = 0; i < l; i++) {
            cBytes[i] = AesCipherUtils.SBOX[(int) (tBytes[i] / 16L)][(int) (tBytes[i] % 16L)];
        }

        return convertToInt(cBytes);
    }

    private long rotWord(long temp) {
        long[] tBytes = intToBytes(temp);

        long t = tBytes[0];
        tBytes[0] = tBytes[1];
        tBytes[1] = tBytes[2];
        tBytes[2] = tBytes[3];
        tBytes[3] = t;

        return convertToInt(tBytes);
    }

    private void rcon(long[] arcon, int n) {
        int c = 1;

        for (int i = 0; i < n - 1; i++) {
            c = (c << 1) ^ (0x11b & -(c >> 7));
        }

        arcon[0] = c;
        arcon[1] = 0;
        arcon[2] = 0;
        arcon[3] = 0;
    }
}
