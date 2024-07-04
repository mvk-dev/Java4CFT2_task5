package task5.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CheckResult {
    @Setter
    private ResponseStatus status;
    private final List<String> errors;

    public CheckResult(ResponseStatus status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }
}
