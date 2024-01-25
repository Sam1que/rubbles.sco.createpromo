package ru.a366.sco_createpromo.common.core;

public interface Consumer<T> {
    void accept(T message);
}
