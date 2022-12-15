package ciphers;

import ciphers.assymetric.RsaCipher;
import ciphers.symmetric.block.AesCipher;
import ciphers.symmetric.classical.Caesar;
import ciphers.symmetric.classical.CaesarP;
import ciphers.symmetric.classical.Playfair;
import ciphers.symmetric.classical.Vigenere;
import ciphers.symmetric.stream.RabbitCipher;
import domain.dtos.InputDto;
import domain.enums.CipherType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CipherFactory {
    public AbstractCipher getCipher(InputDto cipherDto) {
        CipherType cipherType = null;
        try {
            cipherType = CipherType.valueOf(cipherDto.getCipherId());
        } catch (Exception exception) {
            log.info("No cipher found for id {}", cipherDto.getCipherId());
        }

        if (cipherType != null) {
            if (cipherType.equals(CipherType.aes)) {
                return new AesCipher(cipherDto.getKey().toCharArray());
            } else if (cipherType.equals(CipherType.caesar)) {
                if (cipherDto.getSubKey() != null) {
                    return new Caesar(cipherDto.getSubKey());
                }
            } else if (cipherType.equals(CipherType.pCaesar)) {
                try {
                    return new CaesarP(cipherDto.getKey(), cipherDto.getSubKey());
                } catch (Exception ex) {
                    log.error("Invalid substitution key for Caesar cipher. Substitution should be an integer");
                }
            } else if (cipherType.equals(CipherType.playfair)) {
                return new Playfair(cipherDto.getKey());
            } else if (cipherType.equals(CipherType.rabbit)) {
                char [] cs = cipherDto.getKey().toCharArray();
                long [] convertedCS = new long[cs.length];
                for (int i =0; i < cs.length; i++ ) {
                    convertedCS[i] = cs[i];
                }
                return new RabbitCipher(convertedCS);
            } else if (cipherType.equals(CipherType.rsa)) {
                if (cipherDto.getQ() == null || cipherDto.getP() == null) {
                    log.error("Cannot generate RSA class. q or p is null");
                    return null;
                }
                return new RsaCipher(cipherDto.getQ(), cipherDto.getP());
            } else if (cipherType.equals(CipherType.vigenere)) {
                try {
                    return new Vigenere(cipherDto.getKey(), cipherDto.getShift());
                } catch (Exception ex) {
                    log.error("Error while generating Vigenere Cipher class");
                }
            }
        }
        return null;
    }
}
