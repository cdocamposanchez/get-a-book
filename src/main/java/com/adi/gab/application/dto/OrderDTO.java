package com.adi.gab.application.dto;

import com.adi.gab.domain.types.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
public class OrderDTO {
    private UUID id;
    private UUID customerId;
    private AddressDTO shippingAddress;
    private AddressDTO billingAddress;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> orderItems;
    private String creationDate;
}
