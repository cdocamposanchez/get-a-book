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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
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
            @AttributeOverride(name = "street", column = @Column(name = "shipping_address_street")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_address_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "shipping_address_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "shipping_address_country"))
    })
    private AddressEmbeddable shippingAddress;

    @SuppressWarnings("squid:S1710")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "billing_address_firstname")),
            @AttributeOverride(name = "lastName", column = @Column(name = "billing_address_lastname")),
            @AttributeOverride(name = "street", column = @Column(name = "billing_address_street")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_address_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "billing_address_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_address_country"))
    })
    private AddressEmbeddable billingAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> items = new ArrayList<>();
}
