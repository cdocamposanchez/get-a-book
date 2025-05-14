package com.adi.gab.application.usecase.Order;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.dto.OrderDTO;
import com.adi.gab.application.dto.OrderItemDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.usecase.book.GetBooksUseCase;
import com.adi.gab.application.usecase.book.ReduceStockUseCase;
import com.adi.gab.application.usecase.order.CreateOrderUseCase;

import com.adi.gab.infrastructure.persistance.repository.OrderRepository;
import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;
import com.adi.gab.infrastructure.config.stripe.StripeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateOrderUseCaseTest {

    private OrderRepository orderRepository;
    private GetBooksUseCase getBooksUseCase;
    private ReduceStockUseCase reduceStockUseCase;
    private AuthenticatedUserProvider authenticatedUserProvider;
    private StripeService stripeService;
    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        getBooksUseCase = mock(GetBooksUseCase.class);
        reduceStockUseCase = mock(ReduceStockUseCase.class);
        authenticatedUserProvider = mock(AuthenticatedUserProvider.class);
        stripeService = mock(StripeService.class);

        createOrderUseCase = new CreateOrderUseCase(
                orderRepository,
                getBooksUseCase,
                reduceStockUseCase,
                authenticatedUserProvider,
                stripeService
        );
    }

    @Test
    void shouldCreateStripeSessionSuccessfully() {
        OrderItemDTO item = OrderItemDTO.builder()
                .bookId(UUID.randomUUID())
                .quantity(1)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .orderItems(List.of(item))
                .build();

        when(getBooksUseCase.getById(any())).thenReturn(
                BookDTO.builder()
                        .id(item.getBookId())
                        .quantity(10)
                        .price(BigDecimal.TEN)
                        .title("Test Book")
                        .build()
        );

        when(stripeService.createCheckoutSession(any())).thenReturn("stripe-session-url");

        String sessionUrl = createOrderUseCase.createStripeSession(orderDTO);

        assertNotNull(sessionUrl);
        assertEquals("stripe-session-url", sessionUrl);
        verify(stripeService).createCheckoutSession(any());
    }

    @Test
    void shouldThrowException_WhenBookDoesNotExist() {

        UUID bookId = UUID.randomUUID();

        OrderItemDTO item = OrderItemDTO.builder()
                .bookId(bookId)
                .quantity(1)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .orderItems(List.of(item))
                .build();

        when(getBooksUseCase.getById(any())).thenReturn(null);

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            createOrderUseCase.createStripeSession(orderDTO);
        });

        assertTrue(exception.getMessage().contains("Book not found"));
    }

}
