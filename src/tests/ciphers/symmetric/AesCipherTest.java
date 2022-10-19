package tests.ciphers.symmetric;

import main.ciphers.symmetric.block.AesCipher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AesCipherTest {
    @Test
    void encrypt() {
        char[] key = {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6,
                0xab, 0xf7, 0x15, 0x88, 0x09, 0xcf, 0x4f, 0x3c};

        char[] plain = {0x32, 0x43, 0xf6, 0xa8, 0x88, 0x5a, 0x30, 0x8d, 0x31, 0x31, 0x98, 0xa2, 0xe0, 0x37, 0x07, 0x34};

        AesCipher aesCipher = new AesCipher(key);
        String encrypted = aesCipher.encrypt(String.valueOf(plain));
        System.out.println("Encrypted: " + String.valueOf(encrypted));

        String decrypted = aesCipher.decrypt(encrypted);
        System.out.println("Decrypted: " + String.valueOf(decrypted));

        Assertions.assertEquals(String.valueOf(plain), decrypted);
    }

    @Test
    void encrypt_text() {
        char[] key = {0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48};

        String text = "Hello world!!!";

        AesCipher aesCipher = new AesCipher(key);

        String encrypted = aesCipher.encrypt(text);

        String decrypted = aesCipher.decrypt(encrypted);

        Assertions.assertEquals(text, decrypted.substring(0, text.length()));
    }

    @Test
    void encrypt_largeText() {
        char[] key = {0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48, 0x48};

        String text = "Hello world!!!,Hello world!!!,Hello world!!!,Hello world!!!";

        AesCipher aesCipher = new AesCipher(key);

        String encrypted = aesCipher.encrypt(text);

        String decrypted = aesCipher.decrypt(encrypted);
        System.out.println(text.length());
        System.out.println(decrypted.length());

        Assertions.assertEquals(text, decrypted.substring(0, text.length()));
    }
}
