package main;

public abstract class AbstractCypher {
    public String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public abstract String encrypt(String msg);
    public abstract String decrypt(String msg);

    public int prepareKey(int key) {
        return ((key % alphabet.length())+alphabet.length())%alphabet.length();
    }

}
