package main.ciphers.symmetric;

public abstract class AbstractCipher {
    public abstract String encrypt(String msg);
    public abstract String decrypt(String msg);
}
