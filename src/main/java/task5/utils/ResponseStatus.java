package task5.utils;

public enum ResponseStatus {
    OK(0), BAD(400), NOT_FOUND(404), SERVER_ERR(500);
    private final int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
