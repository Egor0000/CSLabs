package tests.ciphers.classical;

import main.ciphers.symmetric.classical.Caesar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CaesarTest {

    @Test
    void encrypt_alphabetWithKey3_encryptedAlphabet () {
        Caesar caesar = new Caesar(3);

        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("defghijklmnopqrstuvwxyzabc", encryptedCaesar);
    }

    @Test
    void encrypt_alphabetCapitalLettersWithKey3_encryptedAlphabet () {
        Caesar caesar = new Caesar(3);

        String alphabet = "ABCDEFGHijklmnopqrstuvwxyz";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("DEFGHIJKlmnopqrstuvwxyzabc", encryptedCaesar);
    }

    @Test
    void encrypt_alphabetAllCapitalLettersWithKey3_encryptedAlphabet () {
        Caesar caesar = new Caesar(3);

        String alphabet = "ABCDEFGHIJKLMOPQRST";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("DEFGHIJKLMNOPRSTUVW", encryptedCaesar);
    }

    @Test
    void encrypt_alphabetWithKey50_encryptedAlphabet () {
        Caesar caesar = new Caesar(50);

        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("yzabcdefghijklmnopqrstuvwx", encryptedCaesar);
    }

    @Test
    void encrypt_alphabetWithKey120_encryptedAlphabet () {
        Caesar caesar = new Caesar(120);

        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("qrstuvwxyzabcdefghijklmnop", encryptedCaesar);
    }

    @Test
    void encrypt_alphabetsWithNegative120_encryptedAlphabet () {
        Caesar caesar = new Caesar(-120);

        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("klmnopqrstuvwxyzabcdefghij", encryptedCaesar);
    }

    @Test
    void encrypt_alphabetsCapitalWithNegative120_encryptedAlphabet () {
        Caesar caesar = new Caesar(-120);

        String alphabet = "ABCDEFGHIJKLMOPQRSTabcdefghijklmnopqrstuvwxyz";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("KLMNOPQRSTUVWYZABCDklmnopqrstuvwxyzabcdefghij", encryptedCaesar);
    }

    @Test
    void encrypt_loremIpsum_encryptedLoremIpsum () {
        Caesar caesar = new Caesar(3);

        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla ornare quis dui mollis dictum. " +
                "Suspendisse interdum, eros ac iaculis euismod, nisl lorem convallis augue, et lacinia eros ipsum eget lorem." +
                " Ut hendrerit accumsan lectus, nec volutpat dolor iaculis vel. Aliquam volutpat varius lacus sit amet" +
                " molestie. Proin tortor mauris, accumsan eu nibh at, mollis mollis arcu. Proin varius, eros sit amet" +
                " molestie placerat, massa nisi tincidunt neque, sit amet rutrum sapien ipsum id leo. In rhoncus at erat" +
                " malesuada egestas. ";

        String encryptedLorem = "Oruhp lsvxp groru vlw dphw, frqvhfwhwxu dglslvflqj holw." +
                " Qxood ruqduh txlv gxl proolv glfwxp. Vxvshqglvvh lqwhugxp, hurv df ldfxolv hxlvprg," +
                " qlvo oruhp frqydoolv dxjxh, hw odflqld hurv lsvxp hjhw oruhp. Xw khqguhulw dffxpvdq ohfwxv," +
                " qhf yroxwsdw groru ldfxolv yho. Doltxdp yroxwsdw ydulxv odfxv vlw dphw prohvwlh. Surlq wruwru pdxulv," +
                " dffxpvdq hx qlek dw, proolv proolv dufx. Surlq ydulxv, hurv vlw dphw prohvwlh sodfhudw," +
                " pdvvd qlvl wlqflgxqw qhtxh, vlw dphw uxwuxp vdslhq lsvxp lg ohr. Lq ukrqfxv dw hudw pdohvxdgd hjhvwdv. ";

        String encryptedCaesar = caesar.encrypt(lorem);

        Assertions.assertEquals(encryptedLorem, encryptedCaesar);
    }

    @Test
    void decrypt_loremIpsum_encryptedLoremIpsum () {
        Caesar caesar = new Caesar(3);

        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla ornare quis dui mollis dictum. " +
                "Suspendisse interdum, eros ac iaculis euismod, nisl lorem convallis augue, et lacinia eros ipsum eget lorem." +
                " Ut hendrerit accumsan lectus, nec volutpat dolor iaculis vel. Aliquam volutpat varius lacus sit amet" +
                " molestie. Proin tortor mauris, accumsan eu nibh at, mollis mollis arcu. Proin varius, eros sit amet" +
                " molestie placerat, massa nisi tincidunt neque, sit amet rutrum sapien ipsum id leo. In rhoncus at erat" +
                " malesuada egestas. ";

        String encryptedLorem = "Oruhp lsvxp groru vlw dphw, frqvhfwhwxu dglslvflqj holw." +
                " Qxood ruqduh txlv gxl proolv glfwxp. Vxvshqglvvh lqwhugxp, hurv df ldfxolv hxlvprg," +
                " qlvo oruhp frqydoolv dxjxh, hw odflqld hurv lsvxp hjhw oruhp. Xw khqguhulw dffxpvdq ohfwxv," +
                " qhf yroxwsdw groru ldfxolv yho. Doltxdp yroxwsdw ydulxv odfxv vlw dphw prohvwlh. Surlq wruwru pdxulv," +
                " dffxpvdq hx qlek dw, proolv proolv dufx. Surlq ydulxv, hurv vlw dphw prohvwlh sodfhudw," +
                " pdvvd qlvl wlqflgxqw qhtxh, vlw dphw uxwuxp vdslhq lsvxp lg ohr. Lq ukrqfxv dw hudw pdohvxdgd hjhvwdv. ";

        String decryptedCaesar = caesar.decrypt(encryptedLorem);

        Assertions.assertEquals(lorem, decryptedCaesar);
    }

    @Test
    void decrypt_alphabetZeroKey_noChanges () {
        Caesar caesar = new Caesar(0);

        String encryptedCaesar = "ABCDEFGHIJKLMOPQRSTabcdefghijklmnopqrstuvwxyz";

        String alphabet = caesar.decrypt(encryptedCaesar);

        Assertions.assertEquals("ABCDEFGHIJKLMOPQRSTabcdefghijklmnopqrstuvwxyz", alphabet);
    }

    @Test
    void encrypt_alphabetZeroKey_noChanges () {
        Caesar caesar = new Caesar(0);

        String alphabet = "ABCDEFGHIJKLMOPQRSTabcdefghijklmnopqrstuvwxyz";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("ABCDEFGHIJKLMOPQRSTabcdefghijklmnopqrstuvwxyz", encryptedCaesar);
    }

    @Test
    void encrypt_emptyString_encryptedAlphabet () {
        Caesar caesar = new Caesar(10);

        String alphabet = "";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("", encryptedCaesar);
    }

    @Test
    void encrypt_emptyStringNegativeKey_emptyString () {
        Caesar caesar = new Caesar(-770);

        String alphabet = "";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("", encryptedCaesar);
    }

    @Test
    void encrypt_numbers_encryptedNumbers () {
        Caesar caesar = new Caesar(3);

        String alphabet = "123456";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("456789", encryptedCaesar);
    }

    @Test
    void encrypt_numbersNegativeKey_encryptedNumbers () {
        Caesar caesar = new Caesar(-3);

        String alphabet = "123456";

        String encryptedCaesar = caesar.encrypt(alphabet);

        Assertions.assertEquals("456789", encryptedCaesar);
    }
}
