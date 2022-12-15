package ciphers.classical;

import ciphers.symmetric.classical.Playfair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class PlayfairTest {

    @Test
    void prepareMsgWithSimpleMsg() {
        String msg = "tree";
        String expected = "trexex";

        String result = new Playfair("").prepareMsg(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void prepareMsgWithSimpleMsgImpair() {
        String msg = "tre";
        String expected = "trex";

        String result = new Playfair("").prepareMsg(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void prepareMsgWithMultipleDoublePairs() {
        String msg = "tree extinction";
        String expected = "trexexextinction";

        String result = new Playfair("").prepareMsg(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void prepareMsgWithMultipleDoublePairsUpperCase() {
        String msg = "Tree Extinction";
        String expected = "trexexextinction";

        String result = new Playfair("").prepareMsg(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void generateTable() {
        String key = "playfair";
        String expected = "playfirbcdeghkmnoqstuvwxz";
        String result = new Playfair(key).generateTable();

        Assertions.assertEquals(25, result.length());
        Assertions.assertEquals(expected, result);
    }

    @Test
    void generateTable_longKey() {
        String key = "playfair example";
        String expected = "playfirexmbcdghknoqstuvwz";
        String result = new Playfair(key).generateTable();

        Assertions.assertEquals(25, result.length());
        Assertions.assertEquals(expected, result);
    }

    @Test
    void generateTable_veryLongKey() {
        String key = "The Playfair cipher or Playfair square or Wheatstone–Playfair cipher is a manual symmetric encryption technique and was the first literal digram substitution cipher";
        String expected = "theplayfircosquwnmdgbkvxz";
        String result = new Playfair(key).generateTable();

        Assertions.assertEquals(25, result.length());
        Assertions.assertEquals(expected, result);
    }

    @Test
    void encrypt() {

        String key = "playfair example";
        String msg = "hello";
        String expected = "dmyran";

        String result = new Playfair(key).encrypt(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void encrypt_withNonLetters() {

        String key = "playfair example";
        String msg = "hello word!";
        String expected = "dmyranvqec";

        String result = new Playfair(key).encrypt(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void encrypt_withNonLettersUpperCase() {

        String key = "playfair example";
        String msg = "Hello Word!";
        String expected = "dmyranvqec";

        String result = new Playfair(key).encrypt(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void encrypt_longText() {

        String key = "playfair example";
        String msg = "The Playfair cipher or Playfair square or Wheatstone–Playfair cipher is a manual symmetric encryption technique and was the first literal digram substitution cipher. The scheme was invented in 1854 by Charles Wheatstone, but bears the name of Lord Playfair for promoting its use. ".toLowerCase(Locale.ROOT);
        String expected = "zbiaayfppecnbidmenilayfppemnnwledvxudmpvkzqoiaayfppecnbidmerofefulyaqfimixuirbroncflpbqovidbkrnwdeocvykzdmpmmnupbpxeyabecxfenzhkpbuvpbqobrfbxezbmodbxixvforkadkudorkgpdblearqzdmpvkzqoidvudilekzdmolixsaaneclayfpyreasilenespbqcbpnzom";

        String result = new Playfair(key).encrypt(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void decrypt_longText() {

        String key = "playfair example";
        String expected = "theplayfaircipherorplayfairsquareorwheatstoneplayfaircipherisamanualsymxmetricencryptiontechniqueandwasthefirstliteraldigramsubstitutionciphertheschemewasinventedinbycharleswheatstonebutbearsthenameoflordplayfairforpromotingitsuse";
        String msg = "zbiaayfppecnbidmenilayfppemnnwledvxudmpvkzqoiaayfppecnbidmerofefulyaqfimixuirbroncflpbqovidbkrnwdeocvykzdmpmmnupbpxeyabecxfenzhkpbuvpbqobrfbxezbmodbxixvforkadkudorkgpdblearqzdmpvkzqoidvudilekzdmolixsaaneclayfpyreasilenespbqcbpnzom";

        String result = new Playfair(key).decrypt(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void decrypt() {

        String key = "playfair example";
        String expected = "helxlo";
        String msg = "dmyran";

        String result = new Playfair(key).decrypt(msg);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void decrypt_withNonLetters() {

        String key = "playfair example";
        String expected = "helxloword";
        String msg = "dmyranvqec";

        String result = new Playfair(key).decrypt(msg);

        Assertions.assertEquals(expected, result);
    }

}
