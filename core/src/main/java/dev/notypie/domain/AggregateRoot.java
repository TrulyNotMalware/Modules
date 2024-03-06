package dev.notypie.domain;

import java.util.function.Supplier;

public abstract class AggregateRoot {


    /**
     * Validates the current instance.
     * This method should be overridden by subclasses to provide custom validation logic.
     * It is called internally by the createInstance() method to ensure the validity of the object before returning it.
     */
    protected void validate(){}

    protected static <T extends AggregateRoot > T createInstance(
            Supplier<T> supplier
    ){
        T instance = supplier.get();
        instance.validate();
        return instance;
    }
}
