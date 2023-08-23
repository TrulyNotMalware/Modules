package dev.notypie.domain.customer.domain;

import dev.notypie.messaging.common.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

@Entity
@Getter
@Table(name = "Customer")
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Money creditLimit;

    @ElementCollection
    private Map<Long, Money> creditReservations;

    @Version
    private Long version;
    public Customer(String name, Money creditLimit) {
        this.name = name;
        this.creditLimit = creditLimit;
        this.creditReservations = Collections.emptyMap();
    }
    public Money availableCredit() {
        return creditLimit.subtract(creditReservations.values().stream().reduce(Money.ZERO, Money::add));
    }

    public void reserveCredit(Long orderId, Money orderTotal) {
        log.info("orderId : {}, orderTotal : {}",orderId, orderTotal);
        if (availableCredit().isGreaterThanOrEqual(orderTotal)) {
            this.creditReservations.put(orderId, orderTotal);
        } else
            throw new CustomerCreditLimitExceededException();
    }
}
