package services;

import domain.dtos.CipherDto;
import domain.dtos.InputDto;

public interface CipherAccessService {
    String encrypt (String text, InputDto cipherDto);
    String decrypt (String text, InputDto cipherDto);
}
