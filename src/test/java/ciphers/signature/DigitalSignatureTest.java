package ciphers.signature;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repositories.PasswordRepository;

public class DigitalSignatureTest {
    private final PasswordRepository passwordRepository = PasswordRepository.getInstance();

    @Test
    void hashTest() {
        DigitalSignature ds = new DigitalSignature();
        String message = "password";

        String hashed = ds.hash(message);

        Assertions.assertEquals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", hashed);
    }

    @Test
    void addUserToRepoTest() {
        DigitalSignature ds = new DigitalSignature();
        String message = "password";

        passwordRepository.add("USER", ds.hash(message));

        String msg = passwordRepository.get("USER");

        Assertions.assertEquals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", msg);
    }

    @Test
    void verifySignatureTest() {
        DigitalSignature ds = new DigitalSignature();

        String message = "password";
        passwordRepository.add("Admin", ds.hash(message));

        String signature = ds.create(message);

        final boolean valid = ds.verify("Admin", signature);

        Assertions.assertTrue(valid);
    }
}
