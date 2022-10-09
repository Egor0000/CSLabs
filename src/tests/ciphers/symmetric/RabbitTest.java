package tests.ciphers.symmetric;

import main.ciphers.symmetric.RabbitCipher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RabbitTest {
    @Test
    void encrypt() {
//        long[] key = new long[]{0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
//                                0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30};

//        long[] key = new long[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

        long[] key = new long[16];
        Arrays.fill(key, 0x00);

        long[] iv = new long[8];
        Arrays.fill(key, 0x00);

        RabbitCipher rabbitCipher = new RabbitCipher(key);

        String toEncrypt = "Hello world";

        String encrypted = rabbitCipher.encrypt(toHex(toEncrypt));
        String decrypted = rabbitCipher.decrypt(encrypted.replaceAll(" ", ""));

        StringBuilder sb = new StringBuilder();
        String[] strs = decrypted.split(" ");
        for (int i =0; i< strs.length; i++) {
            sb.append((char) Integer.parseInt(strs[i], 16));
        }

        Assertions.assertEquals(toEncrypt, sb.toString());
    }

    public String toHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }
}
