package task5.utils;

public interface Checkable<T,R> {
    R apply(T object);
}
