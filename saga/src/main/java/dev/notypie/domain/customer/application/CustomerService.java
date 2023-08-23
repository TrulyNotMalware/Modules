package dev.notypie.domain.customer.application;

import dev.notypie.domain.customer.domain.Customer;
import dev.notypie.domain.customer.domain.CustomerCreditLimitExceededException;
import dev.notypie.domain.customer.domain.CustomerRepository;
import dev.notypie.messaging.common.Money;
import dev.notypie.domain.customer.domain.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String name, Money creditLimit) {
        Customer customer = new Customer(name, creditLimit);
        return customerRepository.save(customer);
    }

    public void reserveCredit(long customerId, long orderId, Money orderTotal) throws CustomerCreditLimitExceededException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        customer.reserveCredit(orderId, orderTotal);
    }
}
