package com.adi.gab.application.usecase.order;

import com.adi.gab.application.dto.OrderDTO;
import com.adi.gab.application.dto.request.PaginationRequest;
import com.adi.gab.application.mapper.OrderMapper;
import com.adi.gab.domain.model.Order;
import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import com.adi.gab.infrastructure.persistance.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetOrdersUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private GetOrdersUseCase getOrdersUseCase;

    @Test
    void shouldReturnPagedOrders() {
        // Arrange
        OrderEntity orderEntity = OrderEntity.builder()
                .id(UUID.randomUUID())
                .orderName("Test Order")
                .orderStatus("CREATED")
                .build();

        List<OrderEntity> orderEntities = List.of(orderEntity);
        PageImpl<OrderEntity> pagedOrders = new PageImpl<>(orderEntities);

        when(orderRepository.findAll(PageRequest.of(0, 10))).thenReturn(pagedOrders);

        // Simular métodos estáticos de OrderMapper
        Order mockDomainOrder = mock(Order.class);
        OrderDTO mockDto = mock(OrderDTO.class);

        try (MockedStatic<OrderMapper> mockedMapper = mockStatic(OrderMapper.class)) {
            mockedMapper.when(() -> OrderMapper.toDomain(orderEntity)).thenReturn(mockDomainOrder);
            mockedMapper.when(() -> OrderMapper.toDto(mockDomainOrder)).thenReturn(mockDto);

            PaginationRequest paginationRequest = new PaginationRequest(0, 10);

            List<OrderDTO> result = getOrdersUseCase.execute(paginationRequest);

            assertEquals(1, result.size());
            verify(orderRepository, times(1)).findAll(PageRequest.of(0, 10));
        }
    }
}

