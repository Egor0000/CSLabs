package repositories;

import java.util.HashMap;

public class TokenRepository {
    private static TokenRepository INSTANCE = null;
    private final HashMap<String, String> tokens = new HashMap<>();

    private TokenRepository() {

    }

    public static TokenRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TokenRepository();
        }
        return INSTANCE;
    }

    public void add(String user, String token) {
        tokens.put(user, token);
    }

    public String get(String user) {
        return tokens.get(user);
    }
}
