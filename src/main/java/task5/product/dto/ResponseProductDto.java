package task5.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class ResponseProductDto {
    private Long instanceId;
    private List<Long> registerId;
    private List<Long> supplementaryAgreementId;
}
