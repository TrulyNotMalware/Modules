package dev.notypie.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.function.Supplier;

//@Aggregate
//@NoArgsConstructor
@Getter
public abstract class AggregateRoot<ID> {

    public ID id;


    /**
     * Validates the current instance.
     * This method should be overridden by subclasses to provide custom validation logic.
     * It is called internally by the createInstance() method to ensure the validity of the object before returning it.
     */

    protected void validate(){}

    protected static <T extends AggregateRoot<?>> T createInstance(
            Supplier<T> supplier
    ){
        T instance = supplier.get();
        instance.validate();
        return instance;
    }
}
