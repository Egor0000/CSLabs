package ciphers.assymetric;

import ciphers.AbstractCipher;

import java.math.BigInteger;

public class RsaCipher extends AbstractCipher {
    private final long p;
    private final long q;
    private final BigInteger e = new BigInteger("65537");
    private BigInteger n;
    private BigInteger d;

    public RsaCipher(long p, long q) {
        this.p = p;
        this.q = q;
        keyGen();
    }

    public String encryptPrivate(String s) {
        char[] chars = s.toCharArray();

        StringBuilder sb = new StringBuilder();

        for (char aChar : chars) {
            BigInteger msg = BigInteger.valueOf((int) aChar);
            BigInteger c = msg.modPow(d, n);
            sb.append(c).append(" ");
        }


        return sb.toString().trim();
    }

    public String decryptPublic(String s) {
        String[] eChars = s.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String eChar : eChars) {
            BigInteger msg = new BigInteger(eChar).modPow(e, n);

            sb.append((char) msg.intValue());
        }
        return sb.toString().trim();
    }

    @Override
    public String encrypt(String s) {
        char[] chars = s.toCharArray();

        StringBuilder sb = new StringBuilder();

        for (char aChar : chars) {
            BigInteger msg = BigInteger.valueOf((int) aChar);
            BigInteger c = msg.modPow(e, n);
            sb.append(c).append(" ");
        }


        return sb.toString().trim();
    }

    @Override
    public String decrypt(String s) {
        String[] eChars = s.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String eChar : eChars) {
            BigInteger msg = new BigInteger(eChar).modPow(d, n);

            sb.append(msg).append(" ");
        }
        return sb.toString().trim();
    }

    private void keyGen() {
        BigInteger bigP = new BigInteger(String.valueOf(p));
        BigInteger bigQ = new BigInteger(String.valueOf(q));
        BigInteger totient = (bigP.subtract(BigInteger.ONE)).multiply(bigQ.subtract(BigInteger.ONE));

        n = bigP.multiply(bigQ);
        d = e.modInverse(totient);
    }

}
