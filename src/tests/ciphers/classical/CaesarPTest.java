package tests.ciphers.classical;

import main.ciphers.classical.CaesarP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CaesarPTest {
    private String key = "xsbiacqpkuhndwtlvzergmjyfo";

    @Test
    void encrypt_basicAlphabet_encryptedAlphabet() {
        int substitutionKey = 3;
        CaesarP caesarP = new CaesarP(key, substitutionKey);

        String toEncrypt = "abcdefghijklmnopqrstuvwxyz";
        String expected = "iacqpkuhndwtlvzergmjyfoxsb";

        String actual = caesarP.encrypt(toEncrypt);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void encrypt_basicAlphabetUpperCaseKey_encryptedAlphabet() {
        int substitutionKey = 3;
        CaesarP caesarP = new CaesarP(key.toUpperCase(), substitutionKey);

        String toEncrypt = "abcdefghijklmnopqrstuvwxyz";
        String expected = "iacqpkuhndwtlvzergmjyfoxsb";

        String actual = caesarP.encrypt(toEncrypt);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void decrypt_encryptedAlphabet_basicAlphabet() {
        int substitutionKey = 3;
        CaesarP caesarP = new CaesarP(key, substitutionKey);

        String expected = "abcdefghijklmnopqrstuvwxyz";
        String toDecrypt = "iacqpkuhndwtlvzergmjyfoxsb";

        String actual = caesarP.decrypt(toDecrypt);

        Assertions.assertEquals(expected, actual);
    }


    private void testPermutation() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        List<Character> chars = alphabet.chars()
                .mapToObj(ch -> (char)ch)
                .collect(Collectors.toList());
        Collections.shuffle(chars);

        System.out.println(chars.toString()
                .substring(1, 3 * chars.size() - 1)
                .replaceAll(", ", ""));

    }
}
