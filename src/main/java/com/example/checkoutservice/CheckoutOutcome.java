package com.example.checkoutservice;

import com.example.checkoutservice.model.CheckoutFailureReason;
import com.example.checkoutservice.model.Customer;
import com.example.checkoutservice.model.Vehicle;

public class CheckoutOutcome {

    private final boolean outcome;
    private final String message;
    private final CheckoutFailureReason reason;

    public CheckoutOutcome(boolean outcome, Customer customer, Vehicle vehicle, CheckoutFailureReason reason) {
        this.outcome = outcome;
        this.reason = reason;

        if (this.outcome) {
            this.message = String.format("Customer '%s' (credit score = %s)" +
                            " is approved to purchase vehicle '%s' (minimum credit score = %s)",
                    customer.getCustomerId(),
                    customer.getCreditScore(),
                    vehicle.getVehicleId(),
                    vehicle.getMinCreditScore());
        } else {
            if (reason == CheckoutFailureReason.CREDIT) {
                this.message = String.format("Customer '%s' (credit score = %s)" +
                                " is NOT approved to purchase vehicle '%s' (minimum credit score = %s)",
                        customer.getCustomerId(),
                        customer.getCreditScore(),
                        vehicle.getVehicleId(),
                        vehicle.getMinCreditScore());
            } else if (reason == CheckoutFailureReason.OUT_OF_STOCK) {
                this.message = String.format("Not enough stock to purchase vehicle '%s'", vehicle.getVehicleId());
            } else {
                this.message = String.format("Can't purchase vehicle '%s' - unknown reason", vehicle.getVehicleId());
            }

        }
    }

    public boolean getOutcome() {
        return this.outcome;
    }

    public String getMessage() {
        return this.message;
    }

}
