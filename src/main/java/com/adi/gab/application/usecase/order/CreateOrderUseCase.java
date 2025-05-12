package com.adi.gab.application.usecase.order;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.dto.OrderDTO;
import com.adi.gab.application.usecase.book.GetBooksUseCase;
import com.adi.gab.application.usecase.book.ReduceStockUseCase;
import com.adi.gab.domain.model.Order;
import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.application.mapper.OrderMapper;
import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import com.adi.gab.infrastructure.persistance.repository.OrderRepository;
import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.stereotype.Service;

import com.adi.gab.application.exception.ApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final GetBooksUseCase getBooksUseCase;
    private final ReduceStockUseCase reduceStockUseCase;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public CreateOrderUseCase(OrderRepository orderRepository, GetBooksUseCase getBooksUseCase, ReduceStockUseCase reduceStockUseCase, AuthenticatedUserProvider authenticatedUserProvider) {
        this.orderRepository = orderRepository;
        this.getBooksUseCase = getBooksUseCase;
        this.reduceStockUseCase = reduceStockUseCase;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Transactional
    public OrderDTO execute(OrderDTO orderDTO) {
        int maxRetries = 3;
        int attempts = 0;

        while (true) {
            try {
                OrderId orderId = OrderId.generate();
                UserId customerId = authenticatedUserProvider.getAuthenticatedUserId();

                checkItems(orderDTO);

                Order order = OrderMapper.toDomain(orderDTO, orderId, customerId);
                order.setOrderStatus(OrderStatus.PENDING);
                return saveOrder(order);

            } catch (DataIntegrityViolationException _) {
                attempts++;
                if (attempts >= maxRetries) throw new ApplicationException("Failed to create order after multiple retries.", this.getClass().getSimpleName());
            }
        }
    }


    private OrderDTO saveOrder(Order order) {
        try {
            OrderEntity savedEntity = orderRepository.save(OrderMapper.toEntity(order));
            Order domain = OrderMapper.toDomain(savedEntity);

            domain.getOrderItems().forEach(item -> reduceStockUseCase.execute(item.getBookId(), item.getQuantity()));

            return OrderMapper.toDto(domain);
        } catch (Exception e) {
            throw new ApplicationException("Error during mapping: " + e.getMessage(), this.getClass().getSimpleName());
        }
    }

    private void checkItems(OrderDTO orderDTO) {
        orderDTO.getOrderItems().forEach(item -> {
            BookDTO book = getBooksUseCase.getById(BookId.of(item.getBookId()));
            if (book == null) throw new ApplicationException(
                    "Book with ID: " + item.getBookId() + " not found",
                    this.getClass().getSimpleName());

            if (book.getQuantity() < item.getQuantity()) throw new ApplicationException(
                    "Not enough items in book with ID: " + book.getId() + " available: " + book.getQuantity(),
                    this.getClass().getSimpleName());

            item.setId(UUID.randomUUID());
        });
    }
}

