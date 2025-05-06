package com.adi.gab.infrastructure.dto;

import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.domain.valueobject.Address;
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
    private String orderName;
    private Address shippingAddress;
    private Address billingAddress;
    private OrderStatus status;
    private List<OrderItemDTO> orderItems;
}
