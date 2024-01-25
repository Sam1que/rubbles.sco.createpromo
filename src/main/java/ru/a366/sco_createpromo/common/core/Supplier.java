package ru.a366.sco_createpromo.common.core;

public interface Supplier<T extends Message> {
    T get();
}
