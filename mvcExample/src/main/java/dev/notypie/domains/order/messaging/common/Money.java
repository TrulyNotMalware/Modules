package dev.notypie.domains.order.messaging.common;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Money {
    public static final Money ZERO = new Money(0);
    private BigDecimal amount;

    public Money(int i){
        this.amount = new BigDecimal(i);
    }
    public Money(String s) {
        this.amount = new BigDecimal(s);
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }

    public Money add(Money other) {
        return new Money(amount.add(other.amount));
    }
    public Money subtract(Money other) {
        return new Money(amount.subtract(other.amount));
    }
}
