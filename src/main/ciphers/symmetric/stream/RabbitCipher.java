package main.ciphers.symmetric.stream;

import main.ciphers.AbstractCipher;

public class RabbitCipher extends AbstractCipher {
    private long[] key;
    private long[] iv;

    // state variables
    private long[] X = new long[8];

    // counter variables
    private long[] C = new long[8];
    private long[] oldC = new long[8];

    private long[] A = new long[8];

    private long[] S = new long[16];

    // counter carry bit
    private int carry = 0;

    public RabbitCipher(long[] key) {
        this.key = key;
    }

    public RabbitCipher(long[] key, long[] iv) {
        this.key = key;
        this.iv = iv;
    }

    @Override
    public String encrypt(String msg) {
        return crypt(msg);
    }

    @Override
    public String decrypt(String msg) {
        return crypt(msg);
    }


    // key setup
    private void setupKey() {
        long[] subkeys = new long[8];

        // counter system constants
        A[0]=A[3]=A[6]=0x4D34D34D;
        A[1]=A[4]=A[7]=0xD34D34D3;
        A[2]=A[5]=0x34D34D34;

        // normalize key
        for (int i=0; i<7; i++) {
            subkeys[i] = key[2*i+1] | ((long) key[2 * i] << 16);
        }

        for (int i=0; i<8; i++) {
            if (i % 2 == 0) {
                X[i] = subkeys[(i+1)%8] | ( subkeys[i] << 16);
                C[i] = subkeys[(i+4)%8] | ( subkeys[(i + 5) % 8] << 16);
            } else {
                X[i] = subkeys[(i+5)%8] | ( subkeys[(i + 4) % 8] << 16);
                C[i] = subkeys[i] | ( subkeys[(i + 1) % 8] << 16);
            }
        }

        for (int i =0; i<4;i++) {
            nextState();
        }

        for (int i =0; i<8; i++) {
            C[(i+4)%8] ^=X[i];
        }
    }

    // iv setup
    private void setupIv() {
        long[] exIv = new long[] {
                iv[3] | iv[2] | iv[1] | (iv[0] << 32),
                iv[7] | iv[6] | iv[5] | (iv[4] << 32)
        };

        C[0]=C[4]^=exIv[0];
        C[2]=C[6]^=exIv[1];

        C[1]=C[5]^=(((exIv[1]>>16)<<16) | (exIv[0]>>16) );
        C[3]=C[7]^=((exIv[1]<<16) | ((exIv[0]<<16)>>16) );

        for(int i=0;i<4;i++){
            nextState();
        }
    }

    private void nextState() {
        counterUpdate();
        long[] g = new long[8];
        long temp;

        for(int i=0;i<8;i++) {
            temp = (X[i] + C[i]) % Integer.MAX_VALUE;
            temp = temp*temp;
            g[i] =  ((temp) ^ (temp >> 32))% Integer.MAX_VALUE;
        }

        for(int i=0;i<8;i++){
            if((i&1)==1)
                X[i] = g[i] + rotateLeft(g[(i+7)%8],8) + g[(i+6)%8];
            else
                X[i] = g[i] + rotateLeft(g[(i+7)%8],16) + rotateLeft(g[(i+6)%8],16);
        }
    }

    private void counterUpdate() {
        System.arraycopy(C, 0, oldC, 0, 8);

        for (int i=0; i<8; i++) {
            long temp = C[i];
            C[i] = (C[i]%Integer.MAX_VALUE + A[i]%Integer.MAX_VALUE + carry);
            carry = (C[i] < temp)?1:0;
        }
    }

    private long rotateLeft(long u, long v) {
        return (v%32 == 0)
                ? u
                : ((u << v) | (u >> (32 - v)));
    }
    //  I. key expansion
    //  II. system iteration
    //  III. counter modification
    // IV setup
    //  I. IV Addition
    //  II. System iteration
    // next-state g-function
    // counter system
    // extraction key
    // encryption/decryption

    private String crypt(String msg) {
        setupKey();
        if (iv!=null && iv.length>0) {
            setupIv();
        }

        byte[] b = hexStringToByteArray(msg);

        StringBuilder sb = new StringBuilder();
        for (int i =0; i<b.length; i++) {
           if (i%16==0) {
               nextBlock();
           }
            b[i] ^= S[i%16];
            sb.append(String.format("%02X ", b[i]));
        }

        return sb.toString();
    }

    private long[] nextBlock() {
        nextState();

        long x = X[0] ^ X[5] >>> 16;
        S[0] = (byte) x;
        S[1] = (byte)(x >> 8);

        x = X[0] >>> 16 ^ X[3];
        S[2] = (byte) x;
        S[3] = (byte)(x >> 8);

        x = X[2] ^ X[7] >>> 16;
        S[4] = (byte) x;
        S[5] = (byte)(x >> 8);

        x = X[2] >> 16 ^ X[5];
        S[6] = (byte) x;
        S[7] = (byte)(x >> 8);

        x = X[4] ^ X[1] >>> 16;
        S[8] = (byte) x;
        S[9] = (byte)(x >> 8);

        x = X[4] >>> 16 ^ X[7];
        S[10] = (byte) x;
        S[11] = (byte)(x >> 8);

        x = X[6] ^ X[3] >>> 16;
        S[12] = (byte) x;
        S[13] = (byte)(x >> 8);

        x = X[6] >>> 16 ^ X[1];
        S[14] = (byte) x;
        S[15] = (byte)(x >> 8);

        return S;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


}
