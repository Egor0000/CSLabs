package ciphers.signature;

import ciphers.assymetric.RsaCipher;
import repositories.PasswordRepository;
import utils.StringUtils;

import java.security.MessageDigest;

public class DigitalSignature {
    private final PasswordRepository passwordRepository = PasswordRepository.getInstance();
    private final RsaCipher rsa = new RsaCipher(137, 193);

    public String create(String message) {
        try {
            return rsa.encryptPrivate(hash(message));

        } catch (Exception ex) {
            throw new RuntimeException("Failed to create digital signature");
        }
    }

    public boolean verify(String user, String digitalSignature) {
        String hashedPassword = passwordRepository.get(user);

        if (hashedPassword == null) {
            return false;
        }

        String decrypted = rsa.decryptPublic(digitalSignature);

        return hashedPassword.equals(decrypted);
    }

    public String hash(String message) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedMessage = digest.digest(message.getBytes());
            String hashed = StringUtils.bytesToHex(hashedMessage);

            return hashed;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
