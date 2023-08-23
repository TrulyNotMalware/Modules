package dev.notypie.messaging.common;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderDetails {
    private Long customerId;

    @Embedded
    private Money orderTotal;
}
