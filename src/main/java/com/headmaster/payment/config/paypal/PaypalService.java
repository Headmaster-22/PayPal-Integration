package com.headmaster.payment.config.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaypalService {

    private final APIContext apiContext;

    /**
     * Create a PayPal payment
     *
     * @param total       Amount to pay
     * @param currency    Currency code (e.g., "USD")
     * @param method      Payment method ("paypal")
     * @param intent      Payment intent ("sale")
     * @param description Description for the transaction
     * @param cancelUrl   URL to redirect if payment is canceled
     * @param successUrl  URL to redirect if payment is approved
     * @return Payment object with approval links
     * @throws PayPalRESTException
     */
    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {

        // Amount
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total)); // safe formatting for PayPal

        // Transaction
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Payer
        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        // Payment
        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // Redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        // Create payment
        return payment.create(apiContext);
    }

    /**
     * Execute a PayPal payment after approval
     *
     * @param paymentId ID of the payment from PayPal
     * @param payerId   PayerID returned by PayPal
     * @return Executed Payment object
     * @throws PayPalRESTException
     */
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}