package com.adi.gab.infrastructure.config.stripe;

import com.adi.gab.application.dto.OrderItemDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StripeService {

    private final StripeProperties stripeProperties;

    public StripeService(StripeProperties stripeProperties) {
        this.stripeProperties = stripeProperties;
        Stripe.apiKey = stripeProperties.getSecretKey();
    }

    public String createCheckoutSession(List<OrderItemDTO> items) {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (OrderItemDTO item : items) {
            lineItems.add(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(Long.valueOf(item.getQuantity()))
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("usd")
                                            .setUnitAmount((long)(item.getPrice().doubleValue() * 100))
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(item.getTitle() + " x " + item.getQuantity())
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(stripeProperties.getSuccessUrl())
                .setCancelUrl(stripeProperties.getCancelUrl())
                .addAllLineItem(lineItems)
                .build();

        try {
            Session session = Session.create(params);
            return session.getUrl();
        } catch (StripeException e) {
            throw new ApplicationException("Stripe error: " + e.getMessage(), this.getClass().getSimpleName());
        }
    }
}
