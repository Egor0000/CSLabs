package repositories;

import domain.entities.User;

import java.util.HashMap;

public class UserRepository {
    private static UserRepository INSTANCE = null;
    private final HashMap<String, User> users = new HashMap<>();

    private UserRepository() {

    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    public void add(String uid, User user) {
        users.put(uid, user);
    }

    public User get(String uid) {
        return users.get(uid);
    }
}
