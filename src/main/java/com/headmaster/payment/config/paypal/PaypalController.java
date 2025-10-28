package com.headmaster.payment.config.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaypalController {

    private final PaypalService paypalService;

    // Home page
    @GetMapping("/")
    public String home() {
        return "index"; // Make sure index.html exists in src/main/resources/templates
    }

    // Create payment
    @PostMapping("/payment/create")
    public RedirectView createPayment() {
        try {
            String cancelUrl = "http://localhost:8080/payment/cancel";
            String successUrl = "http://localhost:8080/payment/success";

            Payment payment = paypalService.createPayment(
                    10.0,
                    "USD",
                    "paypal",
                    "sale",
                    "Payment description",
                    cancelUrl,
                    successUrl
            );

            // Find PayPal approval URL and redirect
            for (Links links : payment.getLinks()) {
                if ("approval_url".equals(links.getRel())) {
                    return new RedirectView(links.getHref());
                }
            }

        } catch (PayPalRESTException e) {
            log.error("Error occurred during payment creation:", e);
        }

        // Fallback to error page
        return new RedirectView("/payment/error");
    }

    // Payment success callback
    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if ("approved".equals(payment.getState())) {
                return "paymentSuccess"; // paymentSuccess.html
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred during payment execution:", e);
        }
        return "paymentError"; // fallback
    }

    // Payment canceled
    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel"; // paymentCancel.html
    }

    // Payment error page
    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError"; // paymentError.html
    }
}