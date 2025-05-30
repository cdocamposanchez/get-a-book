package com.adi.gab.presentation.controller;

import com.adi.gab.application.dto.OrderDTO;
import com.adi.gab.application.dto.request.PaginationRequest;
import com.adi.gab.application.dto.ResponseDTO;
import com.adi.gab.application.usecase.order.CreateOrderUseCase;
import com.adi.gab.application.usecase.order.DeleteOrderUseCase;
import com.adi.gab.application.usecase.order.GetOrdersUseCase;
import com.adi.gab.application.usecase.order.UpdateOrderStatusUseCase;
import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final GetOrdersUseCase getOrdersUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           DeleteOrderUseCase deleteOrderUseCase,
                           UpdateOrderStatusUseCase updateOrderStatusUseCase,
                           GetOrdersUseCase getOrdersUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.deleteOrderUseCase = deleteOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.getOrdersUseCase = getOrdersUseCase;
    }

    @PostMapping("/order-session")
    public ResponseEntity<ResponseDTO<String>> createStripeSession(@RequestBody OrderDTO orderDTO) {
        String stripeSession = createOrderUseCase.createStripeSession(orderDTO);
        ResponseDTO<String> response = new ResponseDTO<>(
                "Order successfully created",
                stripeSession,
                HttpStatus.CREATED
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/confirm-order")
    public ResponseEntity<ResponseDTO<OrderDTO>> confirmOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdDTO = createOrderUseCase.confirmOrder(orderDTO);
        ResponseDTO<OrderDTO> response = new ResponseDTO<>(
                "Order successfully created",
                createdDTO,
                HttpStatus.CREATED
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<OrderDTO>> updateStatus(
            @RequestParam UUID orderId,
            @RequestParam OrderStatus orderStatus
    ) {
        OrderDTO updatedOrder = updateOrderStatusUseCase.execute(orderId, orderStatus);
        ResponseDTO<OrderDTO> response = new ResponseDTO<>(
                "Order status successfully updated",
                updatedOrder,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<OrderDTO>> delete(@RequestParam UUID orderId) {
        deleteOrderUseCase.execute(OrderId.of(orderId));
        ResponseDTO<OrderDTO> response = new ResponseDTO<>(
                "Order successfully deleted with id: " + orderId,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<OrderDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationRequest pagination = new PaginationRequest(page, size);
        List<OrderDTO> orders = getOrdersUseCase.execute(pagination);
        ResponseDTO<List<OrderDTO>> response = new ResponseDTO<>(
                "Orders retrieved successfully",
                orders,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseDTO<List<OrderDTO>>> getByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationRequest pagination = new PaginationRequest(page, size);
        List<OrderDTO> orders = getOrdersUseCase.getByOrderStatus(OrderStatus.fromStringIgnoreCase(status), pagination);
        ResponseDTO<List<OrderDTO>> response = new ResponseDTO<>(
                "Orders retrieved by status " + status + " successfully",
                orders,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderDTO>> getById(@PathVariable UUID id) {
        OrderDTO order = getOrdersUseCase.getById(OrderId.of(id));
        ResponseDTO<OrderDTO> response = new ResponseDTO<>(
                "Order retrieved with ID: "+ id +" successfully",
                order,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customer")
    public ResponseEntity<ResponseDTO<List<OrderDTO>>> getByCustomerId(
            @RequestParam UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationRequest pagination = new PaginationRequest(page, size);
        List<OrderDTO> orders = getOrdersUseCase.getByCustomerId(UserId.of(customerId), pagination);
        ResponseDTO<List<OrderDTO>> response = new ResponseDTO<>(
                "Orders retrieved by customer ID: "+ customerId +" successfully",
                orders,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
