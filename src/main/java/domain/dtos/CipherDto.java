package domain.dtos;

import lombok.Data;

@Data
public class CipherDto {
    private String cipherId;
    private String key;
    private Integer subKey;
    private Long p;
    private Long q;
    private Integer shift;
}
