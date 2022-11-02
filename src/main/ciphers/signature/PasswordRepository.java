package main.ciphers.signature;

import java.util.HashMap;

public class PasswordRepository {
    private static PasswordRepository INSTANCE = null;
    private final HashMap<String, String> passwords = new HashMap<>();

    private PasswordRepository() {

    }

    public static PasswordRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PasswordRepository();
        }
        return INSTANCE;
    }

    public void add(String user, String password) {
        passwords.put(user, password);
    }

    public String get(String user) {
        return passwords.get(user);
    }
}
