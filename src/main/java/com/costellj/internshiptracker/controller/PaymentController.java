package com.costellj.internshiptracker.controller;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.costellj.internshiptracker.model.Tier;
import com.costellj.internshiptracker.model.User;
import com.costellj.internshiptracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final UserRepository userRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @Value("${frontend.url}")
    private String frontendUrl;

    // Step 4 — create a Stripe checkout session
    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckout(
            @AuthenticationPrincipal User currentUser) {

        try {
            Stripe.apiKey = stripeSecretKey;

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(frontendUrl + "/dashboard?upgraded=true")
                    .setCancelUrl(frontendUrl + "/dashboard")
                    .putMetadata("userId", currentUser.getId().toString())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount(499L) // $4.99 in cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Internship Tracker Pro")
                                                                    .setDescription("Unlimited application tracking")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);
            return ResponseEntity.ok(Map.of("url", session.getUrl()));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to create checkout session"));
        }
    }

    // Step 5 — handle Stripe webhook after payment succeeds
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer()
                        .getObject()
                        .orElseThrow();

                String userId = session.getMetadata().get("userId");

                userRepository.findById(Long.parseLong(userId)).ifPresent(user -> {
                    user.setTier(Tier.PRO);
                    userRepository.save(user);
                });
            }

            return ResponseEntity.ok("received");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Webhook error: " + e.getMessage());
        }
    }
}