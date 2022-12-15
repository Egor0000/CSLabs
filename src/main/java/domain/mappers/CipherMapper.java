package domain.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dtos.CipherDto;
import domain.dtos.InputDto;
import domain.dtos.UserDto;
import domain.enums.Role;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CipherMapper {
    private final static ObjectMapper om = new ObjectMapper();
    public static InputDto jsonToObject(String json) {
        InputDto cipher = new InputDto();
        try {
            JsonNode cipherNode = om.readTree(json);
            if (cipherNode.get("text") != null) {
                cipher.setText(cipherNode.get("text").textValue());
            }
            if (cipherNode.get("cipherId") != null) {
                cipher.setCipherId(cipherNode.get("cipherId").textValue());
            }
            if (cipherNode.get("key") != null) {
                cipher.setKey(cipherNode.get("key").textValue());
            }
            if (cipherNode.get("subKey") != null) {
                cipher.setSubKey(cipherNode.get("subKey").intValue());
            }
            if (cipherNode.get("p") != null) {
                cipher.setP(cipherNode.get("p").longValue());
            }
            if (cipherNode.get("q") != null) {
                cipher.setQ(cipherNode.get("q").longValue());
            }
            if (cipherNode.get("shift") != null) {
                cipher.setShift(cipherNode.get("shift").intValue());
            }
            if (cipherNode.get("user") != null) {
                UserDto userDto = new UserDto();
                JsonNode userNode = cipherNode.get("user");

                if (userNode.get("name") != null) {
                    userDto.setName(userNode.get("shift").textValue());
                }
                if (userNode.get("surname") != null) {
                    userDto.setSurname(userNode.get("surname").textValue());
                }
                if (userNode.get("email") != null) {
                    userDto.setEmail(userNode.get("email").textValue());
                }
                if (userNode.get("role") != null) {
                    userDto.setRole(Role.valueOf(userNode.get("role").textValue()));
                }
                if (userNode.get("code") != null) {
                    userDto.setCode(userNode.get("code").textValue());
                }
                if (userNode.get("password") != null) {
                    userDto.setPassword(userNode.get("password").textValue());
                }
                cipher.setUserDto(userDto);
            }
        } catch (Exception ex) {
            log.info("Failed to parse json");
        }

        return cipher;
    }
}
