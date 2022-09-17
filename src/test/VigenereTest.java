package test;

import main.Vigenere;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VigenereTest {

    @Test
    void encrypt() throws Exception {
        String key = "lemon";
        int shift = 1;

        String toEncrypt = "attackatdawn";
        String expected = "lxfopvefrnhr";

        String encrypted = new Vigenere(key, shift).encrypt(toEncrypt);

        Assertions.assertEquals(expected, encrypted);
    }

    @Test
    void encrypt_shift2() throws Exception {
        String key = "lemon";
        int shift = 2;

        String toEncrypt = "attackatdawn";
        String expected = "mygpqwfgsois";

        String encrypted = new Vigenere(key, shift).encrypt(toEncrypt);

        Assertions.assertEquals(expected, encrypted);
    }

    @Test
    void encrypt_upperCaseKey() throws Exception {
        String key = "LEMON";
        int shift = 2;

        String toEncrypt = "attackatdawn";
        String expected = "mygpqwfgsois";

        String encrypted = new Vigenere(key, shift).encrypt(toEncrypt);

        Assertions.assertEquals(expected, encrypted);
    }

    @Test
    void encrypt_realText() throws Exception {
        String key = "lemon";
        int shift = 1;

        String toEncrypt = "The Vigenere cipher is a method of encrypting alphabetic text by using a series of interwoven Caesar ciphers, based on the letters of a keyword. It employs a form of polyalphabetic substitution. First described by Giovan Battista Bellaso in 1553, the cipher is easy to understand and implement, but it resisted all attempts to break it until 1863, three centuries later. This earned it the description le chiffrage indechiffrable (French for 'the indecipherable cipher'). Many people have tried to implement encryption schemes that are essentially Vigenere ciphers. In 1863, Friedrich Kasiski was the first to publish a general method of deciphering Vigenere ciphers. ";
        String expected = "Elq Jvrizsep gudupv ug n xifvbo sr sanvkdgtrs oyalmpremo hrix nm hdmzu n didwrd sr waeidkbgiz Qnpwmf ptttsed, fmgro sz hup pqhgpve cs l oqmjzvp. Wg pqbzbjw m tbcq at czpkoyalmpremo ghmwfwgfxuca. Qmdgg oieqetfqr oj Kucilr Nogemehn Mixznds ub 1553, gsi owcsid wf peem gz yzrrcwfoao ezr vxtxszprf, phe mf frdmehro exz nexqacew fc ocimy ve yzhvw 1863, xtfrp gqbgfvusf wefse. Elug rlvzsq tx fvr oieqettfwby pq qutjrfnri ubqpgtwsqvmpyp (Jdsanl rce 'elq waoiowcsidoowi owcsid'). Anyc bsbapq vngi ffvph fc vxtxszprf sanvkdgtsz gpsiysf elmh nci qgfprfwnwpk Jvrizsep gudupve. Wa 1863, Qvusqcmov Xlwugxt amg gsi rwedx fc cffxwfs e ssapvmz zpxtcq zj psptttsetrs Jvrizsep gudupve. ";

        String encrypted = new Vigenere(key, shift).encrypt(toEncrypt);

        Assertions.assertEquals(expected, encrypted);
    }

    @Test
    void decrypt_realText() throws Exception {
        String key = "lemon";
        int shift = 1;

        String expected = "The Vigenere cipher is a method of encrypting alphabetic text by using a series of interwoven Caesar ciphers, based on the letters of a keyword. It employs a form of polyalphabetic substitution. First described by Giovan Battista Bellaso in 1553, the cipher is easy to understand and implement, but it resisted all attempts to break it until 1863, three centuries later. This earned it the description le chiffrage indechiffrable (French for 'the indecipherable cipher'). Many people have tried to implement encryption schemes that are essentially Vigenere ciphers. In 1863, Friedrich Kasiski was the first to publish a general method of deciphering Vigenere ciphers. ";
        String toDecrypt = "Elq Jvrizsep gudupv ug n xifvbo sr sanvkdgtrs oyalmpremo hrix nm hdmzu n didwrd sr waeidkbgiz Qnpwmf ptttsed, fmgro sz hup pqhgpve cs l oqmjzvp. Wg pqbzbjw m tbcq at czpkoyalmpremo ghmwfwgfxuca. Qmdgg oieqetfqr oj Kucilr Nogemehn Mixznds ub 1553, gsi owcsid wf peem gz yzrrcwfoao ezr vxtxszprf, phe mf frdmehro exz nexqacew fc ocimy ve yzhvw 1863, xtfrp gqbgfvusf wefse. Elug rlvzsq tx fvr oieqettfwby pq qutjrfnri ubqpgtwsqvmpyp (Jdsanl rce 'elq waoiowcsidoowi owcsid'). Anyc bsbapq vngi ffvph fc vxtxszprf sanvkdgtsz gpsiysf elmh nci qgfprfwnwpk Jvrizsep gudupve. Wa 1863, Qvusqcmov Xlwugxt amg gsi rwedx fc cffxwfs e ssapvmz zpxtcq zj psptttsetrs Jvrizsep gudupve. ";

        String decrypted = new Vigenere(key, shift).decrypt(toDecrypt);

        Assertions.assertEquals(expected, decrypted);
    }

    @Test
    void decrypt() throws Exception {
        String key = "lemon";
        int shift = 1;

        String toDecrypt = "lxfopvefrnhr";
        String expected = "attackatdawn";

        String decrypted = new Vigenere(key, shift).decrypt(toDecrypt);

        Assertions.assertEquals(expected, decrypted);
    }

    @Test
    void decrypt_shift2() throws Exception {
        String key = "lemon";
        int shift = 2;

        String toDecrypt = "mygpqwfgsois";
        String expected = "attackatdawn";

        String decrypted = new Vigenere(key, shift).decrypt(toDecrypt);

        Assertions.assertEquals(expected, decrypted);
    }


    @Test
    void encrypt_invalidShift_Exception() {
        String key = "lemon";
        int shift = 0;

        Assertions.assertThrows(Exception.class, ()->{
            new Vigenere(key, shift);
        });
    }
}
