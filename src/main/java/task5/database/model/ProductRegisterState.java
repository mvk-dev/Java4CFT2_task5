package task5.database.model;

public enum ProductRegisterState {
    OPEN("Открыт"), CLOSED("Закрыт"), DELETED("Удалён");

    private final String name;

    ProductRegisterState(String name) {
        this.name = name;
    }
}
