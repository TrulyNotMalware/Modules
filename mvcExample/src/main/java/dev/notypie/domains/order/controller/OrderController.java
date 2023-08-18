package dev.notypie.domains.order.controller;

import dev.notypie.domains.order.domain.Order;
import dev.notypie.domains.order.domain.OrderRepository;
import dev.notypie.domains.order.dto.CreateOrderRequest;
import dev.notypie.domains.order.dto.CreateOrderResponse;
import dev.notypie.domains.order.dto.GetOrderResponse;
import dev.notypie.domains.order.dto.GetOrdersResponse;
import dev.notypie.domains.order.messaging.common.OrderDetails;
import dev.notypie.domains.order.sagas.OrderSagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderSagaService orderSagaService;
    private final OrderRepository orderRepository;


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request){
        Order order = orderSagaService.createOrder(new OrderDetails(request.getCustomerId(), request.getOrderTotal()));
        return new CreateOrderResponse(order.getId());
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetOrdersResponse> getAll() {
        return ResponseEntity.ok(new GetOrdersResponse(orderRepository.findAll().stream()
                .map(o -> new GetOrderResponse(o.getId(), o.getState(), o.getRejectionReason())).collect(Collectors.toList())));
    }

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable Long orderId) {
        return orderRepository
                .findById(orderId)
                .map(o -> new ResponseEntity<>(new GetOrderResponse(o.getId(), o.getState(), o.getRejectionReason()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetOrderResponse>> getOrdersByCustomerId(@PathVariable Long customerId) {
        return new ResponseEntity<>(orderRepository
                .findAllByOrderDetailsCustomerId(customerId)
                .stream()
                .map(o -> new GetOrderResponse(o.getId(), o.getState(), o.getRejectionReason()))
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
