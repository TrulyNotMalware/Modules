package dev.notypie.domains.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersResponse {
    private List<GetOrderResponse> orders;
}
