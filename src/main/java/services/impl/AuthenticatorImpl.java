package services.impl;

import ciphers.signature.DigitalSignature;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import domain.dtos.InputDto;
import domain.dtos.UserDto;
import domain.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import repositories.PasswordRepository;
import repositories.TokenRepository;
import services.UserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Slf4j
public class AuthenticatorImpl {
    private final UserService userService;
    public AuthenticatorImpl(UserService userService) {
        this.userService = userService;
    }

    public void register(UserDto userDto) {
        String secret = generateSecretKey();
        TokenRepository.getInstance().add(userDto.getEmail(), secret);
        DigitalSignature ds = new DigitalSignature();
        PasswordRepository.getInstance().add(userDto.getEmail(), ds.hash(userDto.getPassword()));
        getUserQrCode(secret, userDto.getEmail());

        userService.addUser(userDto);
    }

    public boolean verify(UserDto userDto, String code) {
        DigitalSignature ds = new DigitalSignature();
        return ds.verify(userDto.getEmail(), ds.create(userDto.getPassword()))
                && code.equals(getTOTPCode(TokenRepository.getInstance().get(userDto.getEmail())));
    }

    public boolean authorize(InputDto inputDto) {
        Role role = inputDto.getUserDto().getRole();
        String cipher = inputDto.getCipherId();

        return role.containsCipher(cipher);
    }

    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    private String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    private String getGoogleAuthenticatorBarCode(String secretKey, String account) {
        return "otpauth://totp/"
                + URLEncoder.encode(account, StandardCharsets.UTF_8).replace("+", "%20")
                + "?secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8).replace("+", "%20")
                + "&issuer=" + URLEncoder.encode("", StandardCharsets.UTF_8).replace("+", "%20");
    }

    private void createQRCode(String barCodeData)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                100, 100);
        try (FileOutputStream out = new FileOutputStream("src/main/resources/qrcodes/code1.png")) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }

    private void getUserQrCode(String secretKey, String account) {
        try {
            String barCode = getGoogleAuthenticatorBarCode(secretKey, account);
            createQRCode(barCode);
        } catch (Exception ex) {
            log.info("Error {}", ex.getMessage());
        }
    }

}
