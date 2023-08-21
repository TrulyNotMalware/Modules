package dev.notypie.domain.customer.controller;

import dev.notypie.domain.customer.application.CustomerService;
import dev.notypie.domain.customer.domain.Customer;
import dev.notypie.domain.customer.domain.CustomerRepository;
import dev.notypie.domain.customer.dto.CreateCustomerRequest;
import dev.notypie.domain.customer.dto.CreateCustomerResponse;
import dev.notypie.domain.customer.dto.GetCustomerResponse;
import dev.notypie.domain.customer.dto.GetCustomersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerService.createCustomer(createCustomerRequest.getName(), createCustomerRequest.getCreditLimit());
        return new CreateCustomerResponse(customer.getId());
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCustomersResponse> getAll() {
        return ResponseEntity.ok(new GetCustomersResponse(StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .map(c -> new GetCustomerResponse(c.getId(), c.getName(), c.getCreditLimit())).collect(Collectors.toList())));
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCustomerResponse> getCustomer(@PathVariable Long customerId) {
        return customerRepository
                .findById(customerId)
                .map(c -> new ResponseEntity<>(new GetCustomerResponse(c.getId(), c.getName(), c.getCreditLimit()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
