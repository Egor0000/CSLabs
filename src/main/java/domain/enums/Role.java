package domain.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
    Admin(List.of("caesar")),
    Regular(List.of("rsa", "dsa")),
    Privileged(List.of("playfair"));

    private final List<String> ciphers;
    Role(List<String> ciphers) {
        this.ciphers = ciphers;
    }

    public boolean containsCipher(String cipher) {
        return ciphers.contains(cipher);
    }
}
