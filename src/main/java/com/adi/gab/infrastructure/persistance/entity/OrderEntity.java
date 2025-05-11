package com.adi.gab.infrastructure.persistance.entity;

import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.infrastructure.persistance.embeddable.AddressEmbeddable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    private UUID id;

    private UUID customerId;

    @Column(name = "order_name")
    private String orderName;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @SuppressWarnings("squid:S1710")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "shipping_address_firstname")),
            @AttributeOverride(name = "lastName", column = @Column(name = "shipping_address_lastname")),
            @AttributeOverride(name = "addressLine", column = @Column(name = "shipping_address_line")),
            @AttributeOverride(name = "emailAddress", column = @Column(name = "shipping_email_address")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_address_city")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_address_zip_code")),
            @AttributeOverride(name = "country", column = @Column(name = "shipping_address_country"))
    })
    private AddressEmbeddable shippingAddress;

    @SuppressWarnings("squid:S1710")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "billing_address_firstname")),
            @AttributeOverride(name = "lastName", column = @Column(name = "billing_address_lastname")),
            @AttributeOverride(name = "addressLine", column = @Column(name = "billing_address_line")),
            @AttributeOverride(name = "emailAddress", column = @Column(name = "billing_email_address")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_address_city")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "billing_address_zip_code")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_address_country"))
    })
    private AddressEmbeddable billingAddress;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    public void addItem(OrderItemEntity item) {
        item.setOrder(this);
        this.items.add(item);
    }
}
