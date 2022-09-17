package main;

public class CaesarP extends AbstractCypher{
    private final String key;

    private final int substitutionKey;

    public CaesarP(String key, int substitutionKey) {
        this.key = key.toLowerCase();
        this.substitutionKey = substitutionKey;
    }

    @Override
    public String encrypt(String msg) {
        msg = new Caesar(substitutionKey).encrypt(msg);
        return encrypt(msg, key, alphabet);
    }

    @Override
    public String decrypt(String msg) {
        msg =  encrypt(msg, alphabet, key);
        return new Caesar(substitutionKey).decrypt(msg);
    }

    private String encrypt(String msg, String key, String alphabet) {
        return new String(msg.toLowerCase().chars().map(ch -> {
            return key.charAt(alphabet.indexOf(ch));
        }).toArray(), 0, msg.length());
    }
}
