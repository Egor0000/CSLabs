package tests.ciphers.asymmetric;

import main.ciphers.assymetric.RsaCipher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.StringJoiner;
import java.util.stream.Collectors;

public class RsaTests {
    @Test
    void encrypt() {
        RsaCipher rsaCipher = new RsaCipher(137, 193);

        String msg = "Hello world!!!";

        String encrypted = rsaCipher.encrypt("Hello world!!!");
        System.out.println(encrypted);

        String decrypted = rsaCipher.decrypt(encrypted);
        System.out.println(decrypted);

        Assertions.assertEquals(msg.chars()
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(" "))
                .trim(),  decrypted );
    }
}
