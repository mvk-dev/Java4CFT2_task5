package task5.register.dto;

import lombok.Getter;

@Getter
public class ResponseRegisterDto {
    private final Long accountId;

    public ResponseRegisterDto(Long registerId) {
        this.accountId = registerId;
    }
}
